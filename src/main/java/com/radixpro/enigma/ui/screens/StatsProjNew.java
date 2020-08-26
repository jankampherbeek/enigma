/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens;

import com.radixpro.enigma.ui.creators.LabelBuilder;
import com.radixpro.enigma.ui.creators.PaneBuilder;
import com.radixpro.enigma.ui.creators.VBoxBuilder;
import com.radixpro.enigma.ui.screens.blocks.DataFilesInputBlock;
import com.radixpro.enigma.ui.screens.blocks.NameDescriptionInputBlock;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import static com.radixpro.enigma.ui.shared.UiDictionary.INPUT_WIDTH;
import static com.radixpro.enigma.ui.shared.UiDictionary.TITLE_HEIGHT;

/**
 * Input screen for new projects for statistics.
 */
public class StatsProjNew extends InputScreen {

   private static final double HEIGHT = 600.0;
   private final NameDescriptionInputBlock nameDescrBlock;
   private final DataFilesInputBlock dataFilesBlock;
   private Label lblTitle;
   private Pane paneTitle;

   public StatsProjNew(final NameDescriptionInputBlock nameDescrBlock, final DataFilesInputBlock dataFilesBlock) {
      super();
      this.nameDescrBlock = nameDescrBlock;
      this.dataFilesBlock = dataFilesBlock;
   }

   public void show() {
      initialize();
      stage.setScene(new Scene(createVBox()));
      stage.showAndWait();
   }

   @Override
   public void checkStatus() {
      if (null != nameDescrBlock.getTfDescr().getText() && !nameDescrBlock.getTfDescr().getText().isBlank() &&
            null != nameDescrBlock.getTfDescr() && !nameDescrBlock.getTfDescr().getText().isBlank()) {
         // enable buttons
      } else {
         // disable buttons
      }
   }


   private void initialize() {
      createLabels();
      createPanes();
   }

   private void createLabels() {
      lblTitle = new LabelBuilder("ui.stats.newproj.title").setPrefWidth(INPUT_WIDTH).setStyleClass("titletext").build();
   }

   private void createPanes() {
      paneTitle = new PaneBuilder().setWidth(INPUT_WIDTH).setHeight(TITLE_HEIGHT).setStyleClass("titlepane").setChildren(lblTitle).build();
   }


   private VBox createVBox() {
      return new VBoxBuilder().setWidth(INPUT_WIDTH).setHeight(HEIGHT).setChildren(paneTitle, nameDescrBlock.getVBox(this)).build();
   }

   @Override
   protected ButtonBar createBtnBar() {
      return null;
   }
}
