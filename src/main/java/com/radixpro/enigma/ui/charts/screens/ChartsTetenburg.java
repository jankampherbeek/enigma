/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.charts.screens;

import com.radixpro.enigma.shared.Rosetta;
import com.radixpro.enigma.ui.charts.screens.helpers.GlyphForSign;
import com.radixpro.enigma.ui.domain.FullChart;
import com.radixpro.enigma.ui.shared.Help;
import com.radixpro.enigma.ui.shared.creators.ButtonFactory;
import com.radixpro.enigma.ui.shared.creators.LabelFactory;
import com.radixpro.enigma.ui.shared.creators.PaneFactory;
import com.radixpro.enigma.ui.shared.creators.TextFieldFactory;
import com.radixpro.enigma.ui.shared.formatters.SexagesimalFormatter;
import com.radixpro.enigma.ui.shared.validation.ValidatedDate;
import com.radixpro.enigma.xchg.api.ApiFactory;
import com.radixpro.enigma.xchg.api.TetenburgApi;
import com.radixpro.enigma.xchg.api.requests.TetenburgRequest;
import com.radixpro.enigma.xchg.api.responses.TetenburgResponse;
import com.radixpro.enigma.xchg.domain.*;
import com.radixpro.enigma.xchg.domain.analysis.MetaDataForAnalysis;
import com.radixpro.enigma.xchg.domain.astrondata.FullPointPosition;
import com.radixpro.enigma.xchg.domain.astrondata.IPosition;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

import static com.radixpro.enigma.shared.EnigmaDictionary.GLYPH_FONTNAME;
import static com.radixpro.enigma.shared.EnigmaDictionary.TEXT_FONTNAME;
import static com.radixpro.enigma.ui.shared.UiDictionary.*;

/**
 * Screen for critical points according to Ton Tetenburg. Handles data input and showing result.
 */
public class ChartsTetenburg {

   private static final double WIDTH = 450.0;
   private static final double HEIGHT = 440.0;
   private static final double TITLE_HEIGHT = 45.0;
   private static final double SEPARATOR_HEIGHT = 20.0;
   private static final double INPUT_HEIGHT = 25.0;
   private static final double GAP = 6.0;
   private final Stage stage;
   private final Rosetta rosetta;
   private final MetaDataForAnalysis meta;
   private final FullChart fullChart;
   private final String calendar;
   private Button btnCalc;
   private Button btnExit;
   private Button btnHelp;
   private Label lblConfig;
   private Label lblDate;
   private Label lblName;
   private Label lblResultValue;
   private Label lblSignGlyph;
   private Pane paneCalcBtn;
   private Pane paneInput;
   private Pane paneMeta;
   private Pane paneResult;
   private Pane paneSeparator;
   private Pane paneTitle;
   private TextField tfDate;
   private ValidatedDate valDate;


   public ChartsTetenburg(final Stage stage, final Rosetta rosetta, final MetaDataForAnalysis meta, final FullChart fullChart) {
      this.stage = stage;
      this.rosetta = rosetta;
      this.meta = meta;
      this.fullChart = fullChart;
      this.calendar = fullChart.getChartData().getFullDateTime().getSimpleDateTime().getDate().isGregorian() ? "g" : "j";
      defineLeafs();
      definePanes();
      defineButtons();
      defineStructure();
      defineActions();
      defineListeners();

      stage.setScene(new Scene(createVBox()));
      checkStatus();
      stage.showAndWait();
   }

   private void defineLeafs() {
      lblName = new Label(rosetta.getText("ui.charts.tetenburg.lbl.nameprefix") + " " + meta.getName());
      lblConfig = new Label(rosetta.getText("ui.charts.tetenburg.lbl.configprefix") + " " + meta.getConfigName());
      lblDate = LabelFactory.createLabel(rosetta.getText("ui.charts.tetenburg.lbl.progdate"), WIDTH * 0.4);
      lblResultValue = new Label("");
      lblSignGlyph = new Label("");
      tfDate = TextFieldFactory.createTextField(INPUT_HEIGHT, WIDTH * 0.5, "inputDefault");
   }

   private void definePanes() {
      paneTitle = PaneFactory.createPane(TITLE_HEIGHT, WIDTH, "titlepane");
      paneMeta = PaneFactory.createPane(TITLE_HEIGHT, WIDTH);
      paneInput = PaneFactory.createPane(TITLE_HEIGHT, WIDTH);
      paneCalcBtn = PaneFactory.createPane(INPUT_HEIGHT, WIDTH);
      paneSeparator = PaneFactory.createPane(SEPARATOR_HEIGHT, WIDTH);
      paneResult = PaneFactory.createPane(TITLE_HEIGHT, WIDTH);
      paneResult.setPadding(new Insets(GAP, GAP, GAP, GAP));
   }

   private void defineButtons() {
      btnCalc = ButtonFactory.createButton(rosetta.getText("ui.shared.btn.calculate"), true);
      btnHelp = ButtonFactory.createButton(rosetta.getText("ui.shared.btn.help"), false);
      btnExit = ButtonFactory.createButton(rosetta.getText("ui.shared.btn.exit"), false);
   }

   private void defineStructure() {
      paneTitle.getChildren().add(LabelFactory.createLabel(rosetta.getText("ui.charts.tetenburg.pagetitle"), "titletext", WIDTH));
      paneMeta.getChildren().add(createVBoxMeta());
      paneInput.getChildren().add(createHBoxInput());
      paneCalcBtn.getChildren().add(btnCalc);
      paneResult.getChildren().add(createHBoxResult());
   }

   private void defineActions() {
      btnCalc.setOnAction(click -> onCalc());
      btnHelp.setOnAction(click -> onHelp());
      btnExit.setOnAction(click -> stage.close());
   }

   private void defineListeners() {
      tfDate.textProperty().addListener((observable, oldValue, newValue) -> validateDate(newValue));
   }

   private VBox createVBox() {
      VBox vBox = new VBox();
      vBox.setPadding(new Insets(GAP, GAP, GAP, GAP));
      vBox.getStylesheets().add(STYLESHEET);
      vBox.setPrefWidth(WIDTH);
      vBox.setPrefHeight(HEIGHT);
      vBox.getChildren().addAll(paneTitle, paneMeta, paneInput, paneCalcBtn, paneSeparator, paneResult, createButtonBar());
      return vBox;
   }

   private VBox createVBoxMeta() {
      VBox vBox = new VBox();
      vBox.setPrefWidth(WIDTH);
      vBox.getChildren().addAll(lblName, lblConfig);
      return vBox;
   }

   private HBox createHBoxInput() {
      HBox hBox = new HBox();
      hBox.setPrefWidth(WIDTH);
      hBox.getChildren().addAll(lblDate, tfDate);
      return hBox;
   }

   private Pane createHBoxResult() {
      HBox hBox = new HBox();
      hBox.setPrefWidth(WIDTH);
      hBox.getChildren().addAll(lblResultValue, lblSignGlyph);
      return hBox;
   }

   private ButtonBar createButtonBar() {
      ButtonBar buttonBar = new ButtonBar();
      buttonBar.setPadding(new Insets(GAP, GAP, GAP, GAP));
      buttonBar.getButtons().addAll(btnHelp, btnExit);
      return buttonBar;
   }

   private void validateDate(final String newDate) {
      valDate = new ValidatedDate(newDate + '/' + calendar);
      tfDate.setStyle(valDate.isValidated() ? INPUT_DEFAULT_STYLE : INPUT_ERROR_STYLE);
      checkStatus();
   }

   private void onHelp() {
      new Help(rosetta.getHelpText("help.chartstetenburg.title"), rosetta.getHelpText("help.chartstetenburg.content"));
   }

   private void onCalc() {
      double longMc = fullChart.getCalculatedChart().getMundPoints().getMc().getLongitude();
      double solarSpeed = 0.0;
      final List<IPosition> bodies = fullChart.getCalculatedChart().getCelPoints();
      for (IPosition celPos : bodies) {
         FullPointPosition fpp = (FullPointPosition) celPos;
         if (fpp.getChartPoint() == CelestialObjects.SUN) {
            solarSpeed = fpp.getEclPos().getSpeed().getMainCoord();
         }
      }
      Location location = fullChart.getChartData().getLocation();
      FullDateTime birthDateTime = fullChart.getChartData().getFullDateTime();

      SimpleTime simpleTime = new SimpleTime(0, 0, 0);
      SimpleDateTime simpleDateTime = new SimpleDateTime(valDate.getSimpleDate(), simpleTime);
      FullDateTime progDateTime = new FullDateTime(simpleDateTime, birthDateTime.getTimeZone(), birthDateTime.isDst(), birthDateTime.getOffsetForLmt());
      TetenburgApi api = ApiFactory.getTetenburgApi();
      TetenburgRequest request = new TetenburgRequest(longMc, solarSpeed, location, birthDateTime, progDateTime);
      TetenburgResponse response = api.calculateCriticalPoint(request);
      if (!response.getResultMsg().equals("OK")) lblResultValue.setText(response.getResultMsg());
      else {
         double longAsc = response.getLongAsc();
         double longitudeInSign = longAsc % 30;
         SexagesimalFormatter formatter = new SexagesimalFormatter(2);
         String longAscTxt = formatter.formatDm(longitudeInSign);
         lblResultValue.setFont(new Font(TEXT_FONTNAME, 14));
         lblResultValue.setText(rosetta.getText("ui.charts.tetenburg.lbl.resultprefix") + longAscTxt);
         lblSignGlyph.setFont(new Font(GLYPH_FONTNAME, 16));
         lblSignGlyph.setText(new GlyphForSign().getGlyph((int) (longAsc / 30.0) + 1));
      }
   }

   private void checkStatus() {
      boolean inputOk = (valDate != null && valDate.isValidated());
      btnCalc.setDisable(!inputOk);
      btnCalc.setFocusTraversable(inputOk);
   }


}
