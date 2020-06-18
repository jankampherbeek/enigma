/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.configs.screens;

import com.radixpro.enigma.shared.Property;
import com.radixpro.enigma.shared.Rosetta;
import com.radixpro.enigma.ui.configs.factories.ConfigScreensFactory;
import com.radixpro.enigma.ui.shared.Help;
import com.radixpro.enigma.ui.shared.InputStatus;
import com.radixpro.enigma.ui.shared.factories.*;
import com.radixpro.enigma.ui.shared.presentationmodel.PresentableConfiguration;
import com.radixpro.enigma.xchg.api.PersistedConfigurationApi;
import com.radixpro.enigma.xchg.api.PersistedPropertyApi;
import com.radixpro.enigma.xchg.domain.config.Configuration;
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

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

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
   private final Rosetta rosetta;
   private final PersistedConfigurationApi configApi;
   private final PersistedPropertyApi propApi;
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

   public ConfigOverview(final PersistedConfigurationApi configApi, final PersistedPropertyApi propApi, final Rosetta rosetta) {
      this.configApi = checkNotNull(configApi);
      this.propApi = checkNotNull(propApi);
      this.rosetta = rosetta;
      populateStage();
      fillTableView();
      defineListeners();
      stage.show();
   }

   private void populateStage() {
      Label lblTitle = new LabelBuilder(rosetta.getText("ui.configs.overview.title")).setPrefWidth(WIDTH).setStyleClass("titletext").build();
      Label lblInstruction = new LabelBuilder(rosetta.getText("ui.configs.overview.instruction")).setPrefHeight(INSTRUCTION_HEIGHT).setPrefWidth(WIDTH)
            .setAlignment(Pos.CENTER).build();
      btnSelect = new ButtonBuilder(rosetta.getText("ui.shared.btn.select")).setDisabled(true).build();
      btnNew = new ButtonBuilder(rosetta.getText("ui.shared.btn.new")).setDisabled(true).build();
      btnDetails = new ButtonBuilder(rosetta.getText("ui.shared.btn.details")).setDisabled(true).build();
      btnEdit = new ButtonBuilder(rosetta.getText("ui.shared.btn.edit")).setDisabled(true).setFocusTraversable(false).build();
      btnDelete = new ButtonBuilder(rosetta.getText("ui.shared.btn.delete")).setDisabled(true).setFocusTraversable(false).build();
      btnHelp = new ButtonBuilder(rosetta.getText("ui.shared.btn.help")).setDisabled(false).build();
      btnExit = new ButtonBuilder(rosetta.getText("ui.shared.btn.exit")).setDisabled(false).build();
      ButtonBar buttonBar = new ButtonBarBuilder().setButtons(btnSelect, btnDetails, btnEdit, btnNew, btnDelete, btnHelp, btnExit).build();
      TableColumn<PresentableConfiguration, String> nameColumn = new TableColumnBuilder().setText(rosetta.getText("ui.general.name"))
            .setCellValueFactory(new PropertyValueFactory<>("configName")).build();
      TableColumn<PresentableConfiguration, String> descriptionColumn = new TableColumnBuilder().setText(rosetta.getText("ui.general.description"))
            .setCellValueFactory(new PropertyValueFactory<>("configDescription")).build();
      TableColumn<PresentableConfiguration, String> stndColumn = new TableColumnBuilder().setText(rosetta.getText("ui.configs.overview.colstandard"))
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
            .setTitle(rosetta.getText("ui.configs.overview.title")).setScene(new Scene(vBox)).build();
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

   private void showOrReshow() {
      stage.close();
      populateStage();
      stage.showAndWait();
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
      PresentableConfiguration config = selectedItems.get(0);
      long newConfigId = config.getConfigId();
      Property configProp = (propApi.read("config")).get(0);
      configProp.setValue(Long.toString(newConfigId));
      propApi.update(configProp);
      selectionChanged = true;
      stage.close();
   }

   private void onNew() {
      PresentableConfiguration config = selectedItems.get(0);
      int configId = config.getConfigId();
      ConfigNew configNew = new ConfigScreensFactory().createConfigNew(configApi.read(configId).get(0));

      if (InputStatus.READY == configNew.getInputStatus()) {
         int newConfigId = configNew.getNewConfigId();
         new ConfigScreensFactory().createConfigEdit(configApi.read(newConfigId).get(0));
         stage.close();
      }
   }

   private void onDetails() {
      PresentableConfiguration config = selectedItems.get(0);
      int configId = config.getConfigId();
      new ConfigScreensFactory().createConfigDetails(configApi.read(configId).get(0));
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
      new ConfigScreensFactory().createConfigEdit(configApi.read(configId).get(0));
      showOrReshow();
   }

   private void onHelp() {
      new Help(rosetta.getHelpText("help.configoverview.title"), rosetta.getHelpText("help.configoverview.content"));
   }

   private void onExit() {
      stage.close();
   }

   public boolean isSelectionChanged() {
      return selectionChanged;
   }
}