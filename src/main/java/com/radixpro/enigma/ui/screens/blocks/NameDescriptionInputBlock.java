/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens.blocks;

import com.radixpro.enigma.ui.creators.GridPaneBuilder;
import com.radixpro.enigma.ui.creators.LabelBuilderObs;
import com.radixpro.enigma.ui.creators.PaneBuilder;
import com.radixpro.enigma.ui.creators.TextFieldBuilder;
import com.radixpro.enigma.ui.screens.InputScreen;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import static com.radixpro.enigma.ui.shared.UiDictionary.*;

public class NameDescriptionInputBlock extends InputBlock {

   private static final double BLOCK_HEIGHT = 120.0;
   private static final double DATA_TEXT_WIDTH = 150.0;
   private static final double DATA_INPUT_WIDTH = 350.0;
   private InputScreen mainScreen;
   private TextField tfName;
   private TextField tfDescr;

   public NameDescriptionInputBlock() {
      super();
   }

   @Override
   protected void initialize() {
      createInputs();
   }

   public GridPane getGridPane(InputScreen mainScreen) {
      this.mainScreen = mainScreen;
      initialize();
      return createGridPane();
   }

   public String getName() {
      return tfName.getText();
   }

   public String getDescr() {
      return tfDescr.getText();
   }

   private void createInputs() {
      tfName = new TextFieldBuilder().setPrefWidth(DATA_INPUT_WIDTH).setPrefHeight(INPUT_HEIGHT).setStyleClass("inputDefault").build();
      tfName.textProperty().addListener((observable, oldValue, newValue) -> onChange());
      tfDescr = new TextFieldBuilder().setPrefWidth(DATA_INPUT_WIDTH).setPrefHeight(INPUT_HEIGHT).setStyleClass("inputDefault").build();
      tfDescr.textProperty().addListener((observable, oldValue, newValue) -> onChange());
   }

   private GridPane createGridPane() {
      GridPane gridPane = new GridPaneBuilder().setPrefHeight(BLOCK_HEIGHT).setPrefWidth(INPUT_WIDTH).setHGap(GAP).setVGap(GAP).build();
      gridPane.add(createPaneSubTitle(), 0, 0, 2, 1);
      gridPane.add(new LabelBuilderObs("ui.shared.lbl.name").setPrefWidth(DATA_TEXT_WIDTH).setPrefHeight(INPUT_HEIGHT).build(),
            0, 1, 1, 1);
      gridPane.add(tfName, 1, 1, 1, 1);
      gridPane.add(new LabelBuilderObs("ui.shared.lbl.description").setPrefWidth(DATA_TEXT_WIDTH).setPrefHeight(INPUT_HEIGHT).build(),
            0, 2, 1, 1);
      gridPane.add(tfDescr, 1, 2, 1, 1);
      return gridPane;
   }

   private Pane createPaneSubTitle() {
      Label label = new LabelBuilderObs("ui.shared.lbl.namedescsubtitle").setPrefWidth(INPUT_WIDTH).setStyleClass("subtitletext").build();
      return new PaneBuilder().setWidth(INPUT_WIDTH).setHeight(SUBTITLE_HEIGHT).setStyleClass(STYLE_SUBTITLE_PANE).setChildren(label).build();
   }

   private void onChange() {
      mainScreen.checkStatus();
   }

}
