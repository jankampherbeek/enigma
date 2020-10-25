/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.ui;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.share.ui.domain.AstronConfigFe;
import com.radixpro.enigma.statistics.core.DataFileDescription;
import com.radixpro.enigma.statistics.ui.domain.StatsProjectFe;
import com.radixpro.enigma.ui.creators.*;
import com.radixpro.enigma.ui.screens.InputScreen;
import com.radixpro.enigma.ui.screens.blocks.BaseConfigInputBlock;
import com.radixpro.enigma.ui.shared.Help;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.radixpro.enigma.ui.shared.UiDictionary.*;

/**
 * Input screen for new projects for statistics.
 */
public class StatsProjNew extends InputScreen {

   private static final double HEIGHT = 800.0;
   private final BaseConfigInputBlock configBlock;
   private final StatsDataSearch dataSearch;
   private Label lblSubTitleDataFiles;
   private Label lblName;
   private Label lblDescription;
   private Pane paneSubTitleData;
   private Pane paneDataFiles;
   private TableView tvDataFiles;
   private TextField tfName;
   private TextField tfDescr;
   private DataFileDescription dataFileDescription;
   private final StatsFacade facade;
   private Button btnHelp;
   private Button btnCancel;
   private Button btnSave;
   private TableColumn<DataFileDescription, String> colName;
   private TableColumn<DataFileDescription, String> colDescr;
   private ObservableList<DataFileDescription> selectedDataFiles;
   private Button btnRemove;

   public StatsProjNew(@NotNull final BaseConfigInputBlock configBlock,
                       @NotNull final StatsDataSearch dataSearch,
                       @NotNull final StatsFacade facade) {
      super();
      this.configBlock = configBlock;
      this.dataSearch = dataSearch;
      this.facade = facade;
   }

   public void show() {
      initialize();
      stage.setTitle(Rosetta.getText("ui.stats.newproj.title"));
      stage.setScene(new Scene(createVBox()));
      stage.showAndWait();
   }

   @Override
   public void checkStatus() {
      btnSave.setDisable(null == tfName.getText() || tfDescr.getText().isBlank() ||
            null == tfDescr.getText() || tfDescr.getText().isBlank() || tvDataFiles.getItems().isEmpty());
      btnRemove.setDisable(tvDataFiles.getSelectionModel().getSelectedIndex() == -1);
   }

   private void initialize() {
      defineLeafs();
      createTableView();
      definePanes();
   }

   private void defineLeafs() {
      lblSubTitleDataFiles = new LabelBuilder("ui.stats.datafiles.subtitle").setPrefWidth(INPUT_WIDTH).setPrefHeight(SUBTITLE_HEIGHT).
            setStyleClass("subtitletext").build();
      lblName = new LabelBuilder("ui.stats.newproj.lblname").build();
      lblDescription = new LabelBuilder("ui.stats.newproj.lbldescription").build();
      tfName = new TextFieldBuilder().setPrefWidth(INPUT_DATA_WIDTH).setPrefHeight(INPUT_HEIGHT).setStyleClass("inputDefault").build();
      tfName.textProperty().addListener((observable, oldValue, newValue) -> onChange());
      tfDescr = new TextFieldBuilder().setPrefWidth(INPUT_DATA_WIDTH).setPrefHeight(INPUT_HEIGHT).setStyleClass("inputDefault").build();
      tfDescr.textProperty().addListener((observable, oldValue, newValue) -> onChange());
   }

   private void definePanes() {
      paneSubTitleData = new PaneBuilder().setHeight(SUBTITLE_HEIGHT).setWidth(INPUT_WIDTH).setStyleClass(STYLE_SUBTITLE_PANE).
            setChildren(lblSubTitleDataFiles).build();
      paneDataFiles = new PaneBuilder().setHeight(120).setWidth(INPUT_WIDTH).setChildren(tvDataFiles).build();
   }

   private Pane createPaneTitle() {
      Label label = new LabelBuilder("ui.stats.newproj.title").setPrefWidth(INPUT_WIDTH).setPrefHeight(TITLE_HEIGHT).setStyleClass("titletext").build();
      return new PaneBuilder().setWidth(INPUT_WIDTH).setHeight(TITLE_HEIGHT).setStyleClass("titlepane").setChildren(label).build();
   }


   private VBox createVBox() {
      return new VBoxBuilder().setWidth(INPUT_WIDTH).setHeight(650.0).setPadding(GAP).setChildren(
            createPaneTitle(),
            lblName,
            tfName,
            lblDescription,
            tfDescr,
            new PaneBuilder().setHeight(20.0).build(),
            configBlock.getGridPane(),
            createVBoxData(),
            createPaneBtnBar()
      ).build();
   }

   private VBox createVBoxData() {
      return new VBoxBuilder().setWidth(INPUT_WIDTH).setHeight(230.0).setChildren(
            paneSubTitleData,
            paneDataFiles,
            createBtnBarData()).build();
   }

   @Override
   protected ButtonBar createBtnBar() {
      btnHelp = new ButtonBuilder("ui.shared.btn.help").setDisabled(false).build();
      btnCancel = new ButtonBuilder("ui.shared.btn.cancel").setDisabled(false).build();
      btnSave = new ButtonBuilder("ui.shared.btn.save").setDisabled(true).build();
      btnSave.setOnAction(e -> onSave());
      btnCancel.setOnAction(e -> stage.close());
      btnHelp.setOnAction(e -> onHelp());
      return new ButtonBarBuilder().setButtons(btnHelp, btnCancel, btnSave).build();
   }

   private ButtonBar createBtnBarData() {
      Button searchButton = new ButtonBuilder("ui.stats.datafiles.btnsearch").setDisabled(false).build();
      btnRemove = new ButtonBuilder("ui.stats.datafiles.btnremove").setDisabled(true).build();
      searchButton.setOnAction(e -> onSearch());
      btnRemove.setOnAction(e -> onRemove());
      return new ButtonBarBuilder().setButtons(searchButton, btnRemove).build();
   }

   private void onSearch() {
      dataSearch.show();
      if (dataSearch.isSelectionMade()) {
         this.dataFileDescription = dataSearch.getSelectedItem();
         tvDataFiles.getItems().add(dataFileDescription);
      }
      checkStatus();
   }

   private void createTableView() {
      tvDataFiles = new TableViewBuilder().setPrefWidth(INPUT_WIDTH).setPrefHeight(120).build();
      tvDataFiles.setPlaceholder(new Label(Rosetta.getText("ui.stats.placeholder.datafiles")));
      colName = new TableColumn<>(Rosetta.getText("ui.general.name"));
      colDescr = new TableColumn<>(Rosetta.getText("ui.general.description"));
      colName.setCellValueFactory(new PropertyValueFactory("name"));
      colDescr.setCellValueFactory(new PropertyValueFactory("description"));
      colName.setPrefWidth(300.0);
      colDescr.setPrefWidth(300.0);
      tvDataFiles.getColumns().add(colName);
      tvDataFiles.getColumns().add(colDescr);

      TableView.TableViewSelectionModel<DataFileDescription> selectionModel = tvDataFiles.getSelectionModel();
      selectionModel.setSelectionMode(SelectionMode.SINGLE);
      selectedDataFiles = selectionModel.getSelectedItems();
      selectedDataFiles.addListener((ListChangeListener<DataFileDescription>) change -> onSelectFile());
   }

   private void onRemove() {
      int id = tvDataFiles.getSelectionModel().getSelectedIndex();
      tvDataFiles.getItems().remove(id);
      checkStatus();
   }

   private void onSelectFile() {
      checkStatus();
   }

   private void onSave() {
      facade.saveProject(createProjectFe());
      stage.close();
   }

   private void onChange() {
      checkStatus();
   }

   private StatsProjectFe createProjectFe() {
      final String name = tfName.getText();
      final String descr = tfDescr.getText();
      final List<String> dataFiles = new ArrayList<>();
      final ObservableList items = tvDataFiles.getItems();
      for (Object obj : tvDataFiles.getItems()) {
         DataFileDescription dd = (DataFileDescription) obj;
         dataFiles.add(dd.getName());
      }
      final String houseSystem = configBlock.getHouseSystem().name();
      final String ayanamsha = configBlock.getAyanamsha().name();
      final String obsPos = configBlock.getObserverPosition().name();
      final String eclProj = configBlock.getEclipticProjection().name();
      final AstronConfigFe config = new AstronConfigFe(houseSystem, ayanamsha, eclProj, obsPos);
      return new StatsProjectFe(name, descr, dataFiles.get(0), config);    // FIXME: should also handle datafile for events
   }

   private void onHelp() {
      new Help(Rosetta.getHelpText("help.statsprojnew.title"), Rosetta.getHelpText("help.statsprojnew.content"));
   }

}
