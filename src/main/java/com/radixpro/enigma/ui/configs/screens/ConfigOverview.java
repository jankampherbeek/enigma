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
import com.radixpro.enigma.ui.shared.factories.ButtonFactory;
import com.radixpro.enigma.ui.shared.factories.LabelFactory;
import com.radixpro.enigma.ui.shared.factories.PaneFactory;
import com.radixpro.enigma.ui.shared.presentationmodel.PresentableConfiguration;
import com.radixpro.enigma.xchg.api.PersistedConfigurationApi;
import com.radixpro.enigma.xchg.api.PersistedPropertyApi;
import com.radixpro.enigma.xchg.domain.config.Configuration;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
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

import static com.radixpro.enigma.ui.shared.StyleDictionary.STYLESHEET;

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
   private final Stage stage;
   private ObservableList<PresentableConfiguration> selectedItems;
   private Button btnSelect;
   private Button btnNew;
   private Button btnDetails;
   private Button btnEdit;
   private Button btnDelete;
   private boolean selectionChanged = false;

   public ConfigOverview() {
      configApi = new PersistedConfigurationApi();
      propApi = new PersistedPropertyApi();
      stage = new Stage();
      rosetta = Rosetta.getRosetta();
      showOverview();
   }

   private void showOverview() {
      stage.setMinHeight(HEIGHT);
      stage.setMinWidth(WIDTH);
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setTitle(rosetta.getText("ui.configs.overview.title"));
      showOrReshow();
   }

   private void showOrReshow() {
      stage.close();
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
      vBox.getChildren().add(1, createPaneInstruction());
      vBox.getChildren().add(2, createPaneStandard());
      vBox.getChildren().add(3, createPaneSeparator());
      vBox.getChildren().add(4, createPaneBtns());
      return vBox;
   }

   private Pane createPaneSeparator() {
      return PaneFactory.createPane(SEPARATOR_HEIGHT, WIDTH);
   }

   private Pane createPaneTitle() {
      final Pane pane = PaneFactory.createPane(TITLE_HEIGHT, WIDTH, "titlepane");
      pane.getChildren().add(LabelFactory.createLabel(rosetta.getText("ui.configs.overview.title"), "titletext", WIDTH));
      return pane;
   }

   private Pane createPaneInstruction() {
      final Pane pane = PaneFactory.createPane(INSTRUCTION_HEIGHT, WIDTH);
      Label lblInstruction = LabelFactory.createLabel(rosetta.getText("ui.configs.overview.instruction"));
      lblInstruction.setPrefHeight(INSTRUCTION_HEIGHT);
      lblInstruction.setPrefWidth(WIDTH);
      lblInstruction.setAlignment(Pos.CENTER);
      pane.getChildren().add(lblInstruction);
      return pane;
   }

   private Pane createPaneStandard() {
      final Pane pane = PaneFactory.createPane(TV_HEIGHT, WIDTH);
      pane.getChildren().add(createTableView());
      return pane;
   }

   private Pane createPaneBtns() {
      final Pane pane = PaneFactory.createPane(BTN_PANE_HEIGHT, WIDTH);
      pane.getChildren().add(createButtonBar());
      return pane;
   }

   private TableView<PresentableConfiguration> createTableView() {
      final TableView<PresentableConfiguration> tableView = new TableView<>();
      tableView.setPrefHeight(TV_HEIGHT);
      tableView.setPrefWidth(WIDTH);
      TableColumn<PresentableConfiguration, String> nameColumn = new TableColumn<>(rosetta.getText("ui.general.name"));
      nameColumn.setCellValueFactory(new PropertyValueFactory<>("configName"));
      TableColumn<PresentableConfiguration, String> descriptionColumn = new TableColumn<>(rosetta.getText("ui.general.description"));
      descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("configDescription"));
      TableColumn<PresentableConfiguration, String> stndColumn = new TableColumn<>(rosetta.getText("ui.configs.overview.colstandard"));
      stndColumn.setCellValueFactory(new PropertyValueFactory<>("standardIndication"));
      TableViewSelectionModel<PresentableConfiguration> selectionModel = tableView.getSelectionModel();
      selectionModel.setSelectionMode(SelectionMode.SINGLE);
      selectedItems = selectionModel.getSelectedItems();
      selectedItems.addListener((ListChangeListener<PresentableConfiguration>) change -> onSelectItem());

      tableView.getColumns().add(nameColumn);
      tableView.getColumns().add(descriptionColumn);
      tableView.getColumns().add(stndColumn);

      List<Configuration> configs = configApi.readAll();
      for (Configuration config : configs) {
         PresentableConfiguration presConfig = new PresentableConfiguration(config);
         tableView.getItems().add(presConfig);
      }
      return tableView;
   }

   private ButtonBar createButtonBar() {
      final ButtonBar buttonBar = new ButtonBar();
      btnSelect = ButtonFactory.createButton(rosetta.getText("ui.shared.btn.select"), true);
      btnNew = ButtonFactory.createButton(rosetta.getText("ui.shared.btn.new"), true);
      btnDetails = ButtonFactory.createButton(rosetta.getText("ui.shared.btn.details"), true);
      btnEdit = ButtonFactory.createButton(rosetta.getText("ui.shared.btn.edit"), true);
      btnDelete = ButtonFactory.createButton(rosetta.getText("ui.shared.btn.delete"), true);
      Button btnHelp = ButtonFactory.createButton(rosetta.getText("ui.shared.btn.help"), false);
      Button btnExit = ButtonFactory.createButton(rosetta.getText("ui.shared.btn.exit"), false);

      btnSelect.setOnAction(click -> onSelect());
      btnNew.setOnAction(click -> onNew());
      btnDetails.setOnAction(click -> onDetails());
      btnEdit.setOnAction(click -> onEdit());
      btnEdit.setDisable(true);
      btnEdit.setFocusTraversable(false);
      btnDelete.setOnAction(click -> onDelete());
      btnDelete.setDisable(true);
      btnDelete.setFocusTraversable(false);
      btnHelp.setOnAction(click -> onHelp());
      btnExit.setOnAction(click -> onExit());

      buttonBar.getButtons().add(btnSelect);
      buttonBar.getButtons().add(btnDetails);
      buttonBar.getButtons().add(btnEdit);
      buttonBar.getButtons().add(btnNew);
      buttonBar.getButtons().add(btnDelete);
      buttonBar.getButtons().add(btnHelp);
      buttonBar.getButtons().add(btnExit);

      return buttonBar;
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
      long configId = config.getConfigId();
      ConfigNew configNew = new ConfigScreensFactory().createConfigNew(configApi.read(configId).get(0));

      if (InputStatus.READY == configNew.getInputStatus()) {
         long newConfigId = configNew.getNewConfigId();
         new ConfigScreensFactory().createConfigEdit(configApi.read((int) newConfigId).get(0));
         stage.close();
      }
   }

   private void onDetails() {
      PresentableConfiguration config = selectedItems.get(0);
      long configId = config.getConfigId();
      new ConfigScreensFactory().createConfigDetails(configApi.read(configId).get(0));
   }

   private void onDelete() {
      PresentableConfiguration config = selectedItems.get(0);
      Property currentConfig = propApi.read("config").get(0);
      long currentConfigId = Long.parseLong(currentConfig.getValue());
      if (currentConfigId != config.getConfigId()) {
         configApi.delete(config.getOriginalConfig());
         stage.close();
      } else {
         LOG.error("Prevented deleting currently selected config: with id : " + config.getConfigId() + ". ");
      }
   }

   private void onEdit() {
      PresentableConfiguration config = selectedItems.get(0);
      long configId = config.getConfigId();
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