/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens.blocks;

import com.radixpro.enigma.ui.creators.*;
import com.radixpro.enigma.ui.screens.StatsProjNew;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.radixpro.enigma.ui.shared.UiDictionary.*;

/**
 * Block for part of screen that handles projects for stats.
 */
public class StatsProjBlock {

   private static final double BLOCK_HEIGHT = 300.0;
   private final StatsProjNew statsProjNew;
   private Label lblSubTitleProj;
   private Button btnOpen;
   private Button btnNew;
   private Button btnEdit;
   private Button btnSearch;
   private Pane paneSubTitleProj;
   private Pane paneBtnBar;
   private TableView tvProj;

   public StatsProjBlock(final StatsProjNew statsProjNew) {
      this.statsProjNew = checkNotNull(statsProjNew);
   }

   public VBox getVBox() {
      initialize();
      return createVBox();
   }

   private void initialize() {
      createLeafs();
      createPanes();
   }

   private void createLeafs() {
      lblSubTitleProj = new LabelBuilder("ui.stats.start.projtitle").setStyleClass("subtitletext").setPrefWidth(START_WIDTH).build();
      tvProj = new TableViewBuilder().setPrefWidth(START_WIDTH).setPrefHeight(TV_HEIGHT).build();
   }

   private void createPanes() {
      paneSubTitleProj =
            new PaneBuilder().setHeight(SUBTITLE_HEIGHT).setWidth(START_WIDTH).setStyleClass(STYLE_SUBTITLE_PANE).setChildren(lblSubTitleProj).build();
      paneBtnBar = new PaneBuilder().setHeight(BUTTONBAR_HEIGHT).setWidth(START_WIDTH).setChildren(createBtnBar()).build();
   }

   private ButtonBar createBtnBar() {
      btnOpen = new ButtonBuilder("ui.stats.start.btnprojopen").setDisabled(true).build();
      btnEdit = new ButtonBuilder("ui.stats.start.btnprojedit").setDisabled(true).build();
      btnNew = new ButtonBuilder("ui.stats.start.btnprojnew").setDisabled(false).build();
      btnSearch = new ButtonBuilder("ui.stats.start.btnprojsearch").setDisabled(false).build();
      btnNew.setOnAction(click -> statsProjNew.show());
      return new ButtonBarBuilder().setButtons(btnOpen, btnEdit, btnNew, btnSearch).build();
   }


   private VBox createVBox() {
      return new VBoxBuilder().setWidth(START_WIDTH).setHeight(BLOCK_HEIGHT).setChildren(paneSubTitleProj, tvProj, paneBtnBar).build();
   }

}
