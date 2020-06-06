/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.charts.screens;

import com.radixpro.enigma.shared.Rosetta;
import com.radixpro.enigma.ui.shared.Help;
import com.radixpro.enigma.ui.shared.factories.ButtonFactory;
import com.radixpro.enigma.ui.shared.factories.LabelFactory;
import com.radixpro.enigma.ui.shared.factories.PaneFactory;
import com.radixpro.enigma.ui.shared.presentationmodel.PresentableAspect;
import com.radixpro.enigma.ui.shared.presentationmodel.PresentableMidpoint;
import com.radixpro.enigma.xchg.domain.analysis.IAnalyzedPair;
import com.radixpro.enigma.xchg.domain.analysis.MetaDataForAnalysis;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

import static com.radixpro.enigma.ui.shared.StyleDictionary.*;

/**
 * Screen with result of calcualted midpoints.
 */
public class ChartsMidpoints {

   private static final double WIDTH = 300.0;
   private static final double HEIGHT = 640.0;
   private static final double DATA_HEIGHT = 500.0;
   private static final double TITLE_HEIGHT = 45.0;
   private static final double GAP = 6.0;
   private final Stage stage;
   private final Rosetta rosetta;
   private final List<IAnalyzedPair> midpoints;
   private final MetaDataForAnalysis meta;


   /**
    * Constructor defines all properties. Instantiation should be done by ChartsScreenFActory.createCharsMidpoints().
    *
    * @param stage     The stage to use.
    * @param rosetta   Instance of Rosetta.
    * @param midpoints Calculated midpoints.
    * @param meta      Information about name and configuration.
    */
   public ChartsMidpoints(final Stage stage, final Rosetta rosetta, final List<IAnalyzedPair> midpoints,
                          final MetaDataForAnalysis meta) {
      this.stage = stage;
      this.rosetta = rosetta;
      this.midpoints = midpoints;
      this.meta = meta;
      stage.setScene(new Scene(createVBox()));
      stage.show();
   }

   private VBox createVBox() {
      VBox vBox = new VBox();
      vBox.setPadding(new Insets(GAP, GAP, GAP, GAP));
      vBox.getStylesheets().add(STYLESHEET);
      vBox.setPrefWidth(WIDTH);
      vBox.setPrefHeight(HEIGHT);
      vBox.getChildren().addAll(createPaneTitle(), createPaneMeta(), createOverview(), createButtonBar());
      return vBox;
   }

   private Pane createPaneTitle() {
      final Pane pane = PaneFactory.createPane(TITLE_HEIGHT, WIDTH, "titlepane");
      pane.getChildren().add(LabelFactory.createLabel(rosetta.getText("ui.charts.midpoints.pagetitle"), "titletext", WIDTH));
      return pane;
   }

   private Pane createPaneMeta() {
      final Pane pane = new Pane();
      pane.setPrefWidth(WIDTH);
      pane.setPrefHeight(TITLE_HEIGHT);
      VBox vBox = new VBox();
      vBox.setPrefWidth(WIDTH);
      Label lblName = new Label(rosetta.getText("ui.charts.midpoints.lbl.nameprefix") + " " + meta.getName());
      Label lblConfig = new Label(rosetta.getText("ui.charts.midpoints.lbl.configprefix") + " " + meta.getConfigName());
      vBox.getChildren().addAll(lblName, lblConfig);
      pane.getChildren().add(vBox);
      return pane;
   }

   private TableView createOverview() {
      TableView tvOverview = new TableView();
      tvOverview.setPrefHeight(DATA_HEIGHT);
      tvOverview.setPrefWidth(WIDTH);

      TableColumn<String, PresentableAspect> firstPoint = new TableColumn<>();
      TableColumn<String, PresentableAspect> secondPoint = new TableColumn<>();
      TableColumn<String, PresentableAspect> midpointType = new TableColumn<>("Disk");
      TableColumn<String, PresentableAspect> thirdPoint = new TableColumn<>();
      TableColumn<String, PresentableAspect> effectiveOrb = new TableColumn<>(rosetta.getText("ui.charts.midpoints.col.effectiveorb"));
      TableColumn<String, PresentableAspect> percOrb = new TableColumn<>(rosetta.getText("ui.charts.midpoints.col.percorb"));
      firstPoint.setStyle(FONT_STYLE_GLYPH);
      secondPoint.setStyle(FONT_STYLE_GLYPH);
      midpointType.setStyle(FONT_STYLE_DATA);
      thirdPoint.setStyle(FONT_STYLE_GLYPH);

      effectiveOrb.setStyle(FONT_STYLE_DATA);
      percOrb.setStyle(FONT_STYLE_DATA);
      tvOverview.getColumns().addAll(firstPoint, secondPoint, midpointType, thirdPoint, effectiveOrb, percOrb);

      for (IAnalyzedPair pair : midpoints) {
         PresentableMidpoint presMidpoint = new PresentableMidpoint(pair);
         firstPoint.setCellValueFactory(new PropertyValueFactory<>("firstItemGlyph"));
         secondPoint.setCellValueFactory(new PropertyValueFactory<>("secondItemGlyph"));
         thirdPoint.setCellValueFactory(new PropertyValueFactory<>("thirdItemGlyph"));
         midpointType.setCellValueFactory(new PropertyValueFactory<>("midpointType"));
         effectiveOrb.setCellValueFactory(new PropertyValueFactory<>("effectiveOrb"));
         percOrb.setCellValueFactory(new PropertyValueFactory<>("percOrb"));
         tvOverview.getItems().add(presMidpoint);
      }
      return tvOverview;
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

   private void onHelp() {
      new Help(rosetta.getHelpText("help.chartsmidpoints.title"),
            rosetta.getHelpText("help.chartsmidpoints.content"));

   }
}
