/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.ui;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.statistics.ui.domain.ScenarioFe;
import com.radixpro.enigma.ui.creators.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import static com.radixpro.enigma.ui.shared.UiDictionary.*;

public class ProcessingResult {

   private static final double HEIGHT = 800.0;
   private static final double WIDTH = 900.0;
   private static final double SHORTWIDTH = 120.0;
   private final StatsFacade facade;
   private ScenarioFe scenario;
   private Stage stage;
   private Pane paneTitle;
   private Pane paneSubTitle;
   private Pane paneResults;
   private Pane paneBtnBar;
   private Pane paneFiles;


   public ProcessingResult(@NotNull final StatsFacade facade) {
      this.facade = facade;
   }

   public void show(@NotNull String scenarioName, @NotNull String projName) {
      this.scenario = facade.readScenario(scenarioName, "RANGE", projName);   // TODO handle ScenarioType
      stage = new Stage();
      initialize(scenario);
      stage.setTitle(Rosetta.getText("ui.stats.processingresult.title"));
      stage.setScene(new Scene(createVBox()));
      stage.showAndWait();
   }

   private void initialize(ScenarioFe scenario) {
      Label lblTitle = new LabelBuilder("ui.stats.processingresult.title").setPrefWidth(WIDTH).setStyleClass("titletext").build();
      paneTitle = new PaneBuilder().setWidth(WIDTH).setHeight(TITLE_HEIGHT).setWidth(WIDTH).setStyleClass("titlepane").setChildren(lblTitle).build();
      Label lblSubTitle = new LabelBuilder("").setText(scenario.getName()).setPrefWidth(WIDTH).setStyleClass("subtitletext").build();
      paneSubTitle = new PaneBuilder().setWidth(WIDTH).setHeight(SUBTITLE_HEIGHT).setStyleClass("subtitlepane").setChildren(lblSubTitle).build();
      TextArea taResults = new TextArea();
      taResults.setFont(Font.font("monospace", 9));
      taResults.setPrefRowCount(36);
      taResults.setPrefColumnCount(120);
      taResults.setWrapText(false);
      taResults.setText(defineResults(scenario));
      paneResults = new PaneBuilder().setWidth(WIDTH).setHeight(400).setWidth(WIDTH).setChildren(taResults).build();
      Label lblJsonFile = new LabelBuilder("ui.stats.processingresult.jsonlocation").setPrefWidth(SHORTWIDTH).build();
      Label lblJsonFileValue = new LabelBuilder("").setText("Temp TODO Json").setPrefWidth(SHORTWIDTH).build();
      Label lblCsvFile = new LabelBuilder("ui.stats.processingresult.csvlocation").setPrefWidth(SHORTWIDTH).build();
      Label lblCsvFileValue = new LabelBuilder("").setText("Temp TODO Csv").setPrefWidth(SHORTWIDTH).build();
      GridPane grid = new GridPaneBuilder().setHGap(GAP).setPadding(GAP).setPrefWidth(2 * SHORTWIDTH).build();
      grid.add(lblJsonFile, 0, 0, 1, 1);
      grid.add(lblJsonFileValue, 1, 0, 1, 1);
      grid.add(lblCsvFile, 0, 1, 1, 1);
      grid.add(lblCsvFileValue, 1, 1, 1, 1);
      paneFiles = new PaneBuilder().setWidth(2 * SHORTWIDTH).setHeight(40).setChildren(grid).build();
      Button btnHelp = new ButtonBuilder("ui.shared.btn.help").setDisabled(false).setFocusTraversable(true).build();
      btnHelp.setOnAction(e -> onHelp());
      Button btnClose = new ButtonBuilder("ui.shared.btn.exit").setDisabled(false).setFocusTraversable(true).build();
      btnClose.setOnAction(e -> stage.close());
      ButtonBar buttonBar = new ButtonBarBuilder().setButtons(btnHelp, btnClose).build();
      paneBtnBar = new PaneBuilder().setWidth(WIDTH).setHeight(30.0).setChildren(buttonBar).build();
   }

   private String defineResults(@NotNull ScenarioFe scenario) {
      return facade.processScenRange(scenario);
   }

   private VBox createVBox() {
      return new VBoxBuilder().setHeight(HEIGHT).setWidth(WIDTH).setPadding(GAP).setChildren(
            paneTitle,
            paneSubTitle,
            paneResults,
            paneFiles,
            paneBtnBar
      ).build();
   }


   private void onHelp() {

   }

}
