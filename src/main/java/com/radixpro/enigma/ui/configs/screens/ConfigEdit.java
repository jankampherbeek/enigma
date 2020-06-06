/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.configs.screens;

import com.radixpro.enigma.shared.Rosetta;
import com.radixpro.enigma.shared.exceptions.UnknownIdException;
import com.radixpro.enigma.ui.charts.screens.helpers.GlyphForCelObject;
import com.radixpro.enigma.ui.shared.Help;
import com.radixpro.enigma.ui.shared.InputStatus;
import com.radixpro.enigma.ui.shared.factories.*;
import com.radixpro.enigma.xchg.api.PersistedConfigurationApi;
import com.radixpro.enigma.xchg.domain.*;
import com.radixpro.enigma.xchg.domain.config.Configuration;
import com.radixpro.enigma.xchg.domain.config.ConfiguredCelObject;
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

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.radixpro.enigma.ui.shared.StyleDictionary.INPUT_DEFAULT_STYLE;
import static com.radixpro.enigma.ui.shared.StyleDictionary.INPUT_ERROR_STYLE;

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
   private final Rosetta rosetta;
   private final Configuration config;
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

   public ConfigEdit(final Configuration config, final Rosetta rosetta) {
      this.rosetta = checkNotNull(rosetta);
      this.config = checkNotNull(config);
      populateStage();
      defineListeners();
      stage.showAndWait();
      LOG.info("ConfigEdit initialized for config: " + config.getName());
   }

   private void populateStage() {
      btnHelp = new ButtonBuilder(rosetta.getText("ui.shared.btn.help")).setDisabled(false).build();
      btnCancel = new ButtonBuilder(rosetta.getText("ui.shared.btn.cancel")).setDisabled(false).build();
      btnOk = new ButtonBuilder(rosetta.getText("ui.shared.btn.ok")).setDisabled(false).build();
      ButtonBar buttonBar = new ButtonBarBuilder().setButtons(btnHelp, btnCancel, btnOk).build();
      Label lblTitle = new LabelBuilder(rosetta.getText("ui.configs.edit.title")).setPrefWidth(WIDTH).setStyleClass("titletext").build();
      Label lblSubTitle = new LabelBuilder(config.getName()).setPrefWidth(WIDTH).setStyleClass("subtitletext").build();
      lblDescription = new LabelBuilder(rosetta.getText("ui.general.description")).setPrefWidth(DATA_TEXT_WIDTH).build();
      lblHouseSystem = new LabelBuilder(rosetta.getText("ui.general.housesystem")).setPrefWidth(DATA_TEXT_WIDTH).build();
      lblAyanamsha = new LabelBuilder(rosetta.getText("ui.general.ayanamsha")).setPrefWidth(DATA_TEXT_WIDTH).build();
      lblObserverPosition = new LabelBuilder(rosetta.getText("ui.general.observerposition")).setPrefWidth(DATA_TEXT_WIDTH).build();
      lblEclipticProjection = new LabelBuilder(rosetta.getText("ui.general.eclipticprojection")).setPrefWidth(DATA_TEXT_WIDTH).build();
      lblCelObjects = new LabelBuilder(rosetta.getText("ui.general.celobjects")).setPrefWidth(DATA_TEXT_WIDTH).build();
      Pane paneTitle = new PaneBuilder().setWidth(WIDTH).setHeight(TITLE_HEIGHT).setStyleClass("titlepane").setChildren(lblTitle).build();
      Pane paneSubTitle = new PaneBuilder().setWidth(WIDTH).setHeight(SUBTITLE_HEIGHT).setStyleClass("subtitleplane").setChildren(lblSubTitle).build();
      descriptionInput = new TextFieldBuilder().setPrefWidth(DATA_INPUT_WIDTH).setText(config.getDescription()).build();
      choiceBoxHouseSystem = new ChoiceBoxBuilder().setPrefWidth(DATA_INPUT_WIDTH).setItems(HouseSystems.EMPTY.getObservableList()).build();
      indexMappingsHouseSystems = HouseSystems.EMPTY.getIndexMappings();
      choiceBoxHouseSystem.getSelectionModel().select(indexMappingsHouseSystems.getSequenceIdForEnumId(config.getAstronConfiguration().getHouseSystem().getId()));
      choiceBoxObserverPos = new ChoiceBoxBuilder().setPrefWidth(DATA_INPUT_WIDTH).setItems(ObserverPositions.EMPTY.getObservableList()).build();
      indexMappingsObsPositions = ObserverPositions.EMPTY.getIndexMappings();
      choiceBoxObserverPos.getSelectionModel().select(indexMappingsObsPositions.getSequenceIdForEnumId(config.getAstronConfiguration().getObserverPosition().getId()));
      choiceBoxEclipticProj = new ChoiceBoxBuilder().setPrefWidth(DATA_INPUT_WIDTH).setItems(EclipticProjections.EMPTY.getObservableList()).build();
      indexMappingsEclProjections = EclipticProjections.EMPTY.getIndexMappings();
      choiceBoxEclipticProj.getSelectionModel().select(indexMappingsEclProjections.getSequenceIdForEnumId(config.getAstronConfiguration().getEclipticProjection().getId()));
      choiceBoxAyanamsha = new ChoiceBoxBuilder().setPrefWidth(DATA_INPUT_WIDTH).setItems(Ayanamshas.EMPTY.getObservableList()).build();
      indexMappingsAyanamshas = Ayanamshas.EMPTY.getIndexMappings();
      choiceBoxAyanamsha.getSelectionModel().select(indexMappingsAyanamshas.getSequenceIdForEnumId(config.getAstronConfiguration().getAyanamsha().getId()));
      checkComboBoxCelObjects = createComboBoxCelObject();
      checkComboBoxCelObjects = createComboBoxCelObject();
      GridPane gridPane = createGridPane();
      VBox vBox = new VBoxBuilder().setWidth(WIDTH).setHeight(HEIGHT).setPadding(GAP).setChildren(paneTitle, paneSubTitle, gridPane, buttonBar).build();
      stage = new StageBuilder().setMinHeight(HEIGHT).setMinWidth(WIDTH).setTitle(rosetta.getText("ui.configs.edit.title"))
            .setModality(Modality.APPLICATION_MODAL).setScene(new Scene(vBox)).build();
   }

   private void defineListeners() {
      btnHelp.setOnAction(click -> onHelp());
      btnCancel.setOnAction(click -> onCancel());
      btnOk.setOnAction(click -> onOk());
      descriptionInput.textProperty().addListener((observable, oldValue, newValue) -> validateDescription(newValue));
      choiceBoxEclipticProj.getSelectionModel().selectedIndexProperty().addListener((ov, value, newValue) -> onEclipticChange());
   }

   private GridPane createGridPane() {
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
      String tempDescription = newDescription;
      if (null != newDescription && tempDescription.trim().length() > 0) {
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
      PersistedConfigurationApi api = new PersistedConfigurationApi();
      api.update(config);
      LOG.info("Saved edited config: " + config.getName());
      stage.close();
   }

   private void onCancel() {
      stage.close();
   }

   private void onHelp() {
      new Help(rosetta.getHelpText("help.configedit.title"), rosetta.getHelpText("help.configedit.content"));
   }

   private void constructConfig() {
      config.setDescription(descriptionInput.getText());
      int houseIndex = choiceBoxHouseSystem.getSelectionModel().getSelectedIndex();
      HouseSystems houseSystem;
      try {
         houseSystem = HouseSystems.EMPTY.getSystemForId(indexMappingsHouseSystems.getEnumIdForSequenceId(houseIndex));
      } catch (UnknownIdException e) {
         houseSystem = HouseSystems.WHOLESIGN;
         LOG.error("Could not find housesystem when constructing config, defined HouseSystems.WHOLESIGN instead. Original message : " + e.getMessage());
      }
      config.getAstronConfiguration().setHouseSystem(houseSystem);
      int observerPosIndex = choiceBoxObserverPos.getSelectionModel().getSelectedIndex();
      ObserverPositions obsPos = null;
      try {
         obsPos = ObserverPositions.EMPTY.getObserverPositionForId(indexMappingsObsPositions.getEnumIdForSequenceId(observerPosIndex));
      } catch (UnknownIdException e) {
         obsPos = ObserverPositions.GEOCENTRIC;
         LOG.error("Could not find observer position when constructing config, defined ObserverPositions.GEOCENTRIC instead. Original message : " + e.getMessage());
      }
      config.getAstronConfiguration().setObserverPosition(obsPos);
      int eclProjIndex = choiceBoxEclipticProj.getSelectionModel().getSelectedIndex();
      EclipticProjections eclProj = null;
      try {
         eclProj = EclipticProjections.EMPTY.getProjectionForId(indexMappingsEclProjections.getEnumIdForSequenceId(eclProjIndex));
      } catch (UnknownIdException e) {
         eclProj = EclipticProjections.TROPICAL;
         LOG.error("Could not find ecliptic projection when constructing config, defined EclipticProjections.TROPICAL instead. Original message : " + e.getMessage());
      }
      config.getAstronConfiguration().setEclipticProjection(eclProj);
      int ayanamshaIndex = choiceBoxAyanamsha.getSelectionModel().getSelectedIndex();
      Ayanamshas ayanamsha = null;
      try {
         ayanamsha = eclProj == EclipticProjections.SIDEREAL ? Ayanamshas.NONE.getAyanamshaForId(indexMappingsAyanamshas.getEnumIdForSequenceId(ayanamshaIndex)) : Ayanamshas.NONE;
      } catch (UnknownIdException e) {
            ayanamsha = Ayanamshas.LAHIRI;
            LOG.error("Could not find ayanamsha when constructing config, defined Ayanamshas.LAHIRI instead. Original message : " + e.getMessage());
      }
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
