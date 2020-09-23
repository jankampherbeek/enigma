/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.SessionState;
import com.radixpro.enigma.domain.analysis.MetaDataForAnalysis;
import com.radixpro.enigma.domain.astronpos.FullPointPosition;
import com.radixpro.enigma.domain.astronpos.IPosition;
import com.radixpro.enigma.domain.input.DateTimeJulian;
import com.radixpro.enigma.domain.input.Location;
import com.radixpro.enigma.domain.reqresp.TetenburgRequest;
import com.radixpro.enigma.domain.reqresp.TetenburgResponse;
import com.radixpro.enigma.references.CelestialObjects;
import com.radixpro.enigma.references.TimeZones;
import com.radixpro.enigma.ui.charts.screens.helpers.GlyphForSign;
import com.radixpro.enigma.ui.creators.ButtonBuilder;
import com.radixpro.enigma.ui.creators.LabelBuilder;
import com.radixpro.enigma.ui.creators.PaneBuilder;
import com.radixpro.enigma.ui.creators.TextFieldBuilder;
import com.radixpro.enigma.ui.domain.FullChart;
import com.radixpro.enigma.ui.helpers.DateTimeJulianCreator;
import com.radixpro.enigma.ui.shared.Help;
import com.radixpro.enigma.ui.shared.formatters.SexagesimalFormatter;
import com.radixpro.enigma.ui.validators.ValidatedDate;
import com.radixpro.enigma.xchg.api.TetenburgApi;
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
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.radixpro.enigma.shared.common.EnigmaDictionary.GLYPH_FONTNAME;
import static com.radixpro.enigma.shared.common.EnigmaDictionary.TEXT_FONTNAME;
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
   private final SessionState state;
   private final TetenburgApi api;
   private final DateTimeJulianCreator dateTimeJulianCreator;
   private Stage stage;
   private MetaDataForAnalysis meta;
   private FullChart fullChart;
   private String calendar;
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
   private final ValidatedDate valDate;
   private boolean dateValid = false;


   public ChartsTetenburg(@NotNull final TetenburgApi api,
                          @NotNull final ValidatedDate valDate, @NotNull final DateTimeJulianCreator dateTimeJulianCreator) {
      this.state = SessionState.INSTANCE;
      this.api = api;
      this.valDate = valDate;
      this.dateTimeJulianCreator = dateTimeJulianCreator;
   }

   public void show(final MetaDataForAnalysis meta) {
      stage = new Stage();
      this.meta = meta;
      this.fullChart = state.getSelectedChart();
      this.calendar = fullChart.getChartData().getDateTimeJulian().getCalendar();
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
      lblName = new Label(Rosetta.getText("ui.charts.tetenburg.lbl.nameprefix") + " " + meta.getName());
      lblConfig = new Label(Rosetta.getText("ui.charts.tetenburg.lbl.configprefix") + " " + meta.getConfigName());
      lblDate = new LabelBuilder("ui.charts.tetenburg.lbl.progdate").setPrefWidth(WIDTH * 0.4).build();
      lblResultValue = new Label("");
      lblSignGlyph = new Label("");
      tfDate = new TextFieldBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(WIDTH * 0.5).setStyleClass("inputDefault").build();
   }

   private void definePanes() {
      paneTitle = new PaneBuilder().setHeight(TITLE_HEIGHT).setWidth(WIDTH).setStyleClass("titlepane").build();
      paneMeta = new PaneBuilder().setHeight(TITLE_HEIGHT).setWidth(WIDTH).build();
      paneInput = new PaneBuilder().setHeight(TITLE_HEIGHT).setWidth(WIDTH).build();
      paneCalcBtn = new PaneBuilder().setHeight(INPUT_HEIGHT).setWidth(WIDTH).build();
      paneSeparator = new PaneBuilder().setHeight(SEPARATOR_HEIGHT).setWidth(WIDTH).build();
      paneResult = new PaneBuilder().setHeight(TITLE_HEIGHT).setWidth(WIDTH).build();
      paneResult.setPadding(new Insets(GAP, GAP, GAP, GAP));
   }

   private void defineButtons() {
      btnCalc = new ButtonBuilder("ui.shared.btn.calculate").setDisabled(true).build();
      btnHelp = new ButtonBuilder("ui.shared.btn.help").setDisabled(false).build();
      btnExit = new ButtonBuilder("ui.shared.btn.exit").setDisabled(false).build();
   }

   private void defineStructure() {
      paneTitle.getChildren().add(new LabelBuilder("ui.charts.tetenburg.pagetitle").setStyleClass("titletext").setPrefWidth(WIDTH).build());
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
      dateValid = valDate.validate(newDate + '/' + calendar);
      tfDate.setStyle(dateValid ? INPUT_DEFAULT_STYLE : INPUT_ERROR_STYLE);
      checkStatus();
   }

   private void onHelp() {
      new Help(Rosetta.getHelpText("help.chartstetenburg.title"), Rosetta.getHelpText("help.chartstetenburg.content"));
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
      DateTimeJulian birthDateTime = fullChart.getChartData().getDateTimeJulian();
      String dateText = tfDate.getText();
      String timeText = "0:0:0";
      String cal = fullChart.getChartData().getDateTimeJulian().getCalendar();
      TimeZones zone = TimeZones.UT;
      double offsetLmt = 0.0;
      DateTimeJulian progDateTime = dateTimeJulianCreator.createDateTime(dateText, cal, timeText, zone, false, offsetLmt);

      TetenburgRequest request = new TetenburgRequest(longMc, solarSpeed, location, birthDateTime, progDateTime);
      TetenburgResponse response = api.calculateCriticalPoint(request);
      if (!response.getResultMsg().equals("OK")) lblResultValue.setText(response.getResultMsg());
      else {
         double longAsc = response.getLongAsc();
         double longitudeInSign = longAsc % 30;
         SexagesimalFormatter formatter = new SexagesimalFormatter(2);
         String longAscTxt = formatter.formatDm(longitudeInSign);
         lblResultValue.setFont(new Font(TEXT_FONTNAME, 14));
         lblResultValue.setText(Rosetta.getText("ui.charts.tetenburg.lbl.resultprefix") + longAscTxt);
         lblSignGlyph.setFont(new Font(GLYPH_FONTNAME, 16));
         lblSignGlyph.setText(new GlyphForSign().getGlyph((int) (longAsc / 30.0) + 1));
      }
   }

   private void checkStatus() {
      boolean inputOk = dateValid;
      btnCalc.setDisable(!inputOk);
      btnCalc.setFocusTraversable(inputOk);
   }


}
