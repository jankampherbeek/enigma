/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.charts.screens;

import com.radixpro.enigma.be.calc.main.CelObjectPosition;
import com.radixpro.enigma.shared.Rosetta;
import com.radixpro.enigma.ui.charts.screens.helpers.GlyphForSign;
import com.radixpro.enigma.ui.shared.Help;
import com.radixpro.enigma.ui.shared.factories.ButtonFactory;
import com.radixpro.enigma.ui.shared.factories.LabelFactory;
import com.radixpro.enigma.ui.shared.factories.PaneFactory;
import com.radixpro.enigma.ui.shared.formatters.SexagesimalFormatter;
import com.radixpro.enigma.ui.shared.validation.ValidatedDate;
import com.radixpro.enigma.xchg.api.TetenburgApi;
import com.radixpro.enigma.xchg.api.factories.ApiProgFactory;
import com.radixpro.enigma.xchg.api.requests.TetenburgRequest;
import com.radixpro.enigma.xchg.api.responses.TetenburgResponse;
import com.radixpro.enigma.xchg.domain.*;
import com.radixpro.enigma.xchg.domain.analysis.MetaDataForAnalysis;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import static com.radixpro.enigma.ui.shared.StyleDictionary.*;

/**
 * Screen for critical points according to Ton Tetenburg. Handles data input and showing result.
 */
public class ChartsTetenburg {

   private static final double WIDTH = 450.0;
   private static final double HEIGHT = 440.0;
   private static final double TITLE_HEIGHT = 45.0;
   private static final double SEPARATOR_HEIGHT = 20.0;
   private static final double GAP = 6.0;
   private final Stage stage;
   private final Rosetta rosetta;
   private final MetaDataForAnalysis meta;
   private final FullChart fullChart;
   private final String calendar;
   private Button btnCalc;
   private Label lblResultValue;
   private Label lblSignGlyph;
   private TextField tfDate;
   private ValidatedDate valDate;


   public ChartsTetenburg(final Stage stage, final Rosetta rosetta, final MetaDataForAnalysis meta, final FullChart fullChart) {
      this.stage = stage;
      this.rosetta = rosetta;
      this.meta = meta;
      this.fullChart = fullChart;
      this.calendar = fullChart.getFullDateTime().getSimpleDateTime().getDate().isGregorian() ? "g" : "j";
      stage.setScene(new Scene(createVBox()));
      checkStatus();
      stage.show();
   }

   private VBox createVBox() {
      VBox vBox = new VBox();
      vBox.setPadding(new Insets(GAP, GAP, GAP, GAP));
      vBox.getStylesheets().add(STYLESHEET);
      vBox.setPrefWidth(WIDTH);
      vBox.setPrefHeight(HEIGHT);
      vBox.getChildren().addAll(createPaneTitle(), createPaneMeta(), createPaneInput(), createPaneCalcBtn(), createPaneSeparator(), createResultPane(),
            createPaneSeparator(), createButtonBar());
      return vBox;
   }

   private Pane createPaneTitle() {
      final Pane pane = PaneFactory.createPane(TITLE_HEIGHT, WIDTH, "titlepane");
      pane.getChildren().add(LabelFactory.createLabel(rosetta.getText("ui.charts.tetenburg.pagetitle"), "titletext", WIDTH));
      return pane;
   }

   private Pane createPaneMeta() {
      final Pane pane = new Pane();
      pane.setPrefWidth(WIDTH);
      pane.setPrefHeight(TITLE_HEIGHT);
      VBox vBox = new VBox();
      vBox.setPrefWidth(WIDTH);
      Label lblName = new Label(rosetta.getText("ui.charts.tetenburg.lbl.nameprefix") + " " + meta.getName());
      Label lblConfig = new Label(rosetta.getText("ui.charts.tetenburg.lbl.configprefix") + " " + meta.getConfigName());
      vBox.getChildren().addAll(lblName, lblConfig);
      pane.getChildren().add(vBox);
      return pane;
   }

   private Pane createPaneInput() {
      final Pane pane = new Pane();
      pane.setPrefWidth(WIDTH);
      pane.setPrefHeight(TITLE_HEIGHT);
      HBox hBox = new HBox();
      hBox.setPrefWidth(WIDTH);
      Label lblDate = LabelFactory.createLabel(rosetta.getText("ui.charts.tetenburg.lbl.progdate"), WIDTH * 0.4);
      tfDate = new TextField();
      tfDate.setPrefWidth(WIDTH * 0.5);
      tfDate.setAlignment(Pos.CENTER_RIGHT);
      tfDate.textProperty().addListener((observable, oldValue, newValue) -> validateDate(newValue));
      hBox.getChildren().addAll(lblDate, tfDate);
      pane.getChildren().add(hBox);
      return pane;
   }

   private Pane createPaneCalcBtn() {
      final Pane pane = new Pane();
      pane.setPrefWidth(WIDTH);
      btnCalc = new Button(rosetta.getText("ui.shared.btn.calculate"));
      btnCalc.setOnAction(click -> onCalc());
      pane.getChildren().add(btnCalc);
      return pane;
   }

   private Pane createPaneSeparator() {
      return PaneFactory.createPane(SEPARATOR_HEIGHT, WIDTH);
   }


   private Pane createResultPane() {
      final Pane pane = new Pane();
      pane.setPrefWidth(WIDTH);
      pane.setPrefHeight(TITLE_HEIGHT);
      pane.setPadding(new Insets(GAP, GAP, GAP, GAP));
      lblResultValue = new Label("");
      lblSignGlyph = new Label("");
      HBox hBox = new HBox();
      hBox.setPrefWidth(WIDTH);
      hBox.getChildren().addAll(lblResultValue, lblSignGlyph);
      pane.getChildren().add(hBox);
      return pane;
   }

   private ButtonBar createButtonBar() {
      ButtonBar buttonBar = new ButtonBar();
      buttonBar.setPadding(new Insets(GAP, GAP, GAP, GAP));
      Button btnHelp = ButtonFactory.createButton(rosetta.getText("ui.shared.btn.help"), false);
      Button btnExit = ButtonFactory.createButton(rosetta.getText("ui.shared.btn.exit"), false);

      btnHelp.setOnAction(click -> onHelp());
      btnExit.setOnAction(click -> stage.close());

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
      double longMc = fullChart.getAllHousePositions().get(0).getEclipticCoords().getPosition().getBase();
      double solarSpeed = 0.0;
      final List<CelObjectPosition> bodies = fullChart.getBodies();
      for (CelObjectPosition celPos : bodies) {
         if (celPos.getCelestialBody() == CelestialObjects.SUN) {
            solarSpeed = celPos.getEclipticalPosition().getMainSpeed();
         }
      }
      Location location = fullChart.getLocation();
      FullDateTime birthDateTime = fullChart.getFullDateTime();

      SimpleTime simpleTime = new SimpleTime(0, 0, 0);
      SimpleDateTime simpleDateTime = new SimpleDateTime(valDate.getSimpleDate(), simpleTime);
      FullDateTime progDateTime = new FullDateTime(simpleDateTime, birthDateTime.getTimeZone(), birthDateTime.isDst(), birthDateTime.getOffsetForLmt());
      TetenburgApi api = new ApiProgFactory().getTetenburgApi();
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
