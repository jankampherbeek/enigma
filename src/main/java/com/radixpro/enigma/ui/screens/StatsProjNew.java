/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens;

import com.radixpro.enigma.domain.stats.DataFileDescription;
import com.radixpro.enigma.ui.creators.*;
import com.radixpro.enigma.ui.screens.blocks.BaseConfigInputBlock;
import com.radixpro.enigma.ui.screens.blocks.NameDescriptionInputBlock;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import static com.radixpro.enigma.ui.shared.UiDictionary.*;

/**
 * Input screen for new projects for statistics.
 */
public class StatsProjNew extends InputScreen {

   private static final double HEIGHT = 600.0;
   private final NameDescriptionInputBlock nameDescrBlock;
   private final BaseConfigInputBlock configBlock;
   private final StatsDataSearch dataSearch;
   private Label lblSubTitle;
   private Pane paneSubTitleData;
   private TableView tvDataFiles;
   private DataFileDescription dataFileDescription;
   private Button btnOk;
   private Button btnHelp;
   private Button btnCancel;
   private TableColumn<DataFileDescription, String> colName;
   private TableColumn<DataFileDescription, String> colDescr;
   private ObservableList<DataFileDescription> selectedDataFiles;

   public StatsProjNew(@NotNull final NameDescriptionInputBlock nameDescrBlock,
                       @NotNull final BaseConfigInputBlock configBlock,
                       @NotNull final StatsDataSearch dataSearch) {
      super();
      this.nameDescrBlock = nameDescrBlock;
      this.configBlock = configBlock;
      this.dataSearch = dataSearch;
   }

   public void show() {
      initialize();
      stage.setScene(new Scene(createVBox()));
      stage.showAndWait();
   }

   @Override
   public void checkStatus() {
      btnOk.setDisable(null == nameDescrBlock.getName() || nameDescrBlock.getName().isBlank() ||
            null == nameDescrBlock.getDescr() || nameDescrBlock.getDescr().isBlank() || !tvDataFiles.getItems().isEmpty());
   }

   private void initialize() {
      defineLeafs();
      createTableView();
      definePanes();
   }

   private void defineLeafs() {
      lblSubTitle = new LabelBuilder("ui.stats.datafiles.subtitle").setPrefWidth(INPUT_WIDTH).setPrefHeight(SUBTITLE_HEIGHT).
            setStyleClass("subtitletext").build();
   }

   private void definePanes() {
      paneSubTitleData = new PaneBuilder().setHeight(SUBTITLE_HEIGHT).setWidth(INPUT_WIDTH).setStyleClass(STYLE_SUBTITLE_PANE).
            setChildren(lblSubTitle, tvDataFiles).build();
   }

   private Pane createPaneTitle() {
      Label label = new LabelBuilder("ui.stats.newproj.title").setPrefWidth(INPUT_WIDTH).setPrefHeight(TITLE_HEIGHT).setStyleClass("titletext").build();
      return new PaneBuilder().setWidth(INPUT_WIDTH).setHeight(TITLE_HEIGHT).setStyleClass("titlepane").setChildren(label).build();
   }


   private VBox createVBox() {
      return new VBoxBuilder().setWidth(INPUT_WIDTH).setHeight(600.0).setChildren(
            createPaneTitle(),
            nameDescrBlock.getGridPane(this),
            configBlock.getGridPane(),
            createVBoxData(),
            createPaneBtnBar()
      ).build();
   }

   private VBox createVBoxData() {
      return new VBoxBuilder().setWidth(INPUT_WIDTH).setHeight(180.0).setChildren(paneSubTitleData, tvDataFiles, createBtnBarData()).build();
   }

   @Override
   protected ButtonBar createBtnBar() {
      btnHelp = new ButtonBuilder("ui.shared.btn.help").setDisabled(false).build();
      btnCancel = new ButtonBuilder("ui.shared.btn.cancel").setDisabled(false).build();
      btnOk = new ButtonBuilder("ui.shared.btn.ok").setDisabled(true).build();
      return new ButtonBarBuilder().setButtons(btnHelp, btnCancel, btnOk).build();
   }

   private ButtonBar createBtnBarData() {
      Button searchButton = new ButtonBuilder("ui.stats.datafiles.btnsearch").setDisabled(false).build();
      Button removeButton = new ButtonBuilder("ui.stats.datafiles.btnremove").setDisabled(true).build();
      searchButton.setOnAction(e -> onSearch());
      removeButton.setOnAction(e -> onRemove());
      return new ButtonBarBuilder().setButtons(searchButton, removeButton).build();
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
      tvDataFiles = new TableViewBuilder().setPrefWidth(INPUT_WIDTH).setPrefHeight(HEIGHT).build();
      tvDataFiles.setPlaceholder(new Label(rosetta.getText("ui.stats.placeholder.datafiles")));
      colName = new TableColumn<>(rosetta.getText("ui.general.name"));
      colDescr = new TableColumn<>(rosetta.getText("ui.general.description"));
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
      // remove selected datafile
      checkStatus();
   }

   private void onSelectFile() {
      // TODO enable buttons, maybe just checkStatus()
   }
}
