/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.SessionState;
import com.radixpro.enigma.domain.config.Configuration;
import com.radixpro.enigma.domain.config.ConfiguredCelObject;
import com.radixpro.enigma.references.*;
import com.radixpro.enigma.shared.exceptions.UnknownIdException;
import com.radixpro.enigma.ui.charts.screens.helpers.GlyphForCelObject;
import com.radixpro.enigma.ui.creators.*;
import com.radixpro.enigma.ui.shared.Help;
import com.radixpro.enigma.xchg.api.PersistedConfigurationApi;
import com.radixpro.enigma.xchg.domain.helpers.IndexMappingsList;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.controlsfx.control.CheckComboBox;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.radixpro.enigma.ui.shared.UiDictionary.INPUT_DEFAULT_STYLE;
import static com.radixpro.enigma.ui.shared.UiDictionary.INPUT_ERROR_STYLE;

/**
 * Edit screen for existing configurations.
 */
public class ConfigEdit {

   private static final Logger LOG = Logger.getLogger(ConfigEdit.class);

   private static final double WIDTH = 500.0;
   private static final double HEIGHT = 500.0;
   private static final double CONFIGDATA_HEIGHT = 300.0;
   private static final double TITLE_HEIGHT = 45.0;
   private static final double SUBTITLE_HEIGHT = 30.0;
   private static final double DATA_TEXT_WIDTH = 150.0;
   private static final double DATA_INPUT_WIDTH = 350.0;
   private static final double GAP = 6.0;
   private final SessionState state;
   private final PersistedConfigurationApi pcApi;
   private Configuration config;
   private InputStatus inputStatus = InputStatus.READY;
   private Stage stage;
   private TextField descriptionInput;
   private ChoiceBox<String> choiceBoxObserverPos;
   private ChoiceBox<String> choiceBoxHouseSystem;
   private ChoiceBox<String> choiceBoxEclipticProj;
   private ChoiceBox<String> choiceBoxAyanamsha;
   private CheckComboBox<String> checkComboBoxCelObjects;
   private Button btnOk;
   private Button btnHelp;
   private Button btnCancel;
   private IndexMappingsList indexMappingsEclProjections;
   private IndexMappingsList indexMappingsObsPositions;
   private IndexMappingsList indexMappingsHouseSystems;
   private IndexMappingsList indexMappingsCelObjects;
   private IndexMappingsList indexMappingsAyanamshas;
   private Label lblDescription;
   private Label lblHouseSystem;
   private Label lblAyanamsha;
   private Label lblObserverPosition;
   private Label lblEclipticProjection;
   private Label lblCelObjects;

   public ConfigEdit(@NotNull final PersistedConfigurationApi pcApi) {
      this.state = SessionState.INSTANCE;
      this.pcApi = pcApi;
   }

   public void show() {
      this.config = state.getSelectedConfig();
      populateStage();
      defineListeners();
      stage.showAndWait();
   }

   private void populateStage() {
      btnHelp = new ButtonBuilder("ui.shared.btn.help").setDisabled(false).build();
      btnCancel = new ButtonBuilder("ui.shared.btn.cancel").setDisabled(false).build();
      btnOk = new ButtonBuilder("ui.shared.btn.ok").setDisabled(false).build();
      ButtonBar buttonBar = new ButtonBarBuilder().setButtons(btnHelp, btnCancel, btnOk).build();
      Label lblTitle = new LabelBuilder("ui.configs.edit.title").setPrefWidth(WIDTH).setStyleClass("titletext").build();
      Label lblSubTitle = new LabelBuilder("").setText(config.getName()).setPrefWidth(WIDTH).setStyleClass("subtitletext").build();
      lblDescription = new LabelBuilder("ui.general.description").setPrefWidth(DATA_TEXT_WIDTH).build();
      lblHouseSystem = new LabelBuilder("ui.general.housesystem").setPrefWidth(DATA_TEXT_WIDTH).build();
      lblAyanamsha = new LabelBuilder("ui.general.ayanamsha").setPrefWidth(DATA_TEXT_WIDTH).build();
      lblObserverPosition = new LabelBuilder("ui.general.observerposition").setPrefWidth(DATA_TEXT_WIDTH).build();
      lblEclipticProjection = new LabelBuilder("ui.general.eclipticprojection").setPrefWidth(DATA_TEXT_WIDTH).build();
      lblCelObjects = new LabelBuilder("ui.general.celobjects").setPrefWidth(DATA_TEXT_WIDTH).build();
      Pane paneTitle = new PaneBuilder().setWidth(WIDTH).setHeight(TITLE_HEIGHT).setStyleClass("titlepane").setChildren(lblTitle).build();
      Pane paneSubTitle = new PaneBuilder().setWidth(WIDTH).setHeight(SUBTITLE_HEIGHT).setStyleClass("subtitleplane").setChildren(lblSubTitle).build();
      descriptionInput = new TextFieldBuilder().setPrefWidth(DATA_INPUT_WIDTH).setText(config.getDescription()).build();
      choiceBoxHouseSystem = new ChoiceBoxBuilder().setPrefWidth(DATA_INPUT_WIDTH).setItems(HouseSystems.getObservableList()).build();
      indexMappingsHouseSystems = HouseSystems.getIndexMappings();
      choiceBoxHouseSystem.getSelectionModel().select(indexMappingsHouseSystems.getSequenceIdForEnumId(config.getAstronConfiguration().getHouseSystem().getId()));
      choiceBoxObserverPos = new ChoiceBoxBuilder().setPrefWidth(DATA_INPUT_WIDTH).setItems(ObserverPositions.getObservableList()).build();
      indexMappingsObsPositions = ObserverPositions.getIndexMappings();
      choiceBoxObserverPos.getSelectionModel().select(indexMappingsObsPositions.getSequenceIdForEnumId(config.getAstronConfiguration().getObserverPosition().getId()));
      choiceBoxEclipticProj = new ChoiceBoxBuilder().setPrefWidth(DATA_INPUT_WIDTH).setItems(EclipticProjections.getObservableList()).build();
      indexMappingsEclProjections = EclipticProjections.getIndexMappings();
      choiceBoxEclipticProj.getSelectionModel().select(indexMappingsEclProjections.getSequenceIdForEnumId(config.getAstronConfiguration().getEclipticProjection().getId()));
      choiceBoxAyanamsha = new ChoiceBoxBuilder().setPrefWidth(DATA_INPUT_WIDTH).setItems(Ayanamshas.getObservableList()).build();
      indexMappingsAyanamshas = Ayanamshas.getIndexMappings();
      choiceBoxAyanamsha.getSelectionModel().select(indexMappingsAyanamshas.getSequenceIdForEnumId(config.getAstronConfiguration().getAyanamsha().getId()));
      checkComboBoxCelObjects = createComboBoxCelObject();
      GridPane gridPane = createGridPane();
      VBox vBox = new VBoxBuilder().setWidth(WIDTH).setHeight(HEIGHT).setPadding(GAP).setChildren(paneTitle, paneSubTitle, gridPane, buttonBar).build();
      stage = new StageBuilder().setMinHeight(HEIGHT).setMinWidth(WIDTH).setTitle(Rosetta.getText("ui.configs.edit.title"))
            .setModality(Modality.APPLICATION_MODAL).setScene(new Scene(vBox)).build();
   }

   private void defineListeners() {
      btnHelp.setOnAction(click -> onHelp());
      btnCancel.setOnAction(click -> onCancel());
      btnOk.setOnAction(click -> onOk());
      descriptionInput.textProperty().addListener((observable, oldValue, newValue) -> validateDescription(newValue));
      choiceBoxEclipticProj.getSelectionModel().selectedIndexProperty().addListener((ov, value, newValue) -> onEclipticChange());
   }

   private GridPane createGridPane() {   // TODO replace with NameDescriptionInputBLock and BaseConfigInputBlock
      GridPane gridPane = new GridPaneBuilder().setPrefHeight(CONFIGDATA_HEIGHT).setHGap(6.0).setVGap(6.0).build();
      gridPane.add(lblDescription, 0, 1, 1, 1);
      gridPane.add(descriptionInput, 1, 1, 1, 1);
      gridPane.add(lblHouseSystem, 0, 2, 1, 1);
      gridPane.add(choiceBoxHouseSystem, 1, 2, 1, 1);
      gridPane.add(lblObserverPosition, 0, 3, 1, 1);
      gridPane.add(choiceBoxObserverPos, 1, 3, 1, 1);
      gridPane.add(lblEclipticProjection, 0, 4, 1, 1);
      gridPane.add(choiceBoxEclipticProj, 1, 4, 1, 1);
      gridPane.add(lblAyanamsha, 0, 5, 1, 1);
      gridPane.add(choiceBoxAyanamsha, 1, 5, 1, 1);
      gridPane.add(lblCelObjects, 0, 6, 1, 1);
      gridPane.add(checkComboBoxCelObjects, 1, 6, 1, 1);
      onEclipticChange();
      return gridPane;
   }

   private CheckComboBox<String> createComboBoxCelObject() {
      CheckComboBox<String> checkComboBox = new CheckComboBoxBuilder().setPrefWidth(DATA_INPUT_WIDTH).build();
      ObservableList<String> celObjects = CelestialObjects.EMPTY.getObservableList();
      indexMappingsCelObjects = CelestialObjects.EMPTY.getIndexMappings();
      for (String name : celObjects) {
         checkComboBox.getItems().add(name);
      }
      for (ConfiguredCelObject celestialObject : config.getAstronConfiguration().getCelObjects()) {
         checkComboBox.getCheckModel().check(indexMappingsCelObjects.getSequenceIdForEnumId(celestialObject.getCelObject().getId()));
      }
      return checkComboBox;
   }

   private void validateDescription(final String newDescription) {
      if (null != newDescription && newDescription.trim().length() > 0) {
         descriptionInput.setText(newDescription);
         descriptionInput.setStyle(INPUT_DEFAULT_STYLE);
      } else {
         descriptionInput.setStyle(INPUT_ERROR_STYLE);
      }
      checkStatus();
   }

   private void checkStatus() {
      String description = (null == descriptionInput.getText() ? "" : descriptionInput.getText());
      boolean inputOk = (description.length() > 0);
      btnOk.setDisable(!inputOk);
      inputStatus = inputOk ? InputStatus.READY : InputStatus.INCOMPLETE;
   }

   private void onEclipticChange() {
      int selectedIndex = choiceBoxEclipticProj.getSelectionModel().getSelectedIndex();
      if (selectedIndex >= 0) {
         int eclipticProjectionSelectedIndex = indexMappingsEclProjections.getEnumIdForSequenceId(selectedIndex);
         choiceBoxAyanamsha.setDisable(eclipticProjectionSelectedIndex != EclipticProjections.SIDEREAL.getId());
      }
   }

   private void onOk() {
      constructConfig();
      pcApi.update(config);
      state.setSelectedConfig(config);
      LOG.info("Saved edited config: " + config.getName());
      stage.close();
   }

   private void onCancel() {
      stage.close();
   }

   private void onHelp() {
      new Help(Rosetta.getHelpText("help.configedit.title"), Rosetta.getHelpText("help.configedit.content"));
   }

   private void constructConfig() {
      config.setDescription(descriptionInput.getText());
      int houseIndex = choiceBoxHouseSystem.getSelectionModel().getSelectedIndex();
      HouseSystems houseSystem = HouseSystems.getSystemForId(indexMappingsHouseSystems.getEnumIdForSequenceId(houseIndex));
      config.getAstronConfiguration().setHouseSystem(houseSystem);
      int observerPosIndex = choiceBoxObserverPos.getSelectionModel().getSelectedIndex();
      ObserverPositions obsPos = ObserverPositions.getObserverPositionForId(indexMappingsObsPositions.getEnumIdForSequenceId(observerPosIndex));
      config.getAstronConfiguration().setObserverPosition(obsPos);
      int eclProjIndex = choiceBoxEclipticProj.getSelectionModel().getSelectedIndex();
      EclipticProjections eclProj = EclipticProjections.getProjectionForId(indexMappingsEclProjections.getEnumIdForSequenceId(eclProjIndex));
      config.getAstronConfiguration().setEclipticProjection(eclProj);
      int ayanamshaIndex = choiceBoxAyanamsha.getSelectionModel().getSelectedIndex();
      Ayanamshas ayanamsha = eclProj == EclipticProjections.SIDEREAL ?
            Ayanamshas.getAyanamshaForId(indexMappingsAyanamshas.getEnumIdForSequenceId(ayanamshaIndex)) : Ayanamshas.NONE;
      config.getAstronConfiguration().setAyanamsha(ayanamsha);

      List<ConfiguredCelObject> celObjects = new ArrayList<>();
      ObservableList<Integer> checkedIndices = checkComboBoxCelObjects.getCheckModel().getCheckedIndices();
      int enumIndex;
      for (int celObjIndex : checkedIndices) {
         enumIndex = indexMappingsCelObjects.getEnumIdForSequenceId(celObjIndex);
         CelestialObjects celestialObject;
         try {
            celestialObject = CelestialObjects.EMPTY.getCelObjectForId(enumIndex);
            String glyph = new GlyphForCelObject().getGlyph(enumIndex);
            celObjects.add(new ConfiguredCelObject(celestialObject, glyph, 100, true));
         } catch (UnknownIdException e) {
            LOG.error("Error retrieving CelestialObjects for id : " + enumIndex + " . Original message : " + e.getMessage() + "Celestial Body will be ignored.");
         }
      }
      config.getAstronConfiguration().setCelObjects(celObjects);
   }

   public InputStatus getInputStatus() {
      return inputStatus;
   }
}
