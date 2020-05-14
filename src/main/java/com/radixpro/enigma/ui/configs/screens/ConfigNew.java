/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.configs.screens;

import com.radixpro.enigma.shared.Rosetta;
import com.radixpro.enigma.ui.shared.Help;
import com.radixpro.enigma.ui.shared.InputStatus;
import com.radixpro.enigma.ui.shared.factories.ButtonFactory;
import com.radixpro.enigma.ui.shared.factories.LabelFactory;
import com.radixpro.enigma.ui.shared.factories.PaneFactory;
import com.radixpro.enigma.ui.shared.validation.ValidatedConfigName;
import com.radixpro.enigma.xchg.api.PersistedConfigurationApi;
import com.radixpro.enigma.xchg.domain.config.Configuration;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.radixpro.enigma.ui.shared.StyleDictionary.*;

public class ConfigNew {

   private static final double WIDTH = 400.0;
   private static final double HEIGHT = 300.0;
   private static final double INSTRUCTION_HEIGHT = 30.0;
   private static final double DATA_HEIGHT = 30.0;
   private static final double DATA_TEXT_WIDTH = 100.0;
   private static final double DATA_INPUT_WIDTH = 300.0;
   private static final double TITLE_HEIGHT = 45.0;
   private static final double GAP = 6.0;

   private final Stage stage;
   private final Rosetta rosetta;
   private final Configuration config;
   private final PersistedConfigurationApi api;
   private TextField nameInput;
   private TextField descriptionInput;
   private ValidatedConfigName valName;
   private InputStatus inputStatus = InputStatus.INCOMPLETE;
   private Button btnOk;
   private long newConfigId;

   public ConfigNew(final Configuration config, final Stage stage, final Rosetta rosetta, final PersistedConfigurationApi api) {
      this.rosetta = checkNotNull(rosetta);
      this.config = checkNotNull(config);
      this.stage = checkNotNull(stage);
      this.api = checkNotNull(api);
      showNewNameDescription();
   }

   private void showNewNameDescription() {
      stage.setMinHeight(HEIGHT);
      stage.setMinWidth(WIDTH);
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setTitle(rosetta.getText("ui.configs.new.title"));
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
      vBox.getChildren().add(2, createPaneInstruction());
      vBox.getChildren().add(3, createHBoxName());
      vBox.getChildren().add(4, createHBoxDescription());
      vBox.getChildren().add(5, createButtonBar());
      return vBox;
   }

   private Pane createPaneTitle() {
      final Pane pane = PaneFactory.createPane(TITLE_HEIGHT, WIDTH, "titlepane");
      pane.getChildren().add(LabelFactory.createLabel(rosetta.getText("ui.configs.new.title"), "titletext", WIDTH));
      return pane;
   }

   private Pane createPaneSubTitle() {
      final Pane pane = PaneFactory.createPane(TITLE_HEIGHT, WIDTH, "subtitlepane");
      final String text = rosetta.getText("ui.configs.new.copyfrom") + " " + config.getName();
      pane.getChildren().add(LabelFactory.createLabel(text, "subtitletext", WIDTH));
      return pane;
   }

   private Pane createPaneInstruction() {
      final Pane pane = PaneFactory.createPane(INSTRUCTION_HEIGHT, WIDTH);
      pane.getChildren().add(LabelFactory.createLabel(rosetta.getText("ui.configs.new.instruction")));
      return pane;
   }

   private HBox createHBoxName() {
      final HBox hbox = new HBox();
      hbox.setPrefWidth(WIDTH);
      hbox.setPrefHeight(DATA_HEIGHT);
      hbox.getChildren().add(0, LabelFactory.createLabel(rosetta.getText("ui.general.name"), DATA_TEXT_WIDTH));
      nameInput = new TextField();
      nameInput.textProperty().addListener((observable, oldValue, newValue) -> validateName(newValue));
      nameInput.setPrefWidth(DATA_INPUT_WIDTH);
      hbox.getChildren().add(1, nameInput);
      return hbox;
   }

   private HBox createHBoxDescription() {
      final HBox hbox = new HBox();
      hbox.setPrefWidth(WIDTH);
      hbox.setPrefHeight(DATA_HEIGHT);
      hbox.getChildren().add(0, LabelFactory.createLabel(rosetta.getText("ui.general.description"), DATA_TEXT_WIDTH));
      descriptionInput = new TextField();
      descriptionInput.textProperty().addListener((observable, oldValue, newValue) -> validateDescription(newValue));
      descriptionInput.setPrefWidth(DATA_INPUT_WIDTH);
      hbox.getChildren().add(1, descriptionInput);
      return hbox;
   }

   private ButtonBar createButtonBar() {
      final ButtonBar buttonBar = new ButtonBar();
      btnOk = ButtonFactory.createButton(rosetta.getText("ui.shared.btn.ok"), true);
      Button btnHelp = ButtonFactory.createButton(rosetta.getText("ui.shared.btn.help"), false);
      Button btnCancel = ButtonFactory.createButton(rosetta.getText("ui.shared.btn.cancel"), false);

      btnOk.setOnAction(click -> onOk());
      btnHelp.setOnAction(click -> onHelp());
      btnCancel.setOnAction(click -> onCancel());

      buttonBar.getButtons().add(btnOk);
      buttonBar.getButtons().add(btnHelp);
      buttonBar.getButtons().add(btnCancel);

      return buttonBar;
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
