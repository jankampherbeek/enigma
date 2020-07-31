/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens;

import com.radixpro.enigma.shared.Rosetta;
import com.radixpro.enigma.ui.shared.Help;
import com.radixpro.enigma.ui.shared.creators.*;
import com.radixpro.enigma.xchg.api.PersistedChartDataApi;
import com.radixpro.enigma.xchg.domain.ChartData;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

import static com.radixpro.enigma.ui.shared.UiDictionary.STYLESHEET;

public class ChartsSearch {

   private static final double WIDTH = 600.0;
   private static final double FULL_DATA_WIDTH = 588;
   private static final double SMALL_DATA_WIDTH = 500;
   private static final double HEIGHT = 400.0;
   private static final double GAP = 6.0;
   private static final double GP_HEIGHT = 240.0;
   private static final double LV_HEIGHT = 180.0;
   private static final double TITLE_HEIGHT = 45.0;
   private static final double INPUT_HEIGHT = 25.0;
   private final Rosetta rosetta;
   private final Stage stage;
   private Button btnCancel;
   private Button btnHelp;
   private Button btnOk;
   private Button btnSearch;

   private Label lblInstruction;
   private Label lblPageTitle;
   private ListView<String> lvSearchResults;
   private Pane panePageTitle;

   private TextField tfSearchArg;

   private ChartData selectedItem;
   private boolean selectionMade = false;
   private List<ChartData> chartsFound;

   public ChartsSearch() {
      rosetta = Rosetta.getRosetta();
      defineLeafs();
      definePanes();
      defineButtons();
      defineStructure();
      stage = new Stage();
      stage.setWidth(WIDTH);
      stage.setScene(new Scene(createVBox()));
      stage.showAndWait();
   }

   @SuppressWarnings("unchecked")
   private void defineLeafs() {
      lblInstruction = LabelFactory.createLabel(rosetta.getText("ui.charts.search.instruction"), FULL_DATA_WIDTH);
      lblPageTitle = LabelFactory.createLabel(rosetta.getText("ui.charts.search.pagetitle"), "titletext", FULL_DATA_WIDTH);
      lvSearchResults = ListViewFactory.createListView(LV_HEIGHT, SMALL_DATA_WIDTH, "inputDefault");
      tfSearchArg = TextFieldFactory.createTextField(INPUT_HEIGHT, SMALL_DATA_WIDTH, "inputDefault");
   }

   private void definePanes() {
      panePageTitle = PaneFactory.createPane(TITLE_HEIGHT, WIDTH, "titlepane");
   }

   private void defineButtons() {
      btnSearch = ButtonFactory.createButton(rosetta.getText("ui.shared.btn.search"), false);
      btnSearch.setOnAction(click -> onSearch());
      btnCancel = ButtonFactory.createButton(rosetta.getText("ui.shared.btn.cancel"), false);
      btnCancel.setOnAction(click -> onCancel());
      btnHelp = ButtonFactory.createButton(rosetta.getText("ui.shared.btn.help"), false);
      btnHelp.setOnAction(click -> onHelp());
      btnOk = ButtonFactory.createButton(rosetta.getText("ui.shared.btn.ok"), false);
      btnOk.setOnAction(click -> onSelectOk());
   }

   private void defineStructure() {
      panePageTitle.getChildren().add(lblPageTitle);
   }

   private VBox createVBox() {
      VBox vBox = new VBox();
      vBox.getStylesheets().add(STYLESHEET);
      vBox.setPrefWidth(WIDTH);
      vBox.setPrefHeight(HEIGHT);
      vBox.getChildren().add(0, panePageTitle);
      vBox.getChildren().add(1, createGridPane());
      vBox.getChildren().add(2, createBtnBar());
      return vBox;
   }


   private GridPane createGridPane() {
      GridPane gridPane = GridPaneFactory.createGridPane(GP_HEIGHT, WIDTH, GAP);
      gridPane.add(lblInstruction, 0, 0, 2, 1);
      gridPane.add(tfSearchArg, 0, 1, 1, 1);
      gridPane.add(btnSearch, 1, 1, 1, 1);
      gridPane.add(lvSearchResults, 0, 2, 1, 1);
      return gridPane;
   }

   private ButtonBar createBtnBar() {
      ButtonBar btnBar = new ButtonBar();
      btnBar.setPadding(new Insets(GAP, GAP, GAP, GAP));
      btnBar.getButtons().add(btnHelp);
      btnBar.getButtons().add(btnCancel);
      btnBar.getButtons().add(btnOk);
      return btnBar;
   }

   private void onSearch() {
      lvSearchResults.getItems().clear();
      String arg = tfSearchArg.getText();
      chartsFound = new PersistedChartDataApi().search(arg);
      for (ChartData chartData : chartsFound) {
         lvSearchResults.getItems().add(chartData.getChartMetaData().getName());
      }
   }

   private void onHelp() {
      new Help(rosetta.getHelpText("help.chartssearch.title"), rosetta.getHelpText("help.chartssearch.content"));
   }

   void onSelectOk() {
      int index = lvSearchResults.getSelectionModel().getSelectedIndex();
      if (index >= 0) {
         selectedItem = chartsFound.get(index);
         selectionMade = true;
      }
      stage.close();
   }

   void onCancel() {
      lvSearchResults.getItems().clear();
      stage.close();
   }

   public ChartData getSelectedItem() {
      return this.selectedItem;
   }

   public boolean isSelectionMade() {
      return this.selectionMade;
   }
}
