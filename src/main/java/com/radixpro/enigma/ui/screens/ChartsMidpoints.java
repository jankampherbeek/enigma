/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.SessionState;
import com.radixpro.enigma.domain.analysis.IAnalyzedPair;
import com.radixpro.enigma.domain.analysis.MetaDataForAnalysis;
import com.radixpro.enigma.domain.config.Configuration;
import com.radixpro.enigma.ui.creators.ButtonBuilder;
import com.radixpro.enigma.ui.creators.LabelBuilder;
import com.radixpro.enigma.ui.creators.PaneBuilder;
import com.radixpro.enigma.ui.screens.helpers.ChartDataHelper;
import com.radixpro.enigma.ui.shared.Help;
import com.radixpro.enigma.ui.shared.presentationmodel.PresentableAspect;
import com.radixpro.enigma.ui.shared.presentationmodel.PresentableMidpoint;
import com.radixpro.enigma.xchg.api.MidpointsApi;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

import static com.radixpro.enigma.ui.shared.UiDictionary.*;

/**
 * Screen with result of calculated midpoints.
 */
public class ChartsMidpoints {

   private static final double WIDTH = 300.0;
   private static final double HEIGHT = 640.0;
   private static final double DATA_HEIGHT = 500.0;
   private static final double TITLE_HEIGHT = 45.0;
   private static final double GAP = 6.0;
   private final SessionState state;
   private final Rosetta rosetta;
   private final MidpointsApi midpointsApi;
   private final ChartDataHelper helper;
   private Stage stage;
   private List<IAnalyzedPair> midpoints;
   private MetaDataForAnalysis meta;


   public ChartsMidpoints(final MidpointsApi midpointsApi, final ChartDataHelper helper) {
      this.rosetta = Rosetta.getRosetta();
      this.state = SessionState.INSTANCE;
      this.midpointsApi = midpointsApi;
      this.helper = helper;
   }

   public void show() {
      populate();
      stage = new Stage();
      stage.setScene(new Scene(createVBox()));
      stage.show();
   }

   private void populate() {
      Configuration config = state.getSelectedConfig();
      midpoints = midpointsApi.analyseMidpoints(helper.getCelObjectList(), helper.getHousesList());
      meta = new MetaDataForAnalysis(helper.getChartName(), config.getName(), 1.6);   // TODO replace hardcoded orb for midpoints with configurable orb
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
      final Pane pane = new PaneBuilder().setHeight(TITLE_HEIGHT).setWidth(WIDTH).setStyleClass("titlepane").build();
      pane.getChildren().add(new LabelBuilder("ui.charts.midpoints.pagetitle").setPrefWidth(WIDTH).setStyleClass("titletext").build());
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
      Button btnHelp = new ButtonBuilder("ui.shared.btn.help").setDisabled(false).build();
      Button btnExit = new ButtonBuilder("ui.shared.btn.exit").setDisabled(false).build();

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
