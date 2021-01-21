/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens.blocks;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.domain.input.DateTimeJulian;
import com.radixpro.enigma.references.InputStatus;
import com.radixpro.enigma.references.TimeZones;
import com.radixpro.enigma.shared.exceptions.InputBlockIncompleteException;
import com.radixpro.enigma.ui.creators.*;
import com.radixpro.enigma.ui.helpers.DateTimeCreator;
import com.radixpro.enigma.ui.validators.ValidatedDate;
import com.radixpro.enigma.ui.validators.ValidatedLongitude;
import com.radixpro.enigma.ui.validators.ValidatedTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.radixpro.enigma.ui.shared.UiDictionary.*;

/**
 * Input block for date and time.
 */
public class DateTimeInputBlock extends InputBlock {

   private Label lblLocalTime;
   private Label lblDate;
   private Label lblCalendar;
   private Label lblTime;
   private Label lblTimeZone;
   private Label lblDst;
   private CheckBox cBoxDst;
   private ChoiceBox<String> cbCalendar;
   private ChoiceBox<String> cbLocalEastWest;
   private ChoiceBox<String> cbTimeZone;
   private TextField tfDate;
   private TextField tfLocaltime;
   private TextField tfTime;
   private final ValidatedDate valDate;
   private final ValidatedTime valTime;
   private final ValidatedLongitude valLongLocalTime;
   private boolean timeZoneLocalSelected = false;
   private GridPane gridPane;
   private boolean dateValid;
   private boolean timeValid;
   private boolean localTimeValid;


   public DateTimeInputBlock(@NotNull final ValidatedDate validatedDate, @NotNull final ValidatedTime validatedTime,
                             @NotNull final ValidatedLongitude valLongLocalTime) {
      super();
      this.valDate = validatedDate;
      this.valTime = validatedTime;
      this.valLongLocalTime = valLongLocalTime;
   }

   @Override
   protected void initialize() {
      dateValid = false;
      timeValid = false;
      localTimeValid = false;
      lblLocalTime = new LabelBuilderObs("ui.charts.input.time.localtime").setPrefWidth(INPUT_MINOR_DATA_WIDTH).build();
      lblLocalTime.setDisable(true);
      lblDate = new LabelBuilderObs("ui.charts.input.date.name").setPrefWidth(INPUT_MINOR_DATA_WIDTH).build();
      lblCalendar = new LabelBuilderObs("ui.charts.input.date.calendar").setPrefWidth(INPUT_MICRO_DATA_WIDTH).build();
      lblTime = new LabelBuilderObs("ui.charts.input.time.name").setPrefWidth(INPUT_MINOR_DATA_WIDTH).build();
      lblTimeZone = new LabelBuilderObs("ui.charts.input.time.timezone").setPrefWidth(INPUT_DATA_WIDTH).build();
      lblDst = new LabelBuilderObs("ui.shared.dst").setPrefWidth(INPUT_MICRO_DATA_WIDTH).build();
      cBoxDst = new CheckBoxBuilder().setAlignment(Pos.CENTER_RIGHT).setStyleClass(INPUT_STYLE).build();
      cbCalendar = new ChoiceBoxBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(INPUT_MICRO_DATA_WIDTH).setStyleClass(INPUT_STYLE).build();
      cbLocalEastWest = new ChoiceBoxBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(INPUT_MICRO_DATA_WIDTH).setStyleClass(INPUT_STYLE).build();
      cbLocalEastWest.setDisable(true);
      cbLocalEastWest.setFocusTraversable(false);
      cbTimeZone = new ChoiceBoxBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(INPUT_DATA_WIDTH).setStyleClass(INPUT_STYLE).build();
      tfDate = new TextFieldBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(INPUT_MINOR_DATA_WIDTH).setStyleClass(INPUT_STYLE).build();
      tfLocaltime = new TextFieldBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(INPUT_MINOR_DATA_WIDTH).setStyleClass(INPUT_STYLE).build();
      tfLocaltime.setEditable(false);
      tfLocaltime.setDisable(true);
      tfTime = new TextFieldBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(INPUT_MINOR_DATA_WIDTH).setStyleClass(INPUT_STYLE).build();
      initCalendar();
      initLocalEastWest();
      initTimeZone();
      defineListeners();
      createGridPane();
   }

   private void defineListeners() {
      tfDate.textProperty().addListener((observable, oldValue, newValue) -> validateDate(newValue));
      tfTime.textProperty().addListener((observable, oldValue, newValue) -> validateTime(newValue));
      cbTimeZone.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
            checkTimeZones(newValue));
      tfLocaltime.textProperty().addListener((observable, oldValue, newValue) -> validateLocalTime(newValue));
      cbCalendar.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
            checkCalendar(newValue));
   }

   private void createGridPane() {
      gridPane = new GridPaneBuilder().setPrefHeight(INPUT_DATETIME_HEIGHT).setPrefWidth(INPUT_WIDTH).setHGap(GAP).setVGap(GAP).build();
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
   }

   private void initCalendar() {
      List<String> calList = new ArrayList<>();
      calList.add(Rosetta.getText("ui.shared.calendar.gregorian.char"));
      calList.add(Rosetta.getText("ui.shared.calendar.julian.char"));
      ObservableList<String> observableList = FXCollections.observableArrayList(calList);
      cbCalendar.setItems(observableList);
      cbCalendar.getSelectionModel().select(0);
   }

   private void initTimeZone() {
      ObservableList<String> observableList = TimeZones.UT.getObservableList();
      cbTimeZone.setItems(observableList);
      cbTimeZone.getSelectionModel().select(1);  // UT
   }

   private void initLocalEastWest() {
      List<String> longList = new ArrayList<>();
      longList.add(Rosetta.getText("ui.shared.direction.east.char"));
      longList.add(Rosetta.getText("ui.shared.direction.west.char"));
      var observableList = FXCollections.observableArrayList(longList);
      cbLocalEastWest.setItems(observableList);
      cbLocalEastWest.getSelectionModel().select(0);
   }

   private void validateDate(final String newDate) {
      dateValid = valDate.validate(newDate + '/' + cbCalendar.getValue());
      tfDate.setStyle(dateValid ? INPUT_DEFAULT_STYLE : INPUT_ERROR_STYLE);
      checkStatus();
   }

   private void validateTime(final String newTime) {
      timeValid = valTime.validate(newTime);
      tfTime.setStyle(timeValid ? INPUT_DEFAULT_STYLE : INPUT_ERROR_STYLE);
      checkStatus();
   }

   private void validateLocalTime(final String newLocalTime) {
      localTimeValid = valLongLocalTime.validate(newLocalTime);
      tfLocaltime.setStyle(localTimeValid ? INPUT_DEFAULT_STYLE : INPUT_ERROR_STYLE);
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
      dateValid = valDate.validate(tfDate.getText() + '/' + newValue);
      tfDate.setStyle(dateValid ? INPUT_DEFAULT_STYLE : INPUT_ERROR_STYLE);
      checkStatus();
   }

   private void checkStatus() {
      boolean inputOk = (dateValid && timeValid && (localTimeValid || !timeZoneLocalSelected));
      if (inputOk) inputStatus = InputStatus.READY;
   }

   public GridPane getGridPane() {
      initialize();
      return gridPane;
   }

   public DateTimeJulian getDateTime() throws InputBlockIncompleteException {
      if (inputStatus != InputStatus.READY)
         throw new InputBlockIncompleteException("Retrieving date and time location for DateTimeInput while InputSatus is " + inputStatus.name());
      String dateText = tfDate.getText();
      String timeText = tfTime.getText();
      String cal = cbCalendar.getValue();
      TimeZones selectedTimeZone = TimeZones.UT.timeZoneForName(cbTimeZone.getValue());
      boolean selectedDst = cBoxDst.isSelected();
      double offSetForLmt = 0.0;
      if (selectedTimeZone == TimeZones.LMT) {
         offSetForLmt = valLongLocalTime.getValue() / 15.0;
         if (cbLocalEastWest.getValue().equalsIgnoreCase("W")) offSetForLmt = -offSetForLmt;
      }
      return DateTimeCreator.INSTANCE.createDateTimeJulian(dateText, cal, timeText, selectedTimeZone, selectedDst, offSetForLmt);
   }
}
