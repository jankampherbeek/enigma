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
import com.radixpro.enigma.ui.shared.factories.ButtonFactory;
import com.radixpro.enigma.ui.shared.factories.LabelFactory;
import com.radixpro.enigma.ui.shared.factories.PaneFactory;
import com.radixpro.enigma.xchg.api.PersistedConfigurationApi;
import com.radixpro.enigma.xchg.domain.*;
import com.radixpro.enigma.xchg.domain.config.Configuration;
import com.radixpro.enigma.xchg.domain.config.ConfiguredCelObject;
import com.radixpro.enigma.xchg.domain.helpers.IndexMappingsList;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
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
import static com.radixpro.enigma.ui.shared.StyleDictionary.*;

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

   private final Stage stage;
   private final Rosetta rosetta;
   private final Configuration config;
   private InputStatus inputStatus = InputStatus.READY;
   private TextField descriptionInput;
   private ChoiceBox<String> observerPosSelection;
   private ChoiceBox<String> houseSystemSelection;
   private ChoiceBox<String> eclipticProjSelection;
   private ChoiceBox<String> ayanamshaSelection;
   private CheckComboBox<String> celObjectsSelection;
   private Button btnOk;
   private IndexMappingsList indexMappingsEclProjections;
   private IndexMappingsList indexMappingsObsPositions;
   private IndexMappingsList indexMappingsHouseSystems;
   private IndexMappingsList indexMappingsCelObjects;
   private IndexMappingsList indexMappingsAyanamshas;

   public ConfigEdit(final Configuration config, final Stage stage, final Rosetta rosetta) {
      this.rosetta = checkNotNull(rosetta);
      this.config = checkNotNull(config);
      this.stage = checkNotNull(stage);
      showEditableConfiguration();
      LOG.info("ConfigEdit initialized for config: " + config.getName());
   }

   private void showEditableConfiguration() {
      stage.setMinHeight(HEIGHT);
      stage.setMinWidth(WIDTH);
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setTitle(rosetta.getText("ui.configs.edit.title"));
      stage.setScene(new Scene(createVBox()));
      stage.showAndWait();
   }

   private VBox createVBox() {
      final VBox vBox = new VBox();
      vBox.setPadding(new Insets(GAP, GAP, GAP, GAP));
      vBox.getStylesheets().add(STYLESHEET);
      vBox.setPrefWidth(WIDTH);
      vBox.setPrefHeight(HEIGHT);
      vBox.getChildren().add(0, createPaneTitle());
      vBox.getChildren().add(1, createPaneSubTitle());
      vBox.getChildren().add(2, createGridPane());
      vBox.getChildren().add(3, createButtonBar());
      return vBox;
   }

   private Pane createPaneTitle() {
      final Pane pane = PaneFactory.createPane(TITLE_HEIGHT, WIDTH, "titlepane");
      pane.getChildren().add(LabelFactory.createLabel(rosetta.getText("ui.configs.edit.title"), "titletext", WIDTH));
      return pane;
   }

   private Pane createPaneSubTitle() {
      final Pane pane = PaneFactory.createPane(SUBTITLE_HEIGHT, WIDTH, "subtitlepane");
      pane.getChildren().add(LabelFactory.createLabel(config.getName(), "subtitletext", WIDTH));
      return pane;
   }

   private GridPane createGridPane() {
      GridPane gridPane = new GridPane();
      gridPane.setPrefHeight(CONFIGDATA_HEIGHT);
      gridPane.setHgap(6.0);
      gridPane.setVgap(6.0);

      gridPane.add(LabelFactory.createLabel(rosetta.getText("ui.general.description"), DATA_TEXT_WIDTH), 0, 1, 1, 1);
      descriptionInput = new TextField();
      descriptionInput.setPrefWidth(DATA_INPUT_WIDTH);
      descriptionInput.setText(config.getDescription());
      descriptionInput.textProperty().addListener((observable, oldValue, newValue) -> validateDescription(newValue));
      gridPane.add(descriptionInput, 1, 1, 1, 1);
      gridPane.add(LabelFactory.createLabel(rosetta.getText("ui.general.housesystem"), DATA_TEXT_WIDTH), 0, 2, 1, 1);
      houseSystemSelection = createHouseSystemChoiceBox();
      gridPane.add(houseSystemSelection, 1, 2, 1, 1);
      gridPane.add(LabelFactory.createLabel(rosetta.getText("ui.general.observerposition"), DATA_TEXT_WIDTH), 0, 3, 1, 1);
      observerPosSelection = createObserverPosChoiceBox();
      gridPane.add(observerPosSelection, 1, 3, 1, 1);
      gridPane.add(LabelFactory.createLabel(rosetta.getText("ui.general.eclipticprojection"), DATA_TEXT_WIDTH), 0, 4, 1, 1);
      eclipticProjSelection = createEclipticProjChoiceBox();
      gridPane.add(eclipticProjSelection, 1, 4, 1, 1);
      gridPane.add(LabelFactory.createLabel(rosetta.getText("ui.general.ayanamsha"), DATA_TEXT_WIDTH), 0, 5, 1, 1);
      ayanamshaSelection = createAyanamshaSelection();
      gridPane.add(ayanamshaSelection, 1, 5, 1, 1);
      gridPane.add(LabelFactory.createLabel(rosetta.getText("ui.general.celobjects"), DATA_TEXT_WIDTH), 0, 6, 1, 1);
      celObjectsSelection = createCelObjectComboBox();
      gridPane.add(celObjectsSelection, 1, 6, 1, 1);
      onEclipticChange();
      return gridPane;
   }

   private ChoiceBox<String> createHouseSystemChoiceBox() {
      ChoiceBox<String> choiceBox = new ChoiceBox<>();
      choiceBox.setPrefWidth(DATA_INPUT_WIDTH);
      ObservableList<String> houses = HouseSystems.EMPTY.getObservableList();
      indexMappingsHouseSystems = HouseSystems.EMPTY.getIndexMappings();
      choiceBox.setItems(houses);
      choiceBox.getSelectionModel().select(indexMappingsHouseSystems.getSequenceIdForEnumId(config.getAstronConfiguration().getHouseSystem().getId()));
      return choiceBox;
   }

   private ChoiceBox<String> createObserverPosChoiceBox() {
      ChoiceBox<String> choiceBox = new ChoiceBox<>();
      choiceBox.setPrefWidth(DATA_INPUT_WIDTH);
      ObservableList<String> observerPositions = ObserverPositions.EMPTY.getObservableList();
      indexMappingsObsPositions = ObserverPositions.EMPTY.getIndexMappings();
      choiceBox.setItems(observerPositions);
      choiceBox.getSelectionModel().select(indexMappingsObsPositions.getSequenceIdForEnumId(config.getAstronConfiguration().getObserverPosition().getId()));
      return choiceBox;
   }

   private ChoiceBox<String> createEclipticProjChoiceBox() {
      ChoiceBox<String> choiceBox = new ChoiceBox<>();
      choiceBox.setPrefWidth(DATA_INPUT_WIDTH);
      ObservableList<String> eclipticProjections = EclipticProjections.EMPTY.getObservableList();
      indexMappingsEclProjections = EclipticProjections.EMPTY.getIndexMappings();
      choiceBox.setItems(eclipticProjections);
      choiceBox.getSelectionModel().select(indexMappingsEclProjections.getSequenceIdForEnumId(config.getAstronConfiguration().getEclipticProjection().getId()));
      choiceBox.getSelectionModel().selectedIndexProperty().addListener((ov, value, newValue) -> onEclipticChange());
      return choiceBox;
   }

   private ChoiceBox<String> createAyanamshaSelection() {
      ChoiceBox<String> choiceBox = new ChoiceBox<>(Ayanamshas.NONE.getObservableList());
      indexMappingsAyanamshas = Ayanamshas.EMPTY.getIndexMappings();
      choiceBox.setPrefWidth(DATA_INPUT_WIDTH);
      choiceBox.getSelectionModel().select(indexMappingsAyanamshas.getSequenceIdForEnumId(config.getAstronConfiguration().getAyanamsha().getId()));
      return choiceBox;
   }


   private CheckComboBox<String> createCelObjectComboBox() {
      CheckComboBox<String> checkComboBox = new CheckComboBox<>();
      checkComboBox.setPrefWidth(DATA_INPUT_WIDTH);
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

   private ButtonBar createButtonBar() {
      ButtonBar buttonBar = new ButtonBar();
      Button btnHelp = ButtonFactory.createButton(rosetta.getText("ui.shared.btn.help"), false);
      Button btnCancel = ButtonFactory.createButton(rosetta.getText("ui.shared.btn.cancel"), false);
      btnOk = ButtonFactory.createButton(rosetta.getText("ui.shared.btn.ok"), false);

      btnHelp.setOnAction(click -> onHelp());
      btnCancel.setOnAction(click -> onCancel());
      btnOk.setOnAction(click -> onOk());

      buttonBar.getButtons().add(btnHelp);
      buttonBar.getButtons().add(btnCancel);
      buttonBar.getButtons().add(btnOk);

      return buttonBar;
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
      int selectedIndex = eclipticProjSelection.getSelectionModel().getSelectedIndex();
      if (selectedIndex >= 0) {
         int eclipticProjectionSelectedIndex = indexMappingsEclProjections.getEnumIdForSequenceId(selectedIndex);
         ayanamshaSelection.setDisable(eclipticProjectionSelectedIndex != EclipticProjections.SIDEREAL.getId());
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
      int houseIndex = houseSystemSelection.getSelectionModel().getSelectedIndex();
      HouseSystems houseSystem = null;
      try {
         houseSystem = HouseSystems.EMPTY.getSystemForId(indexMappingsHouseSystems.getEnumIdForSequenceId(houseIndex));
      } catch (UnknownIdException e) {
         houseSystem = HouseSystems.WHOLESIGN;
         LOG.error("Could not find housesystem when constructing config, defined HouseSystems.WHOLESIGN instead. Original message : " + e.getMessage());
      }
      config.getAstronConfiguration().setHouseSystem(houseSystem);
      int observerPosIndex = observerPosSelection.getSelectionModel().getSelectedIndex();
      ObserverPositions obsPos = null;
      try {
         obsPos = ObserverPositions.EMPTY.getObserverPositionForId(indexMappingsObsPositions.getEnumIdForSequenceId(observerPosIndex));
      } catch (UnknownIdException e) {
         obsPos = ObserverPositions.GEOCENTRIC;
         LOG.error("Could not find observer position when constructing config, defined ObserverPositions.GEOCENTRIC instead. Original message : " + e.getMessage());
      }
      config.getAstronConfiguration().setObserverPosition(obsPos);
      int eclProjIndex = eclipticProjSelection.getSelectionModel().getSelectedIndex();
      EclipticProjections eclProj = null;
      try {
         eclProj = EclipticProjections.EMPTY.getProjectionForId(indexMappingsEclProjections.getEnumIdForSequenceId(eclProjIndex));
      } catch (UnknownIdException e) {
         eclProj = EclipticProjections.TROPICAL;
         LOG.error("Could not find ecliptic projection when constructing config, defined EclipticProjections.TROPICAL instead. Original message : " + e.getMessage());
      }
      config.getAstronConfiguration().setEclipticProjection(eclProj);
      int ayanamshaIndex = ayanamshaSelection.getSelectionModel().getSelectedIndex();
      Ayanamshas ayanamsha = null;
      try {
         ayanamsha = eclProj == EclipticProjections.SIDEREAL ? Ayanamshas.NONE.getAyanamshaForId(indexMappingsAyanamshas.getEnumIdForSequenceId(ayanamshaIndex)) : Ayanamshas.NONE;
      } catch (UnknownIdException e) {
            ayanamsha = Ayanamshas.LAHIRI;
            LOG.error("Could not find ayanamsha when constructing config, defined Ayanamshas.LAHIRI instead. Original message : " + e.getMessage());
      }
      config.getAstronConfiguration().setAyanamsha(ayanamsha);

      List<ConfiguredCelObject> celObjects = new ArrayList<>();
      ObservableList<Integer> checkedIndices = celObjectsSelection.getCheckModel().getCheckedIndices();
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
