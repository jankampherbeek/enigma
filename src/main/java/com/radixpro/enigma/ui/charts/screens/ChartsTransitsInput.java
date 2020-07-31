/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.charts.screens;

import com.radixpro.enigma.shared.Rosetta;
import com.radixpro.enigma.ui.charts.ChartsSessionState;
import com.radixpro.enigma.ui.shared.InputStatus;
import com.radixpro.enigma.ui.shared.creators.*;
import com.radixpro.enigma.ui.shared.validation.ValidatedDate;
import com.radixpro.enigma.ui.shared.validation.ValidatedLatitude;
import com.radixpro.enigma.ui.shared.validation.ValidatedLongitude;
import com.radixpro.enigma.ui.shared.validation.ValidatedTime;
import com.radixpro.enigma.xchg.domain.TimeZones;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static com.radixpro.enigma.ui.shared.UiDictionary.*;

public class ChartsTransitsInput {
   private static final double HEIGHT = 520.0;
   private final Rosetta rosetta;
   private final Stage stage;
   private final ChartsSessionState state;
   private Button calculatebtn;
   private Label lblLocalTime;
   private Label lblMetaChart;
   private Label lblMetaConfig;
   private Label lblPageTitle;
   private Label lblSubTitleDateAndTime;
   private Label lblSubTitleGeneral;
   private Label lblSubTitleLocation;
   private Pane panePageTitle;
   private Pane paneSubTitleDateAndTime;
   private Pane paneSubTitleGeneral;
   private Pane paneSubTitleLocation;
   private CheckBox cBoxDst;
   private ChoiceBox<String> cbCalendar;
   private ChoiceBox<String> cbLocalEastWest;
   private ChoiceBox<String> cbTimeZone;
   private TextField tfDate;
   private TextField tfLocaltime;
   private TextField tfLocationLatitude;
   private TextField tfLocationLongitude;
   private TextField tfLocationName;
   private TextField tfTime;
   private ChoiceBox<String> cbEastWest;
   private ChoiceBox<String> cbNorthSouth;
   private ValidatedLongitude valLong;
   private ValidatedLongitude valLongLocalTime;
   private ValidatedLatitude valLat;
   private ValidatedDate valDate;
   private ValidatedTime valTime;
   private InputStatus inputStatus = InputStatus.INCOMPLETE;
   private boolean timeZoneLocalSelected = false;

   public ChartsTransitsInput() {
      state = ChartsSessionState.getInstance();    // TODO use factory
      rosetta = Rosetta.getRosetta();
      defineLeafs();
      definePanes();
      defineStructure();
      initialize();

      stage = new Stage();

      stage.setWidth(INPUT_WIDTH);
      stage.setScene(new Scene(createVBox()));
      stage.showAndWait();

   }

   private void defineLeafs() {
      lblLocalTime = LabelFactory.createLabel(rosetta.getText("ui.charts.input.time.localtime"), INPUT_MINOR_DATA_WIDTH);
      lblLocalTime.setDisable(true);
      lblMetaChart = LabelFactory.createLabel(rosetta.getText("ui.charts.meta.chartname") + " " +
            state.getSelectedChart().getChartData().getChartMetaData().getName(), INPUT_WIDTH);
      lblMetaConfig = LabelFactory.createLabel(rosetta.getText("ui.charts.meta.configname") + " " + state.getSelectedConfig().getName(), INPUT_WIDTH);
      lblPageTitle = LabelFactory.createLabel(rosetta.getText("ui.charts.transitsinput.pagetitle"), "titletext", INPUT_WIDTH);
      lblSubTitleGeneral = LabelFactory.createLabel(rosetta.getText("ui.charts.input.subtitle.general"), "subtitletext", INPUT_WIDTH);
      lblSubTitleLocation = LabelFactory.createLabel(rosetta.getText("ui.charts.input.subtitle.location"), "subtitletext", INPUT_WIDTH);
      lblSubTitleDateAndTime = LabelFactory.createLabel(rosetta.getText("ui.charts.input.subtitle.dateandtime"), "subtitletext", INPUT_WIDTH);

      cBoxDst = CheckBoxFactory.createCheckBox(Pos.CENTER_RIGHT, "inputDefault");
      cbCalendar = ChoiceBoxFactory.createChoiceBox(INPUT_HEIGHT, INPUT_MICRO_DATA_WIDTH, "inputDefault");
      cbLocalEastWest = ChoiceBoxFactory.createChoiceBox(INPUT_HEIGHT, INPUT_MICRO_DATA_WIDTH, "inputDefault");
      cbLocalEastWest.setDisable(true);
      cbLocalEastWest.setFocusTraversable(false);
      cbTimeZone = ChoiceBoxFactory.createChoiceBox(INPUT_HEIGHT, INPUT_DATA_WIDTH, "inputDefault");
      tfDate = TextFieldFactory.createTextField(INPUT_HEIGHT, INPUT_MINOR_DATA_WIDTH, "inputDefault");
      tfLocaltime = TextFieldFactory.createTextField(INPUT_HEIGHT, INPUT_MINOR_DATA_WIDTH, "inputDefault");
      tfLocaltime.setEditable(false);
      tfLocaltime.setDisable(true);
      tfLocationLatitude = TextFieldFactory.createTextField(INPUT_HEIGHT, INPUT_MINOR_DATA_WIDTH, "inputDefault");
      tfLocationLongitude = TextFieldFactory.createTextField(INPUT_HEIGHT, INPUT_MINOR_DATA_WIDTH, "inputDefault");
      tfLocationName = TextFieldFactory.createTextField(INPUT_HEIGHT, INPUT_DATA_WIDTH, "inputDefault");
      tfTime = TextFieldFactory.createTextField(INPUT_HEIGHT, INPUT_MINOR_DATA_WIDTH, "inputDefault");
      cbEastWest = ChoiceBoxFactory.createChoiceBox(INPUT_HEIGHT, INPUT_MICRO_DATA_WIDTH, "inputDefault");
      cbNorthSouth = ChoiceBoxFactory.createChoiceBox(INPUT_HEIGHT, INPUT_MICRO_DATA_WIDTH, "inputDefault");
   }

   private void definePanes() {
      panePageTitle = PaneFactory.createPane(TITLE_HEIGHT, INPUT_WIDTH, "titlepane");
      paneSubTitleGeneral = PaneFactory.createPane(SUBTITLE_HEIGHT, INPUT_WIDTH, "subtitlepane");
      paneSubTitleLocation = PaneFactory.createPane(SUBTITLE_HEIGHT, INPUT_WIDTH, "subtitlepane");
      paneSubTitleDateAndTime = PaneFactory.createPane(SUBTITLE_HEIGHT, INPUT_WIDTH, "subtitlepane");
   }

   private void defineStructure() {
      panePageTitle.getChildren().add(lblPageTitle);
      paneSubTitleGeneral.getChildren().add(lblSubTitleGeneral);
      paneSubTitleLocation.getChildren().add(lblSubTitleLocation);
      paneSubTitleDateAndTime.getChildren().add(lblSubTitleDateAndTime);
   }


   public void initialize() {
      defineListeners();
   }

   private void defineListeners() {
      tfLocationLongitude.textProperty().addListener((observable, oldValue, newValue) -> validateLongitude(newValue));
      tfLocationLatitude.textProperty().addListener((observable, oldValue, newValue) -> validateLatitude(newValue));
      tfDate.textProperty().addListener((observable, oldValue, newValue) -> validateDate(newValue));
      tfTime.textProperty().addListener((observable, oldValue, newValue) -> validateTime(newValue));
      cbTimeZone.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
            checkTimeZones(newValue));
      tfLocaltime.textProperty().addListener((observable, oldValue, newValue) -> validateLocalTime(newValue));
      cbCalendar.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
            checkCalendar(newValue));
   }

   private VBox createVBox() {
      VBox vBox = new VBox();
      vBox.getStylesheets().add(STYLESHEET);
      vBox.setPrefWidth(INPUT_WIDTH);
      vBox.setPrefHeight(HEIGHT);
      vBox.getChildren().addAll(panePageTitle, paneSubTitleGeneral, createPaneMetaInfo(), paneSubTitleLocation, createGridPaneLocation(),
            paneSubTitleDateAndTime, createGridPaneDateAndTime(), createPaneBtnBar());
      return vBox;
   }

   private GridPane createGridPaneLocation() {
      // TODO only show location if config indicates topocentric calculations, otherwise replace with short text
      return new LocationGridPaneCreator().createGridPane(tfLocationName, tfLocationLongitude, tfLocationLatitude, cbEastWest, cbNorthSouth);
   }

   private GridPane createGridPaneDateAndTime() {
      return new DateTimePaneCreator().createGridPane(lblLocalTime, tfDate, tfTime, tfLocaltime, cbCalendar, cbTimeZone, cbLocalEastWest, cBoxDst);
   }

   private VBox createPaneMetaInfo() {    // TODO make external class
      VBox vBox = new VBox();
      vBox.getStylesheets().add(STYLESHEET);
      vBox.setPrefWidth(INPUT_WIDTH);
      vBox.setPrefHeight(50.0);   // todo make constant
      vBox.getChildren().addAll(lblMetaChart, lblMetaConfig);
      return vBox;
   }

   private Pane createPaneBtnBar() {
      Pane pane = new Pane();
      pane.setPrefHeight(BUTTONBAR_HEIGHT);
      pane.setPrefWidth(INPUT_WIDTH);
      pane.getChildren().add(createBtnBar());
      return pane;
   }

   private ButtonBar createBtnBar() {
      ButtonBar buttonBar = new ButtonBar();
      calculatebtn = new Button(rosetta.getText("ui.shared.btn.calculate"));
      Button helpBtn = new Button(rosetta.getText("ui.shared.btn.help"));
      Button cancelBtn = new Button(rosetta.getText("ui.shared.btn.cancel"));
      calculatebtn.setOnAction(click -> onCalculate());
      helpBtn.setOnAction(click -> onHelp());
      cancelBtn.setOnAction(click -> onCancel());
      buttonBar.getButtons().addAll(helpBtn, cancelBtn, calculatebtn);
      return buttonBar;
   }

   private void onCalculate() {
      // perform calculation
      stage.close();
   }

   private void onCancel() {
      stage.close();
   }

   private void onHelp() {

//      new Help(rosetta.getHelpText("help.chartsinput.title"), rosetta.getHelpText("help.chartsinput.content"));
   }

   private void validateLongitude(final String newLongitude) {
      valLong = new ValidatedLongitude(newLongitude);
      tfLocationLongitude.setStyle(valLong.isValidated() ? INPUT_DEFAULT_STYLE : INPUT_ERROR_STYLE);
      checkStatus();
   }

   private void validateLocalTime(final String newLocalTime) {
      valLongLocalTime = new ValidatedLongitude(newLocalTime);
      tfLocaltime.setStyle(valLongLocalTime.isValidated() ? INPUT_DEFAULT_STYLE : INPUT_ERROR_STYLE);
      checkStatus();
   }

   private void validateLatitude(final String newLatitude) {
      valLat = new ValidatedLatitude(newLatitude);
      tfLocationLatitude.setStyle(valLat.isValidated() ? INPUT_DEFAULT_STYLE : INPUT_ERROR_STYLE);
      checkStatus();
   }

   private void validateDate(final String newDate) {
      valDate = new ValidatedDate(newDate + '/' + cbCalendar.getValue());
      tfDate.setStyle(valDate.isValidated() ? INPUT_DEFAULT_STYLE : INPUT_ERROR_STYLE);
      checkStatus();
   }

   private void validateTime(final String newTime) {
      valTime = new ValidatedTime(newTime);
      tfTime.setStyle(valTime.isValidated() ? INPUT_DEFAULT_STYLE : INPUT_ERROR_STYLE);
      checkStatus();
   }

   private void checkTimeZones(final String newValue) {
      TimeZones selected = TimeZones.UT.timeZoneForName(newValue);
      if (selected == TimeZones.LMT) {
         tfLocaltime.setEditable(true);
         tfLocaltime.setDisable(false);
         tfLocaltime.setFocusTraversable(true);
         lblLocalTime.setDisable(false);
         cbLocalEastWest.setDisable(false);
         cbLocalEastWest.setFocusTraversable(true);
         timeZoneLocalSelected = true;
      } else {
         tfLocaltime.setEditable(false);
         tfLocaltime.setDisable(true);
         tfLocaltime.setFocusTraversable(false);
         lblLocalTime.setDisable(true);
         cbLocalEastWest.setDisable(true);
         cbLocalEastWest.setFocusTraversable(false);
         timeZoneLocalSelected = false;
      }
      checkStatus();
   }

   private void checkCalendar(final String newValue) {
      valDate = new ValidatedDate(tfDate.getText() + '/' + newValue);
      tfDate.setStyle(valDate.isValidated() ? INPUT_DEFAULT_STYLE : INPUT_ERROR_STYLE);
      checkStatus();
   }

   private void checkStatus() {
      boolean inputOk = (valLat != null && valLat.isValidated()
            && valLong != null && valLong.isValidated()
            && valDate != null && valDate.isValidated()
            && valTime != null && valTime.isValidated()
            && ((valLongLocalTime != null && valLongLocalTime.isValidated()) || !timeZoneLocalSelected));
      calculatebtn.setDisable(!inputOk);
      calculatebtn.setFocusTraversable(inputOk);
      if (inputOk) inputStatus = InputStatus.READY;
   }

}
