/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.domain.input.ChartMetaData;
import com.radixpro.enigma.domain.input.DateTimeJulian;
import com.radixpro.enigma.domain.input.Location;
import com.radixpro.enigma.references.ChartTypes;
import com.radixpro.enigma.references.InputStatus;
import com.radixpro.enigma.references.Ratings;
import com.radixpro.enigma.references.TimeZones;
import com.radixpro.enigma.ui.creators.*;
import com.radixpro.enigma.ui.helpers.DateTimeJulianCreator;
import com.radixpro.enigma.ui.shared.Help;
import com.radixpro.enigma.ui.validators.*;
import com.radixpro.enigma.xchg.api.PersistedChartDataApi;
import com.radixpro.enigma.xchg.domain.FullChartInputData;
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

import static com.radixpro.enigma.ui.shared.UiDictionary.*;

public class ChartsInput {

   private static final double HEIGHT = 820.0;
   private static final double GP_GENERAL_HEIGHT = 240.0;
   private static final double INPUT_HEIGHT = 25.0;
   private final Rosetta rosetta;
   private final PersistedChartDataApi persistedChartDataApi;
   private final DateTimeJulianCreator dateTimeJulianCreator;
   private final ValidatedChartName validatedChartName;
   private final ValidatedLatitude validatedLatitude;
   private final ValidatedLongitude validatedLongitude;
   private final ValidatedDate validatedDate;
   private final ValidatedTime validatedTime;
   private Stage stage;
   private ValidatedLongitude validatedLocalTimeLong;   // temporary solution
   private Label lblDescription;
   private Label lblLocalTime;
   private Label lblName;
   private Label lblPageTitle;
   private Label lblRating;
   private Label lblSource;
   private Label lblSubject;
   private Label lblSubTitleDateAndTime;
   private Label lblSubTitleGeneral;
   private Label lblSubTitleLocation;
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

   private boolean chartNameValid;
   private boolean longitudeValid;
   private boolean latitudeValid;
   private boolean dateValid;
   private boolean timeValid;
   private boolean localTimeValid;

   private double valLong;
   private double valLat;

   private InputStatus inputStatus = InputStatus.INCOMPLETE;

   public ChartsInput(final PersistedChartDataApi persistedChartDataApi, final ValidatedChartName validatedChartName,
                      final ValidatedDate validatedDate, final ValidatedTime validatedTime, final ValidatedLongitude validatedLongitude,
                      final ValidatedLatitude validatedLatitude, final DateTimeJulianCreator dateTimeJulianCreator) {
      this.rosetta = Rosetta.getRosetta();
      this.persistedChartDataApi = persistedChartDataApi;
      this.validatedChartName = validatedChartName;
      this.validatedDate = validatedDate;
      this.validatedTime = validatedTime;
      this.validatedLongitude = validatedLongitude;
      this.validatedLatitude = validatedLatitude;
      this.dateTimeJulianCreator = dateTimeJulianCreator;
   }

   public void show() {
      defineLeafs();
      definePanes();
      defineStructure();
      initialize();
      stage = new Stage();
      stage.setWidth(INPUT_WIDTH);
      stage.setScene(new Scene(createVBox()));
      stage.showAndWait();
   }


   @SuppressWarnings("unchecked")
   private void defineLeafs() {
      lblDescription = new LabelBuilder("ui.charts.input.description").setPrefWidth(INPUT_DATA_WIDTH).build();
      lblLocalTime = new LabelBuilder("ui.charts.input.time.localtime").setPrefWidth(INPUT_MINOR_DATA_WIDTH).build();
      lblLocalTime.setDisable(true);
      lblName = new LabelBuilder("ui.charts.input.name").setPrefWidth(INPUT_DATA_WIDTH).build();
      lblPageTitle = new LabelBuilder("ui.charts.input.pagetitle").setPrefWidth(INPUT_WIDTH).setStyleClass("titletext").build();
      lblRating = new LabelBuilder("ui.charts.input.rating").setPrefWidth(INPUT_HALF_DATA_WIDTH).build();
      lblSource = new LabelBuilder("ui.charts.input.source").setPrefWidth(INPUT_DATA_WIDTH).build();
      lblSubject = new LabelBuilder("ui.charts.input.subject").setPrefWidth(INPUT_HALF_DATA_WIDTH).build();
      lblSubTitleGeneral = new LabelBuilder("ui.charts.input.subtitle.general").setPrefWidth(INPUT_WIDTH).setStyleClass("subtitletext").build();
      lblSubTitleLocation = new LabelBuilder("ui.charts.input.subtitle.location").setPrefWidth(INPUT_WIDTH).setStyleClass("subtitletext").build();
      lblSubTitleDateAndTime = new LabelBuilder("ui.charts.input.subtitle.dateandtime").setPrefWidth(INPUT_WIDTH).setStyleClass("subtitletext").build();
      cBoxDst = new CheckBoxBuilder().setAlignment(Pos.CENTER_RIGHT).setStyleClass("inputDefault").build();
      cbCalendar = new ChoiceBoxBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(INPUT_MICRO_DATA_WIDTH).setStyleClass("inputDefault").build();
      cbEastWest = new ChoiceBoxBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(INPUT_MICRO_DATA_WIDTH).setStyleClass("inputDefault").build();
      cbLocalEastWest = new ChoiceBoxBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(INPUT_MICRO_DATA_WIDTH).setStyleClass("inputDefault").build();
      cbLocalEastWest.setDisable(true);
      cbLocalEastWest.setFocusTraversable(false);
      cbNorthSouth = new ChoiceBoxBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(INPUT_MICRO_DATA_WIDTH).setStyleClass("inputDefault").build();
      cbRating = new ChoiceBoxBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(INPUT_HALF_DATA_WIDTH).setStyleClass("inputDefault").build();
      cbSubject = new ChoiceBoxBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(INPUT_HALF_DATA_WIDTH).setStyleClass("inputDefault").build();
      cbTimeZone = new ChoiceBoxBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(INPUT_DATA_WIDTH).setStyleClass("inputDefault").build();

      tfDate = new TextFieldBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(INPUT_MINOR_DATA_WIDTH).setStyleClass("inputDefault").build();
      tfDescription = new TextFieldBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(INPUT_DATA_WIDTH).setStyleClass("inputDefault").build();
      tfLocaltime = new TextFieldBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(INPUT_MINOR_DATA_WIDTH).setStyleClass("inputDefault").build();
      tfLocaltime.setEditable(false);
      tfLocaltime.setDisable(true);
      tfLocationLatitude = new TextFieldBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(INPUT_MINOR_DATA_WIDTH).setStyleClass("inputDefault").build();
      tfLocationLongitude = new TextFieldBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(INPUT_MINOR_DATA_WIDTH).setStyleClass("inputDefault").build();
      tfLocationName = new TextFieldBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(INPUT_DATA_WIDTH).setStyleClass("inputDefault").build();
      tfName = new TextFieldBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(INPUT_DATA_WIDTH).setStyleClass("inputDefault").build();
      tfSource = new TextFieldBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(INPUT_DATA_WIDTH).setStyleClass("inputDefault").build();
      tfTime = new TextFieldBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(INPUT_MINOR_DATA_WIDTH).setStyleClass("inputDefault").build();
   }

   private void definePanes() {
      panePageTitle = new PaneBuilder().setHeight(TITLE_HEIGHT).setWidth(INPUT_WIDTH).setStyleClass("titlepane").build();
      paneSubTitleGeneral = new PaneBuilder().setHeight(SUBTITLE_HEIGHT).setWidth(INPUT_WIDTH).setStyleClass("subtitlepane").build();
      paneSubTitleLocation = new PaneBuilder().setHeight(SUBTITLE_HEIGHT).setWidth(INPUT_WIDTH).setStyleClass("subtitlepane").build();
      paneSubTitleDateAndTime = new PaneBuilder().setHeight(SUBTITLE_HEIGHT).setWidth(INPUT_WIDTH).setStyleClass("subtitlepane").build();
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
      vBox.setPrefWidth(INPUT_WIDTH);
      vBox.setPrefHeight(HEIGHT);
      vBox.getChildren().addAll(panePageTitle, paneSubTitleGeneral, createGridPaneGeneral(), paneSubTitleLocation, createGridPaneLocation(),
            paneSubTitleDateAndTime, createGridPaneDateAndTime(), createPaneBtnBar());
      return vBox;
   }

   private GridPane createGridPaneGeneral() {
      GridPane gridPane = new GridPaneBuilder().setPrefHeight(GP_GENERAL_HEIGHT).setPrefWidth(INPUT_WIDTH).setVGap(GAP).setHGap(GAP).build();
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
      return new LocationGridPaneCreator().createGridPane(tfLocationName, tfLocationLongitude, tfLocationLatitude, cbEastWest, cbNorthSouth);
   }

   private GridPane createGridPaneDateAndTime() {
      return new DateTimePaneCreator().createGridPane(lblLocalTime, tfDate, tfTime, tfLocaltime, cbCalendar, cbTimeZone, cbLocalEastWest, cBoxDst);
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
      chartNameValid = false;
      dateValid = false;
      timeValid = false;
      longitudeValid = false;
      latitudeValid = false;
      localTimeValid = false;
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

   private void validateName(final String newValue) {
      chartNameValid = validatedChartName.validate(newValue.trim());
      tfName.setStyle(chartNameValid ? INPUT_DEFAULT_STYLE : INPUT_ERROR_STYLE);  //textinput
      checkStatus();
   }

   private void validateLongitude(final String newLongitude) {
      if (longitudeValid = validatedLongitude.validate(newLongitude)) valLong = validatedLongitude.getValue();
      tfLocationLongitude.setStyle(longitudeValid ? INPUT_DEFAULT_STYLE : INPUT_ERROR_STYLE);
      checkStatus();
   }

   private void validateLocalTime(final String newLocalTime) {
      validatedLocalTimeLong = new ValidatedLongitude();            // TODO  temporary solution
      localTimeValid = validatedLocalTimeLong.validate(newLocalTime);
      tfLocaltime.setStyle(localTimeValid ? INPUT_DEFAULT_STYLE : INPUT_ERROR_STYLE);
      checkStatus();
   }

   private void validateLatitude(final String newLatitude) {
      if (latitudeValid = validatedLatitude.validate(newLatitude)) valLat = validatedLatitude.getValue();
      tfLocationLatitude.setStyle(latitudeValid ? INPUT_DEFAULT_STYLE : INPUT_ERROR_STYLE);
      checkStatus();
   }

   private void validateDate(final String newDate) {
      dateValid = validatedDate.validate(newDate + '/' + cbCalendar.getValue());
      tfDate.setStyle(dateValid ? INPUT_DEFAULT_STYLE : INPUT_ERROR_STYLE);
      checkStatus();
   }

   private void validateTime(final String newTime) {
      timeValid = validatedTime.validate(newTime);
      tfTime.setStyle(timeValid ? INPUT_DEFAULT_STYLE : INPUT_ERROR_STYLE);
      checkStatus();
   }

   private void checkCalendar(final String newValue) {
      dateValid = validatedDate.validate(tfDate.getText() + '/' + newValue);
      tfDate.setStyle(dateValid ? INPUT_DEFAULT_STYLE : INPUT_ERROR_STYLE);
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
      boolean inputOk = (chartNameValid && latitudeValid && longitudeValid && dateValid && timeValid && (localTimeValid || !timeZoneLocalSelected));
      calculatebtn.setDisable(!inputOk);
      calculatebtn.setFocusTraversable(inputOk);
      if (inputOk) inputStatus = InputStatus.READY;
   }

   private int saveData() {
      int chartId = 0;
      FullChartInputData fullChartInputData = new FullChartInputData(chartId, constructFullDateTime(), constructLocation(), constructMetaData());
      return persistedChartDataApi.insert(fullChartInputData);
   }

   private String constructDataInput() {
      String SPACE = " ";
      String NEWLINE = "\n";
      TimeZones zone = TimeZones.timeZoneForName(cbTimeZone.getValue());
      String offsetLmt = "";
      if (zone == TimeZones.LMT) offsetLmt = "Offset : " + tfLocaltime.getText() + SPACE + cbLocalEastWest.getValue();
      String dstText = rosetta.getText(cBoxDst.isSelected() ? "ui.shared.dst" : "ui.shared.nodst");
      return tfDate.getText() + SPACE + cbCalendar.getValue() + SPACE + tfTime.getText() + SPACE + rosetta.getText(zone.getNameForRB()) + SPACE + dstText +
            offsetLmt + NEWLINE + tfLocationLatitude.getText() + cbEastWest.getValue() + SPACE + tfLocationLongitude.getText() + SPACE +
            cbNorthSouth.getValue() + NEWLINE + rosetta.getText("ui.shared.source") + SPACE + tfSource.getText();
   }

   private ChartMetaData constructMetaData() {
      String inputName = tfName.getText();
      String inputDescription = tfDescription.getText().trim();
      String inputSource = tfSource.getText().trim();
      Ratings inputRating = Ratings.ratingForName(cbRating.getValue());
      ChartTypes inputChartType = ChartTypes.UNKNOWN.chartTypeForLocalName(cbSubject.getValue());
      String dataInput = constructDataInput();
      return new ChartMetaData(inputName, inputDescription, inputChartType, inputRating, dataInput);
   }

   private Location constructLocation() {
      return new Location(validatedLatitude.getValue(), validatedLongitude.getValue());
   }

   private DateTimeJulian constructFullDateTime() {
      TimeZones zone = TimeZones.timeZoneForName(cbTimeZone.getValue());
//      double offsetLmt = validatedLocalTimeLong.getValue();     // FIXME handle offset for local time, combine fix with refactoring.
      double offsetLmt = 0.0;
      return dateTimeJulianCreator.createDateTime(tfDate.getText(), cbCalendar.getValue(), tfTime.getText(), zone, cBoxDst.isSelected(), offsetLmt);
   }

}
