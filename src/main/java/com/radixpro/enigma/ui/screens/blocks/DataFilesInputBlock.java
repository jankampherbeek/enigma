/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens.blocks;

import com.radixpro.enigma.SessionState;
import com.radixpro.enigma.ui.creators.*;
import com.radixpro.enigma.ui.screens.InputScreen;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import static com.radixpro.enigma.ui.shared.UiDictionary.*;

public class DataFilesInputBlock extends InputBlock {

   private static final double HEIGHT = 120.0;
   private InputScreen mainScreen;
   private Label lblSubTitle;
   private Pane paneSubTitle;
   private TableView tvDataFiles;

   public DataFilesInputBlock(@NotNull final SessionState state) {
      super(state);

   }

   public VBox getVBox(@NotNull final InputScreen mainScreen) {
      this.mainScreen = mainScreen;
      initialize();
      return createVBox();
   }

   @Override
   protected void initialize() {
      defineLeafs();
      definePanes();
   }

   private void defineLeafs() {
      lblSubTitle = new LabelBuilder("ui.stats.datafiles.subtitle").setPrefWidth(INPUT_WIDTH).setPrefHeight(SUBTITLE_HEIGHT).
            setStyleClass("subtitletext").build();
      tvDataFiles = new TableViewBuilder().setPrefWidth(INPUT_WIDTH).setPrefHeight(HEIGHT).build();
   }

   private void definePanes() {
      paneSubTitle = new PaneBuilder().setHeight(SUBTITLE_HEIGHT).setWidth(INPUT_WIDTH).setStyleClass(STYLE_SUBTITLE_PANE).
            setChildren(lblSubTitle, tvDataFiles).build();
   }

   private ButtonBar createButtonBar() {
      Button searchButton = new ButtonBuilder("ui.stats.datafiles.btnsearch").setDisabled(false).build();
      Button removeButton = new ButtonBuilder("ui.stats.datafiles.btnremove").setDisabled(true).build();
      searchButton.setOnAction(e -> onSearch());
      removeButton.setOnAction(e -> onRemove());
      return new ButtonBarBuilder().setButtons(searchButton, removeButton).build();
   }

   private VBox createVBox() {
      return new VBoxBuilder().setWidth(INPUT_WIDTH).setChildren(paneSubTitle, tvDataFiles, createButtonBar()).build();
   }

   private void onSearch() {
      // open search screen and select datafile
      mainScreen.checkStatus();
   }

   private void onRemove() {
      // remove selected datafile
      mainScreen.checkStatus();
   }


}
