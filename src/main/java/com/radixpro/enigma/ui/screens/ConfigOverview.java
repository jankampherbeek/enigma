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
import com.radixpro.enigma.share.api.PropertyApi;
import com.radixpro.enigma.shared.Property;
import com.radixpro.enigma.ui.creators.*;
import com.radixpro.enigma.ui.shared.Help;
import com.radixpro.enigma.ui.shared.presentationmodel.PresentableConfiguration;
import com.radixpro.enigma.xchg.api.PersistedConfigurationApi;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * Overview of configurations with the possibility to perform actions on these configurations.
 */
public class ConfigOverview {
   private static final Logger LOG = Logger.getLogger(ConfigOverview.class);
   private static final double WIDTH = 700.0;
   private static final double HEIGHT = 600.0;
   private static final double TITLE_HEIGHT = 45.0;
   private static final double BTN_PANE_HEIGHT = 30.0;
   private static final double TV_HEIGHT = 400.0;
   private static final double INSTRUCTION_HEIGHT = 45.0;
   private static final double SEPARATOR_HEIGHT = 20.0;
   private static final double GAP = 6.0;
   private final PersistedConfigurationApi configApi;
   private final ConfigNew configNew;
   private final ConfigEdit configEdit;
   private final ConfigDetails configDetails;
   private final PropertyApi propApi;
   private final SessionState state;
   private ObservableList<PresentableConfiguration> selectedItems;
   private boolean selectionChanged = false;
   private Stage stage;
   private Button btnSelect;
   private Button btnNew;
   private Button btnDetails;
   private Button btnEdit;
   private Button btnDelete;
   private Button btnHelp;
   private Button btnExit;
   private TableView<PresentableConfiguration> tableView;

   public ConfigOverview(@NotNull final PersistedConfigurationApi configApi, @NotNull final PropertyApi propApi, @NotNull final ConfigNew configNew,
                         @NotNull final ConfigEdit configEdit, @NotNull final ConfigDetails configDetails) {
      this.configApi = configApi;
      this.propApi = propApi;
      this.configNew = configNew;
      this.configEdit = configEdit;
      this.configDetails = configDetails;
      this.state = SessionState.INSTANCE;
   }

   public void show() {
      populateStage();
      fillTableView();
      defineListeners();
      stage.showAndWait();
   }

   private void populateStage() {
      Label lblTitle = new LabelBuilder("ui.configs.overview.title").setPrefWidth(WIDTH).setStyleClass("titletext").build();
      Label lblInstruction = new LabelBuilder("ui.configs.overview.instruction").setPrefHeight(INSTRUCTION_HEIGHT).setPrefWidth(WIDTH)
            .setAlignment(Pos.CENTER).build();
      btnSelect = new ButtonBuilder("ui.shared.btn.select").setDisabled(true).build();
      btnNew = new ButtonBuilder("ui.shared.btn.new").setDisabled(true).build();
      btnDetails = new ButtonBuilder("ui.shared.btn.details").setDisabled(true).build();
      btnEdit = new ButtonBuilder("ui.shared.btn.edit").setDisabled(true).setFocusTraversable(false).build();
      btnDelete = new ButtonBuilder("ui.shared.btn.delete").setDisabled(true).setFocusTraversable(false).build();
      btnHelp = new ButtonBuilder("ui.shared.btn.help").setDisabled(false).build();
      btnExit = new ButtonBuilder("ui.shared.btn.exit").setDisabled(false).build();
      ButtonBar buttonBar = new ButtonBarBuilder().setButtons(btnSelect, btnDetails, btnEdit, btnNew, btnDelete, btnHelp, btnExit).build();
      TableColumn<PresentableConfiguration, String> nameColumn = new TableColumnBuilder().setText(Rosetta.getText("ui.general.name"))
            .setCellValueFactory(new PropertyValueFactory<>("configName")).build();
      TableColumn<PresentableConfiguration, String> descriptionColumn = new TableColumnBuilder().setText(Rosetta.getText("ui.general.description"))
            .setCellValueFactory(new PropertyValueFactory<>("configDescription")).build();
      TableColumn<PresentableConfiguration, String> stndColumn = new TableColumnBuilder().setText(Rosetta.getText("ui.configs.overview.colstandard"))
            .setCellValueFactory(new PropertyValueFactory<>("standardIndication")).build();
      tableView = new TableViewBuilder().setPrefHeight(TV_HEIGHT).setPrefWidth(WIDTH).setColumns(nameColumn, descriptionColumn, stndColumn).build();
      Pane paneTitle = new PaneBuilder().setWidth(WIDTH).setHeight(TITLE_HEIGHT).setStyleClass("titlepane").setChildren(lblTitle).build();
      Pane paneInstruction = new PaneBuilder().setWidth(WIDTH).setHeight(INSTRUCTION_HEIGHT).setChildren(lblInstruction).build();
      Pane paneSeparator = new PaneBuilder().setWidth(WIDTH).setHeight(SEPARATOR_HEIGHT).build();
      Pane paneStandard = new PaneBuilder().setWidth(WIDTH).setHeight(TV_HEIGHT).setChildren(tableView).build();
      Pane paneButtons = new PaneBuilder().setWidth(WIDTH).setHeight(BTN_PANE_HEIGHT).setChildren(buttonBar).build();
      VBox vBox = new VBoxBuilder().setWidth(WIDTH).setHeight(HEIGHT).setPadding(GAP)
            .setChildren(paneTitle, paneInstruction, paneStandard, paneSeparator, paneButtons).build();
      stage = new StageBuilder().setMinWidth(WIDTH).setMinHeight(HEIGHT).setModality(Modality.APPLICATION_MODAL)
            .setTitle(Rosetta.getText("ui.configs.overview.title")).setScene(new Scene(vBox)).build();
   }

   private void defineListeners() {
      btnSelect.setOnAction(click -> onSelect());
      btnNew.setOnAction(click -> onNew());
      btnDetails.setOnAction(click -> onDetails());
      btnEdit.setOnAction(click -> onEdit());
      btnDelete.setOnAction(click -> onDelete());
      btnHelp.setOnAction(click -> onHelp());
      btnExit.setOnAction(click -> onExit());
      selectedItems.addListener((ListChangeListener<PresentableConfiguration>) change -> onSelectItem());
   }

   private void fillTableView() {
      TableViewSelectionModel<PresentableConfiguration> selectionModel = tableView.getSelectionModel();
      selectionModel.setSelectionMode(SelectionMode.SINGLE);
      selectedItems = selectionModel.getSelectedItems();
      List<Configuration> configs = configApi.readAll();
      for (Configuration config : configs) {
         PresentableConfiguration presConfig = new PresentableConfiguration(config);
         tableView.getItems().add(presConfig);
      }
   }

   private void onSelectItem() {
      if (selectedItems.isEmpty()) {
         btnSelect.setDisable(true);
         btnDetails.setDisable(true);
         btnNew.setDisable(true);
         btnEdit.setDisable(true);
         btnDelete.setDisable(true);
      } else {
         btnSelect.setDisable(false);
         btnDetails.setDisable(false);
         btnNew.setDisable(false);
         PresentableConfiguration config = selectedItems.get(0);
         Property currentConfig = propApi.read("config").get(0);
         long currentConfigId = Long.parseLong(currentConfig.getValue());
         if ("Yes".equals(config.getStandardIndication()) || "Ja".equals(config.getStandardIndication())) {
            btnEdit.setDisable(true);
            btnEdit.setFocusTraversable(false);
            btnDelete.setDisable(true);
            btnEdit.setFocusTraversable(false);
         } else {
            btnEdit.setDisable(false);
            btnEdit.setFocusTraversable(true);
            btnDelete.setDisable(false);
            btnDelete.setFocusTraversable(true);
         }
         if (currentConfigId == config.getConfigId()) {
            btnDelete.setDisable(true);
            btnEdit.setFocusTraversable(false);
         }
      }
   }

   private void onSelect() {
      PresentableConfiguration presConfig = selectedItems.get(0);
      long newConfigId = presConfig.getConfigId();
      Property configProp = (propApi.read("config")).get(0);
      configProp.setValue(Long.toString(newConfigId));
      propApi.change(configProp);
      state.setSelectedConfig(presConfig.getOriginalConfig());
      selectionChanged = true;
      stage.close();
   }

   private void onNew() {
      configNew.show();
      if (InputStatus.READY == configNew.getInputStatus()) {
         int newConfigId = configNew.getNewConfigId();
         configEdit.show();
         stage.close();
      }
   }

   private void onDetails() {
      PresentableConfiguration config = selectedItems.get(0);
      int configId = config.getConfigId();
      configDetails.show();
//      new ConfigScreensFactory().createConfigDetails(configApi.read(configId).get(0));
   }

   private void onDelete() {
      PresentableConfiguration config = selectedItems.get(0);
      Property currentConfig = propApi.read("config").get(0);
      long currentConfigId = Long.parseLong(currentConfig.getValue());
      if (currentConfigId != config.getConfigId()) {
         configApi.delete(config.getOriginalConfig().getId());
         stage.close();
      } else {
         LOG.error("Prevented deleting currently selected config: with id : " + config.getConfigId() + ". ");
      }
   }

   private void onEdit() {
      PresentableConfiguration config = selectedItems.get(0);
      int configId = config.getConfigId();
      configEdit.show();
   }

   private void onHelp() {
      new Help(Rosetta.getHelpText("help.configoverview.title"), Rosetta.getHelpText("help.configoverview.content"));
   }

   private void onExit() {
      stage.close();
   }

   public boolean isSelectionChanged() {
      return selectionChanged;
   }
}