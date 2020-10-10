/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.ui;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.statistics.api.InputDataFileRequest;
import com.radixpro.enigma.statistics.api.InputDataFileResponse;
import com.radixpro.enigma.statistics.core.DataInputFormats;
import com.radixpro.enigma.ui.creators.*;
import com.radixpro.enigma.ui.shared.Help;
import com.radixpro.enigma.xchg.api.InputDataFileApi;
import com.radixpro.enigma.xchg.api.PersistedPropertyApi;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.File;

import static com.radixpro.enigma.ui.shared.UiDictionary.*;

/**
 * Screen for adding data files to Enigma.
 */
public class StatsDataNew {

   private static final String KEY_PROJDIR = "projdir";
   private static final double HEIGHT = 360.0;
   private final InputDataFileApi inputDataFileApi;
   private final PersistedPropertyApi propApi;
   private Stage stage;
   private Button btnDataFile;
   private Label lblPageTitle;
   private Label lblExplanation;
   private Label lblName;
   private Label lblDescription;
   private Label lblFormat;
   private Label lblResults;
   private Pane panePageTitle;
   private Pane paneExplanation;
   private ChoiceBox<String> cbInputFormats;
   private TextField tfName;
   private TextField tfDescr;
   private DataInputFormats selectedInputFormat;


   public StatsDataNew(@NotNull final InputDataFileApi inputDataFileApi,
                       @NotNull final PersistedPropertyApi propApi) {
      this.inputDataFileApi = inputDataFileApi;
      this.propApi = propApi;
   }

   public void show() {
      stage = new Stage();
      stage.setTitle(Rosetta.getText("ui.stats.inputdata.title"));
      defineLeafs();
      definePanes();
      initInputFormats();
      stage.setScene(new Scene(createInputGridPane()));
      stage.showAndWait();
   }

   private void defineLeafs() {
      lblPageTitle = new LabelBuilder("ui.stats.inputdata.pagetitle").setPrefWidth(INPUT_WIDTH).setStyleClass("titletext").build();
      lblExplanation = new LabelBuilder("ui.stats.inputdata.lblexplanation").setPrefWidth(INPUT_WIDTH).build();
      lblName = new LabelBuilder("ui.stats.inputdata.lblname").build();
      lblDescription = new LabelBuilder("ui.stats.inputdata.lbldescription").build();
      lblFormat = new LabelBuilder("ui.stats.inputdata.lblformat").build();
      lblResults = new LabelBuilder("ui.stats.inputdata.lblresults").build();
      cbInputFormats = new ChoiceBoxBuilder().setPrefWidth(INPUT_DATA_WIDTH).setStyleClass(INPUT_DEFAULT_STYLE).build();
      cbInputFormats.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> defineInputFormat(newValue));
      selectedInputFormat = DataInputFormats.CSV_CHARTS_STANDARD;
      tfName = new TextFieldBuilder().setPrefWidth(INPUT_DATA_WIDTH).setPrefHeight(INPUT_HEIGHT).setStyleClass("inputDefault").build();
      tfName.textProperty().addListener((observable, oldValue, newValue) -> onChange());
      tfDescr = new TextFieldBuilder().setPrefWidth(INPUT_DATA_WIDTH).setPrefHeight(INPUT_HEIGHT).setStyleClass("inputDefault").build();
      tfDescr.textProperty().addListener((observable, oldValue, newValue) -> onChange());
      btnDataFile = new ButtonBuilder("ui.stats.inputdata.btn.datafile").setDisabled(false).build();
      btnDataFile.setOnAction(click -> onDataFile());
      tfName.textProperty().addListener((observable, oldValue, newValue) -> checkStatus());
      tfDescr.textProperty().addListener((observable, oldValue, newValue) -> checkStatus());
      cbInputFormats.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> checkStatus());

   }

   private void definePanes() {
      panePageTitle = new PaneBuilder().setWidth(INPUT_WIDTH).setHeight(TITLE_HEIGHT).setStyleClass("titlepane").setChildren(lblPageTitle).build();
      paneExplanation = new PaneBuilder().setWidth(INPUT_WIDTH).setHeight(50.0).setChildren(lblExplanation).build();
   }

   private GridPane createInputGridPane() {
      GridPane gridPane = new GridPaneBuilder().setVGap(GAP).setHGap(GAP).setPrefWidth(INPUT_DATA_WIDTH).setPrefHeight(HEIGHT).build();
      gridPane.add(panePageTitle, 0, 0, 2, 1);
      gridPane.add(paneExplanation, 0, 1, 2, 1);
      gridPane.add(lblName, 0, 2, 2, 1);
      gridPane.add(tfName, 0, 3, 2, 1);
      gridPane.add(lblDescription, 0, 4, 2, 1);
      gridPane.add(tfDescr, 0, 5, 2, 1);
      gridPane.add(lblFormat, 0, 6, 2, 1);
      gridPane.add(cbInputFormats, 0, 7, 2, 1);
      gridPane.add(createBtnBar(), 0, 8, 2, 1);
      return gridPane;
   }

   protected ButtonBar createBtnBar() {
      Button btnHelp = new ButtonBuilder("ui.shared.btn.help").setDisabled(false).build();
      btnHelp.setOnAction(click -> onHelp());
      Button btnExit = new ButtonBuilder("ui.shared.btn.exit").setDisabled(false).build();
      btnExit.setOnAction((click -> stage.close()));
      return new ButtonBarBuilder().setButtons(btnHelp, btnExit, btnDataFile).build();
   }

   private void initInputFormats() {
      final ObservableList<String> observableList = DataInputFormats.getObservableList();
      cbInputFormats.setItems(observableList);
      cbInputFormats.getSelectionModel().select(0);
   }

   private void defineInputFormat(final String newValue) {
      selectedInputFormat = DataInputFormats.formatForName(newValue);
   }

   private void onHelp() {
      new Help(Rosetta.getHelpText("help.statsdatanew.title"), Rosetta.getHelpText("help.statsdatanew.content"));
   }

   private void onDataFile() {
      File dataFile = new FileChooser().showOpenDialog(stage);
      if (null != dataFile) {
         processDataFile(dataFile);
         stage.close();
      }
   }

   private void onChange() {
      checkStatus();
   }

   public void checkStatus() {
      boolean statusOk = DataInputFormats.UNDEFINED != selectedInputFormat && null != tfName.getText() && !tfName.getText().isBlank() && null != tfDescr
            && !tfDescr.getText().isBlank();
      btnDataFile.setDisable(!statusOk);
   }

   private void processDataFile(final File dataFile) {
      String fullPathProjdir = propApi.read(KEY_PROJDIR).get(0).getValue();
      InputDataFileRequest request = new InputDataFileRequest(tfName.getText(), tfDescr.getText(), dataFile, fullPathProjdir);
      InputDataFileResponse response = inputDataFileApi.addDataFile(request);
      lblResults.setText(response.getResultMsg());
   }

}
