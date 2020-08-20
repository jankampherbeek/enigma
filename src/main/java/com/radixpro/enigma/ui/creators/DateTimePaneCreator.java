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
      GridPane gridPane = new GridPaneBuilder().setPrefHeight(INPUT_DATETIME_HEIGHT).setPrefWidth(INPUT_WIDTH).setHGap(GAP).setVGap(GAP).build();
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
      lblDate = new LabelBuilder("ui.charts.input.date.name").setPrefWidth(INPUT_MINOR_DATA_WIDTH).build();
      lblCalendar = new LabelBuilder("ui.charts.input.date.calendar").setPrefWidth(INPUT_MICRO_DATA_WIDTH).build();
      lblTime = new LabelBuilder("ui.charts.input.time.name").setPrefWidth(INPUT_MINOR_DATA_WIDTH).build();
      lblTimeZone = new LabelBuilder("ui.charts.input.time.timezone").setPrefWidth(INPUT_DATA_WIDTH).build();
      lblDst = new LabelBuilder("ui.shared.dst").setPrefWidth(INPUT_MICRO_DATA_WIDTH).build();
   }

}
