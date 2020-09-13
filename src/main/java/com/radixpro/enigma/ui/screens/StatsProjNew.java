/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens;

import com.radixpro.enigma.ui.creators.*;
import com.radixpro.enigma.ui.screens.blocks.BaseConfigInputBlock;
import com.radixpro.enigma.ui.screens.blocks.DataFilesInputBlock;
import com.radixpro.enigma.ui.screens.blocks.NameDescriptionInputBlock;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import static com.radixpro.enigma.ui.shared.UiDictionary.INPUT_WIDTH;
import static com.radixpro.enigma.ui.shared.UiDictionary.TITLE_HEIGHT;

/**
 * Input screen for new projects for statistics.
 */
public class StatsProjNew extends InputScreen {

   private static final double HEIGHT = 600.0;
   private final NameDescriptionInputBlock nameDescrBlock;
   private final DataFilesInputBlock dataFilesBlock;
   private final BaseConfigInputBlock configBlock;
   private Button btnOk;
   private Button btnHelp;
   private Button btnCancel;

   public StatsProjNew(@NotNull final NameDescriptionInputBlock nameDescrBlock,
                       @NotNull final DataFilesInputBlock dataFilesBlock,
                       @NotNull final BaseConfigInputBlock configBlock) {
      super();
      this.nameDescrBlock = nameDescrBlock;
      this.dataFilesBlock = dataFilesBlock;
      this.configBlock = configBlock;
   }

   public void show() {
      initialize();
      stage.setScene(new Scene(createVBox()));
      stage.showAndWait();
   }

   @Override
   public void checkStatus() {
      btnOk.setDisable(null == nameDescrBlock.getName() || nameDescrBlock.getName().isBlank() ||
            null == nameDescrBlock.getDescr() || nameDescrBlock.getDescr().isBlank());
   }

   private void initialize() {
//      createPanes();
   }

   private Pane createPaneTitle() {
      Label label = new LabelBuilder("ui.stats.newproj.title").setPrefWidth(INPUT_WIDTH).setPrefHeight(TITLE_HEIGHT).setStyleClass("titletext").build();
      return new PaneBuilder().setWidth(INPUT_WIDTH).setHeight(TITLE_HEIGHT).setStyleClass("titlepane").setChildren(label).build();
   }


   private VBox createVBox() {
      return new VBoxBuilder().setWidth(INPUT_WIDTH).setHeight(HEIGHT).setChildren(
            createPaneTitle(),
            nameDescrBlock.getGridPane(this),
            configBlock.getGridPane(),
            dataFilesBlock.getVBox(this),
            createPaneBtnBar()
      ).build();
   }

   @Override
   protected ButtonBar createBtnBar() {
      btnHelp = new ButtonBuilder("ui.shared.btn.help").setDisabled(false).build();
      btnCancel = new ButtonBuilder("ui.shared.btn.cancel").setDisabled(false).build();
      btnOk = new ButtonBuilder("ui.shared.btn.ok").setDisabled(true).build();
      return new ButtonBarBuilder().setButtons(btnHelp, btnCancel, btnOk).build();
   }
}
