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
import com.radixpro.enigma.references.InputStatus;
import com.radixpro.enigma.ui.creators.*;
import com.radixpro.enigma.ui.shared.Help;
import com.radixpro.enigma.ui.validators.ConfigNameValidator;
import com.radixpro.enigma.xchg.api.PersistedConfigurationApi;
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
import org.jetbrains.annotations.NotNull;

import static com.radixpro.enigma.ui.shared.UiDictionary.INPUT_DEFAULT_STYLE;
import static com.radixpro.enigma.ui.shared.UiDictionary.INPUT_ERROR_STYLE;

public class ConfigNew {

   private static final double WIDTH = 400.0;
   private static final double HEIGHT = 300.0;
   private static final double INSTRUCTION_HEIGHT = 30.0;
   private static final double DATA_HEIGHT = 30.0;
   private static final double DATA_TEXT_WIDTH = 100.0;
   private static final double DATA_INPUT_WIDTH = 300.0;
   private static final double TITLE_HEIGHT = 45.0;
   private static final double GAP = 6.0;

   private int newConfigId;
   private final ConfigNameValidator valName;
   private InputStatus inputStatus = InputStatus.INCOMPLETE;
   private final Rosetta rosetta;
   private final SessionState state;
   private final PersistedConfigurationApi api;
   private boolean nameValid = false;
   private Configuration config;
   private Stage stage;
   private TextField nameInput;
   private TextField descriptionInput;

   private Button btnOk;
   private Button btnHelp;
   private Button btnCancel;

   public ConfigNew(@NotNull final PersistedConfigurationApi api, @NotNull final ConfigNameValidator nameValidator) {
      this.rosetta = Rosetta.getRosetta();
      this.state = SessionState.INSTANCE;
      this.api = api;
      this.valName = nameValidator;
   }

   public void show() {
      this.config = state.getSelectedConfig();
      populateStage();
      defineListeners();
      stage.showAndWait();
   }

   private void populateStage() {
      Label lblTitle = new LabelBuilder("ui.configs.new.title").setPrefWidth(WIDTH).setStyleClass("titletext").build();
      Label lblSubTitle = new LabelBuilder("").setText(rosetta.getText("ui.configs.new.copyfrom") + " " + config.getName()).setPrefWidth(WIDTH)
            .setStyleClass("subtitletext").build();
      Label lblInstruction = new LabelBuilder("ui.configs.new.instruction").build();
      Label lblName = new LabelBuilder("ui.general.name").setPrefWidth(DATA_TEXT_WIDTH).build();
      Label lblDescription = new LabelBuilder("ui.general.description").setPrefWidth(DATA_TEXT_WIDTH).build();
      btnOk = new ButtonBuilder("ui.shared.btn.ok").setDisabled(true).build();
      btnHelp = new ButtonBuilder("ui.shared.btn.help").setDisabled(false).build();
      btnCancel = new ButtonBuilder("ui.shared.btn.exit").setDisabled(false).build();
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
      Configuration editedConfig = createEditedConfig();
      newConfigId = api.insert(editedConfig);
      editedConfig.setId(newConfigId);
      state.setSelectedConfig(editedConfig);
      stage.close();
   }

   private void onHelp() {
      new Help(rosetta.getHelpText("help.confignew.title"), rosetta.getHelpText("help.confignew.content"));
   }

   private Configuration createEditedConfig() {
      int parentId = config.getId();
      Configuration newConfig = api.read(parentId).get(0);
      newConfig.setId(-1);   // will be replaced with sequence
      newConfig.setParentId(parentId);
      newConfig.setName(nameInput.getText().trim());
      newConfig.setDescription(descriptionInput.getText());
      return newConfig;
   }

   private void validateName(final String newName) {
      nameValid = valName.validate(newName);
      nameInput.setStyle(nameValid ? INPUT_DEFAULT_STYLE : INPUT_ERROR_STYLE);  //textinput
      checkStatus();
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
      boolean inputOk = (nameValid && description.length() > 0);
      btnOk.setDisable(!inputOk);
      if (inputOk) inputStatus = InputStatus.READY;
   }

   public int getNewConfigId() {
      return newConfigId;
   }

   public InputStatus getInputStatus() {
      return inputStatus;
   }
}
