/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.creators;

import com.radixpro.enigma.Rosetta;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import static com.radixpro.enigma.ui.shared.UiDictionary.*;

/**
 * Creates a GridPane for datainput for date and time.
 */
public class DateTimePaneCreator {
   private Rosetta rosetta;
   private Label lblDate;
   private Label lblCalendar;
   private Label lblTime;
   private Label lblTimeZone;
   private Label lblDst;

   public GridPane createGridPane(final Label lblLocalTime, final TextField tfDate, final TextField tfTime, final TextField tfLocaltime,
                                  final ChoiceBox<String> cbCalendar, final ChoiceBox<String> cbTimeZone, final ChoiceBox<String> cbLocalEastWest,
                                  final CheckBox cBoxDst) {
      initialize();
      GridPane gridPane = GridPaneFactory.createGridPane(INPUT_DATETIME_HEIGHT, INPUT_WIDTH, GAP);
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

   private void initialize() {
      rosetta = Rosetta.getRosetta();
      lblDate = LabelFactory.createLabel(rosetta.getText("ui.charts.input.date.name"), INPUT_MINOR_DATA_WIDTH);
      lblCalendar = LabelFactory.createLabel(rosetta.getText("ui.charts.input.date.calendar"), INPUT_MICRO_DATA_WIDTH);
      lblTime = LabelFactory.createLabel(rosetta.getText("ui.charts.input.time.name"), INPUT_MINOR_DATA_WIDTH);
      lblTimeZone = LabelFactory.createLabel(rosetta.getText("ui.charts.input.time.timezone"), INPUT_DATA_WIDTH);
      lblDst = LabelFactory.createLabel(rosetta.getText("ui.shared.dst"), INPUT_MICRO_DATA_WIDTH);
   }

}
