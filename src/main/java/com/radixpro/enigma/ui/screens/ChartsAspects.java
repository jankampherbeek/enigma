/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens;

import com.radixpro.enigma.shared.common.Rosetta;
import com.radixpro.enigma.shared.common.SessionState;
import com.radixpro.enigma.ui.screens.helpers.ChartDataHelper;
import com.radixpro.enigma.ui.shared.Help;
import com.radixpro.enigma.ui.shared.creators.ButtonFactory;
import com.radixpro.enigma.ui.shared.creators.LabelFactory;
import com.radixpro.enigma.ui.shared.creators.PaneFactory;
import com.radixpro.enigma.ui.shared.presentationmodel.PresentableAspect;
import com.radixpro.enigma.xchg.api.AspectsApi;
import com.radixpro.enigma.xchg.domain.analysis.IAnalyzedPair;
import com.radixpro.enigma.xchg.domain.analysis.MetaDataForAnalysis;
import com.radixpro.enigma.xchg.domain.config.Configuration;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

import static com.radixpro.enigma.ui.shared.UiDictionary.*;

public class ChartsAspects {

   private static final double WIDTH = 300.0;
   private static final double HEIGHT = 440.0;
   private static final double DATA_HEIGHT = 300.0;
   private final SessionState state;
   private final Rosetta rosetta;
   private final AspectsApi aspectsApi;
   private final ChartDataHelper helper;
   private Stage stage;
   private List<IAnalyzedPair> aspects;
   private MetaDataForAnalysis meta;

   public ChartsAspects(final SessionState state, final Rosetta rosetta, final AspectsApi aspectsApi, final ChartDataHelper helper) {
      this.rosetta = rosetta;
      this.aspectsApi = aspectsApi;
      this.state = state;
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
      aspects = aspectsApi.analyzeAspects(helper.getCelObjectList(), helper.getHousesList(), config.getDelinConfiguration().getAspectConfiguration());
      meta = new MetaDataForAnalysis(helper.getChartName(), config.getName(), config.getDelinConfiguration().getAspectConfiguration().getBaseOrb());
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
      pane.getChildren().add(LabelFactory.createLabel(rosetta.getText("ui.charts.aspects.pagetitle"), "titletext", WIDTH));
      return pane;
   }

   private Pane createPaneMeta() {
      final Pane pane = new Pane();
      pane.setPrefWidth(WIDTH);
      pane.setPrefHeight(TITLE_HEIGHT);
      VBox vBox = new VBox();
      vBox.setPrefWidth(WIDTH);
      Label lblName = new Label(rosetta.getText("ui.charts.aspects.lbl.nameprefix") + " " + meta.getName());
      Label lblConfig = new Label(rosetta.getText("ui.charts.aspects.lbl.configprefix") + " " + meta.getConfigName());
      vBox.getChildren().addAll(lblName, lblConfig);
      pane.getChildren().add(vBox);
      return pane;
   }

   private TableView createOverview() {
      TableView tvOverview = new TableView();
      tvOverview.setPrefHeight(DATA_HEIGHT);
      tvOverview.setPrefWidth(WIDTH);
      TableColumn<String, PresentableAspect> firstPoint = new TableColumn<>();
      TableColumn<String, PresentableAspect> aspect = new TableColumn<>();
      TableColumn<String, PresentableAspect> secondPoint = new TableColumn<>();
      TableColumn<String, PresentableAspect> effectiveOrb = new TableColumn<>(rosetta.getText("ui.charts.aspects.col.effectiveorb"));
      TableColumn<String, PresentableAspect> percOrb = new TableColumn<>(rosetta.getText("ui.charts.aspects.col.percorb"));
      firstPoint.setStyle(FONT_STYLE_GLYPH);
      secondPoint.setStyle(FONT_STYLE_GLYPH);
      aspect.setStyle(FONT_STYLE_GLYPH);
      effectiveOrb.setStyle(FONT_STYLE_DATA);
      percOrb.setStyle(FONT_STYLE_DATA);
      tvOverview.getColumns().addAll(firstPoint, aspect, secondPoint, effectiveOrb, percOrb);
      for (IAnalyzedPair pair : aspects) {
         PresentableAspect presAspect = new PresentableAspect(pair);
         firstPoint.setCellValueFactory(new PropertyValueFactory<>("firstItemGlyph"));
         aspect.setCellValueFactory(new PropertyValueFactory<>("aspectGlyph"));
         secondPoint.setCellValueFactory(new PropertyValueFactory<>("secondItemGlyph"));
         effectiveOrb.setCellValueFactory(new PropertyValueFactory<>("effectiveOrb"));
         percOrb.setCellValueFactory(new PropertyValueFactory<>("percOrb"));
         tvOverview.getItems().add(presAspect);
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
      new Help(rosetta.getHelpText("help.chartsaspects.title"), rosetta.getHelpText("help.chartsaspects.content"));
   }

}
