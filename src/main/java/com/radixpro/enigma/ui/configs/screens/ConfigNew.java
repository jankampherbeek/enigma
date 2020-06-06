/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.configs.screens;

import com.radixpro.enigma.shared.Rosetta;
import com.radixpro.enigma.ui.shared.Help;
import com.radixpro.enigma.ui.shared.InputStatus;
import com.radixpro.enigma.ui.shared.factories.*;
import com.radixpro.enigma.ui.shared.validation.ValidatedConfigName;
import com.radixpro.enigma.xchg.api.PersistedConfigurationApi;
import com.radixpro.enigma.xchg.domain.config.Configuration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.radixpro.enigma.ui.shared.StyleDictionary.INPUT_DEFAULT_STYLE;
import static com.radixpro.enigma.ui.shared.StyleDictionary.INPUT_ERROR_STYLE;

public class ConfigNew {

   private static final double WIDTH = 400.0;
   private static final double HEIGHT = 300.0;
   private static final double INSTRUCTION_HEIGHT = 30.0;
   private static final double DATA_HEIGHT = 30.0;
   private static final double DATA_TEXT_WIDTH = 100.0;
   private static final double DATA_INPUT_WIDTH = 300.0;
   private static final double TITLE_HEIGHT = 45.0;
   private static final double GAP = 6.0;

   private long newConfigId;
   private ValidatedConfigName valName;
   private InputStatus inputStatus = InputStatus.INCOMPLETE;
   private final Rosetta rosetta;
   private final Configuration config;
   private final PersistedConfigurationApi api;
   private Stage stage;
   private TextField nameInput;
   private TextField descriptionInput;

   private Button btnOk;
   private Button btnHelp;
   private Button btnCancel;

   public ConfigNew(final Configuration config, final Rosetta rosetta, final PersistedConfigurationApi api) {
      this.rosetta = checkNotNull(rosetta);
      this.config = checkNotNull(config);
      this.api = checkNotNull(api);
      populateStage();
      defineListeners();
      stage.showAndWait();
   }

   private void populateStage() {
      Label lblTitle = new LabelBuilder(rosetta.getText("ui.configs.new.title")).setPrefWidth(WIDTH).setStyleClass("titletext").build();
      Label lblSubTitle = new LabelBuilder(rosetta.getText("ui.configs.new.copyfrom") + " " + config.getName()).setPrefWidth(WIDTH)
            .setStyleClass("subtitletext").build();
      Label lblInstruction = new LabelBuilder(rosetta.getText("ui.configs.new.instruction")).build();
      Label lblName = new LabelBuilder(rosetta.getText("ui.general.name")).setPrefWidth(DATA_TEXT_WIDTH).build();
      Label lblDescription = new LabelBuilder(rosetta.getText("ui.general.description")).setPrefWidth(DATA_TEXT_WIDTH).build();
      btnOk = new ButtonBuilder(rosetta.getText("ui.shared.btn.ok")).setDisabled(true).build();
      btnHelp = new ButtonBuilder(rosetta.getText("ui.shared.btn.help")).setDisabled(false).build();
      btnCancel = new ButtonBuilder(rosetta.getText("ui.shared.btn.exit")).setDisabled(false).build();
      ButtonBar buttonBar = new ButtonBarBuilder().setButtons(btnOk, btnHelp, btnCancel).build();
      nameInput = new TextFieldBuilder().setPrefWidth(DATA_INPUT_WIDTH).build();
      descriptionInput = new TextFieldBuilder().setPrefWidth(DATA_INPUT_WIDTH).build();
      Pane paneTitle = new PaneBuilder().setWidth(WIDTH).setHeight(TITLE_HEIGHT).setStyleClass("titlepane").setChildren(lblTitle).build();
      Pane paneSubTitle = new PaneBuilder().setWidth(WIDTH).setHeight(TITLE_HEIGHT).setStyleClass("subtitlepane").setChildren(lblSubTitle).build();
      Pane paneInstruction = new PaneBuilder().setWidth(WIDTH).setHeight(INSTRUCTION_HEIGHT).setChildren(lblInstruction).build();
      HBox hBoxName = new HBoxBuilder().setPrefWidth(WIDTH).setPrefHeight(DATA_HEIGHT).setChildren(lblName, nameInput).build();
      HBox hBoxDescription = new HBoxBuilder().setPrefWidth(WIDTH).setPrefHeight(DATA_HEIGHT).setChildren(lblDescription, descriptionInput).build();
      VBox vBox = new VBoxBuilder().setWidth(WIDTH).setHeight(HEIGHT).setPadding(GAP)
            .setChildren(paneTitle, paneSubTitle, paneInstruction, hBoxName, hBoxDescription, buttonBar).build();
      stage = new StageBuilder().setMinHeight(HEIGHT).setMinWidth(WIDTH).setModality(Modality.APPLICATION_MODAL)
            .setTitle(rosetta.getText("ui.configs.new.title")).setScene(new Scene(vBox)).build();
   }

   private void defineListeners() {
      btnOk.setOnAction(click -> onOk());
      btnHelp.setOnAction(click -> onHelp());
      btnCancel.setOnAction(click -> onCancel());
      nameInput.textProperty().addListener((observable, oldValue, newValue) -> validateName(newValue));
      descriptionInput.textProperty().addListener((observable, oldValue, newValue) -> validateDescription(newValue));
   }

   private void onCancel() {
      inputStatus = InputStatus.CANCELLED;
      stage.close();
   }

   private void onOk() {
      api.insert(createEditedConfig());
      stage.close();
   }

   private void onHelp() {
      new Help(rosetta.getHelpText("help.confignew.title"), rosetta.getHelpText("help.confignew.content"));
   }

   private Configuration createEditedConfig() {
      long parentId = config.getId();
      Configuration newConfig = api.read((int) parentId).get(0);
      newConfigId = api.getMaxId() + 1L;
      newConfig.setId(newConfigId);
      newConfig.setParentId(parentId);
      newConfig.setName(valName.getNameText());
      newConfig.setDescription(descriptionInput.getText());
      return newConfig;
   }

   private void validateName(final String newName) {
      valName = new ValidatedConfigName(newName);
      nameInput.setText(valName.getNameText());
      nameInput.setStyle(valName.isValidated() ? INPUT_DEFAULT_STYLE : INPUT_ERROR_STYLE);  //textinput
      checkStatus();
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
      boolean inputOk = (null != valName && valName.isValidated() && description.length() > 0);
      btnOk.setDisable(!inputOk);
      if (inputOk) inputStatus = InputStatus.READY;
   }

   public long getNewConfigId() {
      return newConfigId;
   }

   public InputStatus getInputStatus() {
      return inputStatus;
   }
}
