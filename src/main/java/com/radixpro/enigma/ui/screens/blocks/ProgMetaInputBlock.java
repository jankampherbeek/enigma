/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens.blocks;

import com.radixpro.enigma.SessionState;
import com.radixpro.enigma.references.InputStatus;
import com.radixpro.enigma.ui.creators.LabelBuilder;
import com.radixpro.enigma.ui.creators.TextFieldBuilder;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import static com.radixpro.enigma.ui.shared.UiDictionary.*;

/**
 * Input block for ProgMeta (Meta info for an event that is analyzed).
 */
public class ProgMetaInputBlock extends InputBlock {

   private static final double HEIGHT = 100.0;
   private VBox vBox;
   private Label lblMetaChart;
   private Label lblMetaConfig;
   private Label lblInputEvent;
   private TextField tfEvent;

   public ProgMetaInputBlock(SessionState state) {
      super(state);
   }

   @Override
   protected void initialize() {
      inputStatus = InputStatus.READY;    // always ready, as no validation is done.
      lblMetaChart = new LabelBuilder("").setText(rosetta.getText("ui.charts.meta.chartname") + " " +
            state.getSelectedChart().getChartData().getChartMetaData().getName()).setPrefWidth(INPUT_WIDTH).build();
      lblMetaConfig = new LabelBuilder("").setText(rosetta.getText("ui.charts.meta.configname") + " " + state.getSelectedConfig().getName()).
            setPrefWidth(INPUT_WIDTH).build();
      lblInputEvent = new LabelBuilder("ui.progmetainput.lbl.eventinput").setPrefWidth(INPUT_WIDTH).build();
      tfEvent = new TextFieldBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(INPUT_DATA_WIDTH).setStyleClass(INPUT_STYLE).build();
      createVBox();
   }

   private void createVBox() {
      vBox = new VBox();
      vBox.getStylesheets().add(STYLESHEET);
      vBox.setPrefWidth(INPUT_WIDTH);
      vBox.setPadding(new Insets(GAP, GAP, GAP, GAP));
      vBox.setPrefHeight(HEIGHT);
      vBox.getChildren().addAll(lblMetaChart, lblMetaConfig, lblInputEvent, tfEvent);
   }

   /**
    * Return populated VBox.
    *
    * @return Instance of VBox.
    */
   public VBox getVBox() {
      initialize();
      return vBox;
   }

   /**
    * If an event description has been entered, returns the text. Otherwise returns empty string.
    *
    * @return Text for event description.
    */
   public String getEventDescription() {
      return tfEvent.getText().isEmpty() ? "" : tfEvent.getText();
   }

}
