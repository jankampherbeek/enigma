/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens;

import com.radixpro.enigma.shared.Rosetta;
import com.radixpro.enigma.ui.shared.Help;
import com.radixpro.enigma.ui.shared.InputStatus;
import com.radixpro.enigma.ui.shared.factories.*;
import com.radixpro.enigma.ui.shared.validation.*;
import com.radixpro.enigma.xchg.api.PersistedChartDataApi;
import com.radixpro.enigma.xchg.domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static com.radixpro.enigma.ui.shared.StyleDictionary.*;

public class ChartsInput {

   private static final double HEIGHT = 820.0;
   private static final double WIDTH = 600.0;
   private static final double GAP = 6.0;
   private static final double FULL_DATA_WIDTH = 588.0;
   private static final double HALF_DATA_WIDTH = 288.0;
   private static final double MINOR_DATA_WIDTH = 236.0;
   private static final double MICRO_DATA_WIDTH = 40;
   private static final double TITLE_HEIGHT = 45.0;
   private static final double SUBTITLE_HEIGHT = 30.0;
   private static final double GP_GENERAL_HEIGHT = 240.0;
   private static final double GP_LOCATION_HEIGHT = 130.0;
   private static final double GP_DATETIME_HEIGHT = 200.0;
   private static final double BUTTONBAR_HEIGHT = 50.0;
   private static final double INPUT_HEIGHT = 25.0;
   private final Rosetta rosetta;
   private final Stage stage;
   private Label lblCalendar;
   private Label lblDate;
   private Label lblDescription;
   private Label lblDst;
   private Label lblLocalTime;
   private Label lblLocationLongitude;
   private Label lblLocationLatitude;
   private Label lblLocationName;
   private Label lblName;
   private Label lblPageTitle;
   private Label lblRating;
   private Label lblSource;
   private Label lblSubject;
   private Label lblSubTitleDateAndTime;
   private Label lblSubTitleGeneral;
   private Label lblSubTitleLocation;
   private Label lblTime;
   private Label lblTimeZone;
   private Pane panePageTitle;
   private Pane paneSubTitleDateAndTime;
   private Pane paneSubTitleGeneral;
   private Pane paneSubTitleLocation;
   private CheckBox cBoxDst;
   private ChoiceBox<String> cbCalendar;
   private ChoiceBox<String> cbEastWest;
   private ChoiceBox<String> cbLocalEastWest;
   private ChoiceBox<String> cbNorthSouth;
   private ChoiceBox<String> cbRating;
   private ChoiceBox<String> cbSubject;
   private ChoiceBox<String> cbTimeZone;
   private TextField tfDate;
   private TextField tfDescription;
   private TextField tfLocaltime;
   private TextField tfLocationLatitude;
   private TextField tfLocationLongitude;
   private TextField tfLocationName;
   private TextField tfName;
   private TextField tfSource;
   private TextField tfTime;
   private Button calculatebtn;
   private boolean timeZoneLocalSelected = false;
   private int newChartId;
   private ValidatedLongitude valLong;
   private ValidatedLongitude valLongLocalTime;
   private ValidatedLatitude valLat;
   private ValidatedChartName valChartName;
   private ValidatedDate valDate;
   private ValidatedTime valTime;
   private InputStatus inputStatus = InputStatus.INCOMPLETE;

   public ChartsInput() {
      rosetta = Rosetta.getRosetta();
      defineLeafs();
      definePanes();
      defineStructure();
      initialize();
      stage = new Stage();
      stage.setWidth(WIDTH);
      stage.setScene(new Scene(createVBox()));
      stage.showAndWait();
   }

   @SuppressWarnings("unchecked")
   private void defineLeafs() {
      lblCalendar = LabelFactory.createLabel(rosetta.getText("ui.charts.input.date.calendar"), MICRO_DATA_WIDTH);
      lblDate = LabelFactory.createLabel(rosetta.getText("ui.charts.input.date.name"), MINOR_DATA_WIDTH);
      lblDescription = LabelFactory.createLabel(rosetta.getText("ui.charts.input.description"), FULL_DATA_WIDTH);
      lblDst = LabelFactory.createLabel(rosetta.getText("ui.shared.dst"), MICRO_DATA_WIDTH);
      lblLocalTime = LabelFactory.createLabel(rosetta.getText("ui.charts.input.time.localtime"), MINOR_DATA_WIDTH);
      lblLocalTime.setDisable(true);
      lblLocationName = LabelFactory.createLabel(rosetta.getText("ui.charts.input.location.name"), FULL_DATA_WIDTH);
      lblLocationLongitude = LabelFactory.createLabel(rosetta.getText("ui.charts.input.location.longitude"), HALF_DATA_WIDTH);
      lblLocationLatitude = LabelFactory.createLabel(rosetta.getText("ui.charts.input.location.latitude"), HALF_DATA_WIDTH);
      lblName = LabelFactory.createLabel(rosetta.getText("ui.charts.input.name"), FULL_DATA_WIDTH);
      lblPageTitle = LabelFactory.createLabel(rosetta.getText("ui.charts.input.pagetitle"), "titletext", WIDTH);
      lblRating = LabelFactory.createLabel(rosetta.getText("ui.charts.input.rating"), HALF_DATA_WIDTH);
      lblSource = LabelFactory.createLabel(rosetta.getText("ui.charts.input.source"), FULL_DATA_WIDTH);
      lblSubject = LabelFactory.createLabel(rosetta.getText("ui.charts.input.subject"), HALF_DATA_WIDTH);
      lblSubTitleGeneral = LabelFactory.createLabel(rosetta.getText("ui.charts.input.subtitle.general"), "subtitletext", WIDTH);
      lblSubTitleLocation = LabelFactory.createLabel(rosetta.getText("ui.charts.input.subtitle.location"), "subtitletext", WIDTH);
      lblSubTitleDateAndTime = LabelFactory.createLabel(rosetta.getText("ui.charts.input.subtitle.dateandtime"), "subtitletext", WIDTH);
      lblTime = LabelFactory.createLabel(rosetta.getText("ui.charts.input.time.name"), MINOR_DATA_WIDTH);
      lblTimeZone = LabelFactory.createLabel(rosetta.getText("ui.charts.input.time.timezone"), FULL_DATA_WIDTH);

      cBoxDst = CheckBoxFactory.createCheckBox(Pos.CENTER_RIGHT, "inputDefault");

      cbCalendar = ChoiceBoxFactory.createChoiceBox(INPUT_HEIGHT, MICRO_DATA_WIDTH, "inputDefault");
      cbEastWest = ChoiceBoxFactory.createChoiceBox(INPUT_HEIGHT, MICRO_DATA_WIDTH, "inputDefault");
      cbLocalEastWest = ChoiceBoxFactory.createChoiceBox(INPUT_HEIGHT, MICRO_DATA_WIDTH, "inputDefault");
      cbLocalEastWest.setDisable(true);
      cbLocalEastWest.setFocusTraversable(false);
      cbNorthSouth = ChoiceBoxFactory.createChoiceBox(INPUT_HEIGHT, MICRO_DATA_WIDTH, "inputDefault");
      cbRating = ChoiceBoxFactory.createChoiceBox(INPUT_HEIGHT, HALF_DATA_WIDTH, "inputDefault");
      cbSubject = ChoiceBoxFactory.createChoiceBox(INPUT_HEIGHT, HALF_DATA_WIDTH, "inputDefault");
      cbTimeZone = ChoiceBoxFactory.createChoiceBox(INPUT_HEIGHT, FULL_DATA_WIDTH, "inputDefault");

      tfDate = TextFieldFactory.createTextField(INPUT_HEIGHT, MINOR_DATA_WIDTH, "inputDefault");
      tfDescription = TextFieldFactory.createTextField(INPUT_HEIGHT, FULL_DATA_WIDTH, "inputDefault");
      tfLocaltime = TextFieldFactory.createTextField(INPUT_HEIGHT, MINOR_DATA_WIDTH, "inputDefault");
      tfLocaltime.setEditable(false);
      tfLocaltime.setDisable(true);
      tfLocationLatitude = TextFieldFactory.createTextField(INPUT_HEIGHT, MINOR_DATA_WIDTH, "inputDefault");
      tfLocationLongitude = TextFieldFactory.createTextField(INPUT_HEIGHT, MINOR_DATA_WIDTH, "inputDefault");
      tfLocationName = TextFieldFactory.createTextField(INPUT_HEIGHT, FULL_DATA_WIDTH, "inputDefault");
      tfName = TextFieldFactory.createTextField(INPUT_HEIGHT, FULL_DATA_WIDTH, "inputDefault");
      tfSource = TextFieldFactory.createTextField(INPUT_HEIGHT, FULL_DATA_WIDTH, "inputDefault");
      tfTime = TextFieldFactory.createTextField(INPUT_HEIGHT, MINOR_DATA_WIDTH, "inputDefault");
   }

   private void definePanes() {
      panePageTitle = PaneFactory.createPane(TITLE_HEIGHT, WIDTH, "titlepane");
      paneSubTitleGeneral = PaneFactory.createPane(SUBTITLE_HEIGHT, WIDTH, "subtitlepane");
      paneSubTitleLocation = PaneFactory.createPane(SUBTITLE_HEIGHT, WIDTH, "subtitlepane");
      paneSubTitleDateAndTime = PaneFactory.createPane(SUBTITLE_HEIGHT, WIDTH, "subtitlepane");
   }

   private void defineStructure() {
      panePageTitle.getChildren().add(lblPageTitle);
      paneSubTitleGeneral.getChildren().add(lblSubTitleGeneral);
      paneSubTitleLocation.getChildren().add(lblSubTitleLocation);
      paneSubTitleDateAndTime.getChildren().add(lblSubTitleDateAndTime);
   }


   private VBox createVBox() {
      VBox vBox = new VBox();
      vBox.getStylesheets().add(STYLESHEET);
      vBox.setPrefWidth(WIDTH);
      vBox.setPrefHeight(HEIGHT);
      vBox.getChildren().add(0, panePageTitle);
      vBox.getChildren().add(1, paneSubTitleGeneral);
      vBox.getChildren().add(2, createGridPaneGeneral());
      vBox.getChildren().add(3, paneSubTitleLocation);
      vBox.getChildren().add(4, createGridPaneLocation());
      vBox.getChildren().add(5, paneSubTitleDateAndTime);
      vBox.getChildren().add(6, createGridPaneDateAndTime());
      vBox.getChildren().add(7, createPaneBtnBar());
      return vBox;
   }

   private GridPane createGridPaneGeneral() {
      GridPane gridPane = GridPaneFactory.createGridPane(GP_GENERAL_HEIGHT, WIDTH, GAP);
      gridPane.add(lblName, 0, 0, 2, 1);
      gridPane.add(tfName, 0, 1, 2, 1);
      gridPane.add(lblSubject, 0, 2, 1, 1);
      gridPane.add(lblRating, 1, 2, 1, 1);
      gridPane.add(cbSubject, 0, 3, 1, 1);
      gridPane.add(cbRating, 1, 3, 1, 1);
      gridPane.add(lblSource, 0, 4, 2, 1);
      gridPane.add(tfSource, 0, 5, 2, 1);
      gridPane.add(lblDescription, 0, 6, 2, 1);
      gridPane.add(tfDescription, 0, 7, 2, 1);
      return gridPane;
   }


   private GridPane createGridPaneLocation() {
      GridPane gridPane = GridPaneFactory.createGridPane(GP_LOCATION_HEIGHT, WIDTH, GAP);
      gridPane.add(lblLocationName, 0, 0, 4, 1);
      gridPane.add(tfLocationName, 0, 1, 4, 1);
      gridPane.add(lblLocationLongitude, 0, 2, 2, 1);
      gridPane.add(lblLocationLatitude, 2, 2, 2, 1);
      gridPane.add(tfLocationLongitude, 0, 3, 1, 1);
      gridPane.add(cbEastWest, 1, 3, 1, 1);
      gridPane.add(tfLocationLatitude, 2, 3, 1, 1);
      gridPane.add(cbNorthSouth, 3, 3, 1, 1);
      return gridPane;
   }

   private GridPane createGridPaneDateAndTime() {
      GridPane gridPane = GridPaneFactory.createGridPane(GP_DATETIME_HEIGHT, WIDTH, GAP);
      gridPane.add(lblDate, 0, 0, 1, 1);
      gridPane.add(lblCalendar, 1, 0, 1, 1);
      gridPane.add(lblTime, 2, 0, 1, 1);
      gridPane.add(lblDst, 3, 0, 1, 1);
      gridPane.add(tfDate, 0, 1, 1, 1);
      gridPane.add(cbCalendar, 1, 1, 1, 1);
      gridPane.add(tfTime, 2, 1, 1, 1);
      gridPane.add(cBoxDst, 3, 1, 1, 1);
      gridPane.add(lblTimeZone, 0, 2, 4, 1);
      gridPane.add(cbTimeZone, 0, 3, 4, 1);
      gridPane.add(lblLocalTime, 0, 4, 2, 1);
      gridPane.add(tfLocaltime, 0, 5, 1, 1);
      gridPane.add(cbLocalEastWest, 1, 5, 1, 1);
      return gridPane;
   }

   private Pane createPaneBtnBar() {
      Pane pane = new Pane();
      pane.setPrefHeight(BUTTONBAR_HEIGHT);
      pane.setPrefWidth(WIDTH);
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

      buttonBar.getButtons().add(helpBtn);
      buttonBar.getButtons().add(cancelBtn);
      buttonBar.getButtons().add(calculatebtn);

      return buttonBar;
   }


   private void onCalculate() {
      newChartId = saveData();
      stage.close();
   }

   private void onCancel() {
      inputStatus = InputStatus.CANCELLED;
      stage.close();
   }

   private void onHelp() {
      new Help(rosetta.getHelpText("help.chartsinput.title"), rosetta.getHelpText("help.chartsinput.content"));
   }

   public InputStatus getInputStatus() {
      return inputStatus;
   }

   public int getNewChartId() {
      return newChartId;
   }

   public void initialize() {
      initSubject();
      initRating();
      initLatitude();
      initLongitude();
      initCalendar();
      initTimeZone();
      initLocalEastWest();
      defineListeners();
   }

   private void defineListeners() {
      tfName.textProperty().addListener((observable, oldValue, newValue) -> validateName(newValue));
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

   private void initSubject() {
      ObservableList<String> observableList = ChartTypes.NATAL.getObservableList();
      cbSubject.setItems(observableList);
      cbSubject.getSelectionModel().select(1);  // Natal
   }

   private void initRating() {
      ObservableList<String> observableList = Ratings.ZZ.getObservableList();
      cbRating.setItems(observableList);
      cbRating.getSelectionModel().select(0);  // Unknown
   }

   private void initLatitude() {
      List<String> latList = new ArrayList<>();
      latList.add(rosetta.getText("ui.shared.direction.north.char"));
      latList.add(rosetta.getText("ui.shared.direction.south.char"));
      var observableList = FXCollections.observableArrayList(latList);
      cbNorthSouth.setItems(observableList);
      cbNorthSouth.getSelectionModel().select(0);
   }

   private void initLongitude() {
      List<String> longList = new ArrayList<>();
      longList.add(rosetta.getText("ui.shared.direction.east.char"));
      longList.add(rosetta.getText("ui.shared.direction.west.char"));
      var observableList = FXCollections.observableArrayList(longList);
      cbEastWest.setItems(observableList);
      cbEastWest.getSelectionModel().select(0);
   }

   private void initCalendar() {
      List<String> calList = new ArrayList<>();
      calList.add(rosetta.getText("ui.shared.calendar.gregorian.char"));
      calList.add(rosetta.getText("ui.shared.calendar.julian.char"));
      var observableList = FXCollections.observableArrayList(calList);
      cbCalendar.setItems(observableList);
      cbCalendar.getSelectionModel().select(0);
   }

   private void initTimeZone() {
      var observableList = TimeZones.UT.getObservableList();
      cbTimeZone.setItems(observableList);
      cbTimeZone.getSelectionModel().select(1);  // UT
   }

   private void initLocalEastWest() {
      List<String> longList = new ArrayList<>();
      longList.add(rosetta.getText("ui.shared.direction.east.char"));
      longList.add(rosetta.getText("ui.shared.direction.west.char"));
      var observableList = FXCollections.observableArrayList(longList);
      cbLocalEastWest.setItems(observableList);
      cbLocalEastWest.getSelectionModel().select(0);
   }

   private void validateName(final String newName) {
      valChartName = new ValidatedChartName(newName);
      tfName.setText(valChartName.getNameText());
      tfName.setStyle(valChartName.isValidated() ? INPUT_DEFAULT_STYLE : INPUT_ERROR_STYLE);  //textinput
      checkStatus();
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

   private void checkCalendar(final String newValue) {
      valDate = new ValidatedDate(tfDate.getText() + '/' + newValue);
      tfDate.setStyle(valDate.isValidated() ? INPUT_DEFAULT_STYLE : INPUT_ERROR_STYLE);
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

   private void checkStatus() {
      boolean inputOk = (valChartName != null && valChartName.isValidated()
            && valLat != null && valLat.isValidated()
            && valLong != null && valLong.isValidated()
            && valDate != null && valDate.isValidated()
            && valTime != null && valTime.isValidated()
            && ((valLongLocalTime != null && valLongLocalTime.isValidated()) || !timeZoneLocalSelected));
      calculatebtn.setDisable(!inputOk);
      calculatebtn.setFocusTraversable(inputOk);
      if (inputOk) inputStatus = InputStatus.READY;
   }

   private int saveData() {
      PersistedChartDataApi api = new PersistedChartDataApi();
      int chartId = 0;
      ChartData chartData = new ChartData(chartId, constructFullDateTime(), constructLocation(), constructMetaData());
      return api.insert(chartData);
   }

   private ChartMetaData constructMetaData() {
      String inputName = tfName.getText();
      String inputDescription = tfDescription.getText().trim();
      String inputSource = tfSource.getText().trim();
      Ratings inputRating = Ratings.ZZ.ratingForName(cbRating.getValue());
      ChartTypes inputChartType = ChartTypes.UNKNOWN.chartTypeForLocalName(cbSubject.getValue());
      return new ChartMetaData(inputName, inputDescription, inputSource, inputChartType, inputRating);
   }

   private Location constructLocation() {
      String enteredLocation = tfLocationName.getText().trim();
      String longDir = cbEastWest.getValue();
      if (longDir.equalsIgnoreCase("O")) longDir = "E";
      if (longDir.equalsIgnoreCase("W")) valLong.applyWesternLongitude();
      GeographicCoordinate longitudeCoordinate = new GeographicCoordinate(valLong.getDegrees(), valLong.getMinutes(),
            valLong.getSeconds(), longDir, valLong.getValue());
      String latDir = cbNorthSouth.getValue();
      if (latDir.equalsIgnoreCase("Z")) latDir = "S";
      if (latDir.equals("S")) valLat.applySouthernLatitude();
      GeographicCoordinate latitudeCoordinate = new GeographicCoordinate(valLat.getDegrees(), valLat.getMinutes(),
            valLat.getSeconds(), latDir, valLat.getValue());
      return new Location(longitudeCoordinate, latitudeCoordinate, enteredLocation);
   }

   private FullDateTime constructFullDateTime() {
      SimpleDateTime dateTime = new SimpleDateTime(valDate.getSimpleDate(), valTime.getSimpleTime());
      TimeZones selectedTimeZone = TimeZones.UT.timeZoneForName(cbTimeZone.getValue());
      boolean selectedDst = cBoxDst.isSelected();
      double offSetForLmt = 0.0;
      if (selectedTimeZone == TimeZones.LMT) {
         offSetForLmt = valLongLocalTime.getValue() / 15.0;
         if (cbLocalEastWest.getValue().equalsIgnoreCase("W")) offSetForLmt = -offSetForLmt;
      }
      return new FullDateTime(dateTime, selectedTimeZone, selectedDst, offSetForLmt);
   }

}
