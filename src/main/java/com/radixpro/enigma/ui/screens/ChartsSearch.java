/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.ui.creators.*;
import com.radixpro.enigma.ui.shared.Help;
import com.radixpro.enigma.xchg.api.PersistedChartDataApi;
import com.radixpro.enigma.xchg.domain.FullChartInputData;
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
   private final PersistedChartDataApi persistedChartDataApi;
   private Stage stage;
   private Button btnCancel;
   private Button btnHelp;
   private Button btnOk;
   private Button btnSearch;

   private Label lblInstruction;
   private Label lblPageTitle;
   private ListView<String> lvSearchResults;
   private Pane panePageTitle;

   private TextField tfSearchArg;

   private FullChartInputData selectedItem;
   private boolean selectionMade = false;
   private List<FullChartInputData> chartsFound;

   public ChartsSearch(PersistedChartDataApi persistedChartDataApi) {
      this.rosetta = Rosetta.getRosetta();
      this.persistedChartDataApi = persistedChartDataApi;
   }

   public void show() {
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
      lblInstruction = new LabelBuilder("ui.charts.search.instruction").setPrefWidth(FULL_DATA_WIDTH).build();
      lblPageTitle = new LabelBuilder("ui.charts.search.pagetitle").setStyleClass("titletext").setPrefWidth(FULL_DATA_WIDTH).build();
      lvSearchResults = new ListViewBuilder().setHeight(LV_HEIGHT).setWidth(SMALL_DATA_WIDTH).setStyleClass("inputDefault").build();
      tfSearchArg = new TextFieldBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(SMALL_DATA_WIDTH).setStyleClass("inputDefault").build();
   }

   private void definePanes() {
      panePageTitle = new PaneBuilder().setHeight(TITLE_HEIGHT).setWidth(WIDTH).setStyleClass("titlepane").build();
   }

   private void defineButtons() {
      btnSearch = new ButtonBuilder("ui.shared.btn.search").setDisabled(false).build();
      btnSearch.setOnAction(click -> onSearch());
      btnCancel = new ButtonBuilder("ui.shared.btn.cancel").setDisabled(false).build();
      btnCancel.setOnAction(click -> onCancel());
      btnHelp = new ButtonBuilder("ui.shared.btn.help").setDisabled(false).build();
      btnHelp.setOnAction(click -> onHelp());
      btnOk = new ButtonBuilder("ui.shared.btn.ok").setDisabled(false).build();
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
      GridPane gridPane = new GridPaneBuilder().setPrefHeight(GP_HEIGHT).setPrefWidth(WIDTH).setHGap(GAP).setVGap(GAP).build();
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
      chartsFound = persistedChartDataApi.search(arg);
      for (FullChartInputData fullChartInputData : chartsFound) {
         lvSearchResults.getItems().add(fullChartInputData.getChartMetaData().getName());
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

   public FullChartInputData getSelectedItem() {
      return this.selectedItem;
   }

   public boolean isSelectionMade() {
      return this.selectionMade;
   }
}
