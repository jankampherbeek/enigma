/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens;

import com.radixpro.enigma.shared.Rosetta;
import com.radixpro.enigma.ui.charts.screens.helpers.ChartIDrawMetrics;
import com.radixpro.enigma.ui.charts.screens.helpers.RadixWheel;
import com.radixpro.enigma.ui.shared.Help;
import com.radixpro.enigma.xchg.api.CalculatedFullChart;
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

import static com.google.common.base.Preconditions.checkNotNull;

public class ChartsDrawing2d {

   private static final double GAP = 6.0;
   private final Rosetta rosetta;
   private final Stage stage;
   private ChartIDrawMetrics metrics;
   private Canvas canvas;
   private CalculatedFullChart fullChart;
   private GraphicsContext gc;
   private String name;

   public ChartsDrawing2d() {
      rosetta = Rosetta.getRosetta();
      stage = new Stage();
   }

   public void setFullChart(final CalculatedFullChart fullChart) {
      this.fullChart = checkNotNull(fullChart);
      drawChart();
   }

   private void drawChart() {
      metrics = new ChartIDrawMetrics();
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
      new RadixWheel(gc, metrics, fullChart);
   }

   private void onHelp() {
      new Help(rosetta.getHelpText("help.chartsdrawing.title"), rosetta.getHelpText("help.chartsdrawing.content"));
   }

   public void setName(String name) {
      this.name = name;
   }


}
