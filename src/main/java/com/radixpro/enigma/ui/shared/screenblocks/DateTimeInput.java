/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.screenblocks;

import com.radixpro.enigma.domain.datetime.FullDateTime;
import com.radixpro.enigma.domain.datetime.SimpleDateTime;
import com.radixpro.enigma.shared.exceptions.InputBlockIncompleteException;
import com.radixpro.enigma.ui.shared.InputStatus;
import com.radixpro.enigma.ui.shared.creators.*;
import com.radixpro.enigma.ui.shared.validation.ValidatedDate;
import com.radixpro.enigma.ui.shared.validation.ValidatedLongitude;
import com.radixpro.enigma.ui.shared.validation.ValidatedTime;
import com.radixpro.enigma.xchg.domain.TimeZones;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

import static com.radixpro.enigma.ui.shared.UiDictionary.*;

/**
 * Input block for date and time.
 */
public class DateTimeInput extends InputBlock {

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
   private ValidatedDate valDate;
   private ValidatedTime valTime;
   private ValidatedLongitude valLongLocalTime;
   private boolean timeZoneLocalSelected = false;
   private GridPane gridPane;


   @Override
   protected void initialize() {
      lblLocalTime = LabelFactory.createLabel(rosetta.getText("ui.charts.input.time.localtime"), INPUT_MINOR_DATA_WIDTH);
      lblLocalTime.setDisable(true);
      lblDate = LabelFactory.createLabel(rosetta.getText("ui.charts.input.date.name"), INPUT_MINOR_DATA_WIDTH);
      lblCalendar = LabelFactory.createLabel(rosetta.getText("ui.charts.input.date.calendar"), INPUT_MICRO_DATA_WIDTH);
      lblTime = LabelFactory.createLabel(rosetta.getText("ui.charts.input.time.name"), INPUT_MINOR_DATA_WIDTH);
      lblTimeZone = LabelFactory.createLabel(rosetta.getText("ui.charts.input.time.timezone"), INPUT_DATA_WIDTH);
      lblDst = LabelFactory.createLabel(rosetta.getText("ui.shared.dst"), INPUT_MICRO_DATA_WIDTH);
      cBoxDst = CheckBoxFactory.createCheckBox(Pos.CENTER_RIGHT, INPUT_STYLE);
      cbCalendar = ChoiceBoxFactory.createChoiceBox(INPUT_HEIGHT, INPUT_MICRO_DATA_WIDTH, INPUT_STYLE);
      cbLocalEastWest = ChoiceBoxFactory.createChoiceBox(INPUT_HEIGHT, INPUT_MICRO_DATA_WIDTH, INPUT_STYLE);
      cbLocalEastWest.setDisable(true);
      cbLocalEastWest.setFocusTraversable(false);
      cbTimeZone = ChoiceBoxFactory.createChoiceBox(INPUT_HEIGHT, INPUT_DATA_WIDTH, INPUT_STYLE);
      tfDate = TextFieldFactory.createTextField(INPUT_HEIGHT, INPUT_MINOR_DATA_WIDTH, INPUT_STYLE);
      tfLocaltime = TextFieldFactory.createTextField(INPUT_HEIGHT, INPUT_MINOR_DATA_WIDTH, INPUT_STYLE);
      tfLocaltime.setEditable(false);
      tfLocaltime.setDisable(true);
      tfTime = TextFieldFactory.createTextField(INPUT_HEIGHT, INPUT_MINOR_DATA_WIDTH, INPUT_STYLE);
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
      gridPane = GridPaneFactory.createGridPane(INPUT_DATETIME_HEIGHT, INPUT_WIDTH, GAP);
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
      calList.add(rosetta.getText("ui.shared.calendar.gregorian.char"));
      calList.add(rosetta.getText("ui.shared.calendar.julian.char"));
      ObservableList<String> observableList = FXCollections.observableArrayList(calList);
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

   private void validateLocalTime(final String newLocalTime) {
      valLongLocalTime = new ValidatedLongitude(newLocalTime);
      tfLocaltime.setStyle(valLongLocalTime.isValidated() ? INPUT_DEFAULT_STYLE : INPUT_ERROR_STYLE);
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
      boolean inputOk = (valDate != null && valDate.isValidated()
            && valTime != null && valTime.isValidated()
            && ((valLongLocalTime != null && valLongLocalTime.isValidated()) || !timeZoneLocalSelected));
      if (inputOk) inputStatus = InputStatus.READY;
   }

   /**
    * Returns populated GridPane.
    */
   public GridPane getGridPane() {
      return gridPane;
   }

   /**
    * Retrieve date and time.
    *
    * @return Instance of FullDateTime.
    */
   public FullDateTime getDateTime() throws InputBlockIncompleteException {
      if (inputStatus != InputStatus.READY)
         throw new InputBlockIncompleteException("Retrieving date and time location for DateTimeInput while InputSatus is " + inputStatus.name());
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
