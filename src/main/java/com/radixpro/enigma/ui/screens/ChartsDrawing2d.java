/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.SessionState;
import com.radixpro.enigma.domain.config.Configuration;
import com.radixpro.enigma.ui.charts.screens.helpers.ChartDrawMetrics;
import com.radixpro.enigma.ui.domain.FullChart;
import com.radixpro.enigma.ui.screens.helpers.RadixWheel;
import com.radixpro.enigma.ui.shared.Help;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class ChartsDrawing2d {

   private static final double GAP = 6.0;
   private final Rosetta rosetta;
   private final Stage stage;
   private final SessionState state;
   private ChartDrawMetrics metrics;
   private Canvas canvas;
   private FullChart fullChart;
   private GraphicsContext gc;
   private String name;
   private Configuration currentConfig;
   private final RadixWheel radixWheel;


   public ChartsDrawing2d(@NotNull final RadixWheel radixWheel, @NotNull final Rosetta rosetta) {
      this.state = SessionState.INSTANCE;
      this.radixWheel = radixWheel;
      this.rosetta = rosetta;
      stage = new Stage();
   }

   public void setDrawingInfo() {                  // todo call from constructor
      this.fullChart = state.getSelectedChart();
      this.currentConfig = state.getSelectedConfig();
      this.name = fullChart.getChartData().getChartMetaData().getName();
      drawChart();
   }

   private void drawChart() {
      metrics = new ChartDrawMetrics();
      canvas = new Canvas(metrics.getCanvasDimension(), metrics.getCanvasDimension());
      gc = canvas.getGraphicsContext2D();
      gc.setFont(new Font("Courier", 10));

      Pane chartPane = new Pane(canvas);
      Label lblName = new Label();
      lblName.setText(rosetta.getText("ui.charts.draw.nameprefix") + " " + name);

      GridPane gridPane = new GridPane();
      gridPane.setPadding(new Insets(GAP, GAP, GAP, GAP));
      ColumnConstraints columnConstraints = new ColumnConstraints();
      columnConstraints.setPercentWidth(50.0);
      gridPane.getColumnConstraints().add(0, columnConstraints);
      gridPane.getColumnConstraints().add(1, columnConstraints);
      RowConstraints rowConstraintsTitle = new RowConstraints();
      rowConstraintsTitle.setMaxHeight(40.0);
      rowConstraintsTitle.setMinHeight(40.0);
      rowConstraintsTitle.setValignment(VPos.CENTER);
      RowConstraints rowConstraintsChart = new RowConstraints();
      rowConstraintsChart.setPrefHeight(840.0);
      RowConstraints rowConstraintsButtons = new RowConstraints();
      rowConstraintsButtons.setMaxHeight(40.0);
      rowConstraintsButtons.setMinHeight(40.0);
      gridPane.getRowConstraints().add(0, rowConstraintsTitle);
      gridPane.getRowConstraints().add(1, rowConstraintsChart);
      gridPane.getRowConstraints().add(2, rowConstraintsButtons);
      gridPane.setGridLinesVisible(false);

      gridPane.add(lblName, 0, 0, 2, 1);          // node col row colspan rowspan
      gridPane.add(chartPane, 0, 1, 2, 1);
      gridPane.add(createBtnBar(), 1, 2, 1, 1);

      stage.setMinHeight(400.0);
      stage.setMinWidth(320.0);
      stage.setScene(new Scene(gridPane, 700, 1000));  // pane, hor pos, vert pos
      stage.setTitle(rosetta.getText("ui.charts.draw.title"));
      stage.show();

      canvas.widthProperty().bind(chartPane.widthProperty());
      canvas.heightProperty().bind(chartPane.heightProperty());
      metrics.setCanvasDimension(Math.min(canvas.getWidth(), canvas.getHeight()));
      stageSizeChangeListener(stage);
      performDraw();
   }

   private ButtonBar createBtnBar() {
      ButtonBar btnBar = new ButtonBar();
      Button btnClose = new Button(rosetta.getText("ui.shared.btn.exit"));
      btnClose.setDefaultButton(true);
      Button btnHelp = new Button(rosetta.getText("ui.shared.btn.help"));
      btnHelp.setOnAction(click -> onHelp());
      btnClose.setOnAction(click -> stage.close());
      btnBar.getButtons().add(btnHelp);
      btnBar.getButtons().add(btnClose);
      return btnBar;
   }

   private void stageSizeChangeListener(Stage stage) {
      // check https://stackoverflow.com/questions/11377639/how-can-one-detect-a-finished-resizing-operation-in-javafx
      // for bug Rezising
      stage.widthProperty().addListener((observable, oldValue, newValue) -> resizeDrawing());
      stage.heightProperty().addListener((observable, oldValue, newValue) -> resizeDrawing());
   }

   private void resizeDrawing() {
      metrics.setCanvasDimension(Math.min(canvas.getWidth(), canvas.getHeight()));
      performDraw();
   }

   private void performDraw() {
      gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
      gc.setFill(Color.WHITE);
      gc.fill();
      gc.setStroke(Color.BLUE);
      gc.setLineWidth(metrics.getWidthMediumLines());
      gc.setGlobalAlpha(0.5d);
      radixWheel.drawWheel(gc, metrics, fullChart, currentConfig);
   }

   private void onHelp() {
      new Help(rosetta.getHelpText("help.chartsdrawing.title"), rosetta.getHelpText("help.chartsdrawing.content"));
   }


}
