/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens.blocks;

import com.radixpro.enigma.SessionState;
import com.radixpro.enigma.ui.creators.LabelBuilder;
import com.radixpro.enigma.ui.creators.PaneBuilder;
import com.radixpro.enigma.ui.creators.TextFieldBuilder;
import com.radixpro.enigma.ui.creators.VBoxBuilder;
import com.radixpro.enigma.ui.screens.InputScreen;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import static com.radixpro.enigma.ui.shared.UiDictionary.*;

public class NameDescriptionInputBlock extends InputBlock {

   private static final double BLOCK_HEIGHT = 200.0;
   private static final double DATA_HEIGHT = 150.0;
   private InputScreen mainScreen;
   private Label lblName;
   private Label lblDescr;
   private Label lblSubTitle;
   private TextField tfName;
   private TextField tfDescr;
   private Pane paneSubTitle;

   public NameDescriptionInputBlock(SessionState state) {
      super(state);
   }

   public VBox getVBox(InputScreen mainScreen) {
      this.mainScreen = mainScreen;
      initialize();
      return createVBox();
   }

   public String getName() {
      return tfName.getText();
   }

   public String getDescr() {
      return tfDescr.getText();
   }

   @Override
   protected void initialize() {
      createLeafs();
      createPanes();
   }

   private void createLeafs() {
      lblSubTitle = new LabelBuilder("ui.shared.lbl.namedescsubtitle").setPrefWidth(INPUT_WIDTH).setStyleClass("subtitletext").build();
      lblName = new LabelBuilder("ui.shared.lbl.name").setPrefWidth(INPUT_WIDTH).build();
      lblDescr = new LabelBuilder("ui.shared.lbl.description").setPrefWidth(INPUT_WIDTH).build();
      tfName = new TextFieldBuilder().setPrefWidth(INPUT_WIDTH).setPrefHeight(INPUT_HEIGHT).setStyleClass("inputDefault").build();
      tfName.textProperty().addListener((observable, oldValue, newValue) -> onChange());
      tfDescr = new TextFieldBuilder().setPrefWidth(INPUT_WIDTH).setPrefHeight(INPUT_HEIGHT).setStyleClass("inputDefault").build();
      tfDescr.textProperty().addListener((observable, oldValue, newValue) -> onChange());
   }

   private void createPanes() {
      paneSubTitle = new PaneBuilder().setWidth(INPUT_WIDTH).setHeight(SUBTITLE_HEIGHT).setStyleClass(STYLE_SUBTITLE_PANE).setChildren(lblSubTitle).build();
   }

   private VBox createVBox() {
      return new VBoxBuilder().setWidth(START_WIDTH).setHeight(BLOCK_HEIGHT).setChildren(paneSubTitle, lblName, tfName, lblDescr, tfDescr).build();
   }

   private void onChange() {
      mainScreen.checkStatus();
   }

}
