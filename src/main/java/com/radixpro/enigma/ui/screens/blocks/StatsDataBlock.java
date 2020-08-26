/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens.blocks;

import com.radixpro.enigma.ui.creators.*;
import com.radixpro.enigma.ui.screens.StatsDataNew;
import com.radixpro.enigma.ui.screens.StatsDataSearch;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import static com.radixpro.enigma.ui.shared.UiDictionary.*;

/**
 * Block for part of screen that handles input data for stats.
 */
public class StatsDataBlock {

   private static final double BLOCK_HEIGHT = 300.0;
   private final StatsDataNew dataNew;
   private final StatsDataSearch dataSearch;
   private Label lblSubTitleData;
   private Button btnDetails;
   private Button btnNew;
   private Button btnSearch;
   private Pane paneSubTitle;
   private Pane paneBtnBar;
   private TableView tvDataFiles;

   public StatsDataBlock(@NotNull final StatsDataNew dataNew, @NotNull final StatsDataSearch dataSearch) {
      this.dataNew = dataNew;
      this.dataSearch = dataSearch;
   }


   public VBox getVBox() {
      initialize();
      return createVBox();
   }

   private void initialize() {
      createLeafs();
      createPanes();
      createBtnBar();
      createVBox();
   }

   private void createLeafs() {
      lblSubTitleData = new LabelBuilder("ui.stats.start.datasubtitle").setPrefWidth(START_WIDTH).setPrefHeight(SUBTITLE_HEIGHT).
            setStyleClass("subtitletext").build();
      tvDataFiles = new TableViewBuilder().setPrefWidth(START_WIDTH).setPrefHeight(TV_HEIGHT).build();
   }

   private void createPanes() {
      paneSubTitle = new PaneBuilder().setHeight(SUBTITLE_HEIGHT).setWidth(START_WIDTH).setStyleClass(STYLE_SUBTITLE_PANE).setChildren(lblSubTitleData).build();
      paneBtnBar = new PaneBuilder().setHeight(BUTTONBAR_HEIGHT).setWidth(START_WIDTH).setChildren(createBtnBar()).build();
   }

   private ButtonBar createBtnBar() {
      btnDetails = new ButtonBuilder("ui.stats.start.btndatadetails").setDisabled(true).build();
      btnNew = new ButtonBuilder("ui.stats.start.btndatanew").setDisabled(false).build();
      btnSearch = new ButtonBuilder("ui.stats.start.btndatasearch").setDisabled(false).build();
      btnSearch.setOnAction(click -> onSearch());
      btnNew.setOnAction(click -> onNew());
      return new ButtonBarBuilder().setButtons(btnDetails, btnNew, btnSearch).build();
   }

   private VBox createVBox() {
      return new VBoxBuilder().setWidth(START_WIDTH).setHeight(BLOCK_HEIGHT).setChildren(paneSubTitle, tvDataFiles, paneBtnBar).build();
   }

   private void onNew() {
      dataNew.show();
   }

   private void onSearch() {
      dataSearch.show();
   }

}
