/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.creators;


import com.radixpro.enigma.shared.Rosetta;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.radixpro.enigma.ui.shared.UiDictionary.*;

/**
 * Creates a GridPane for datainput for a location.
 */
public class LocationGridPaneCreator {

   private Rosetta rosetta;
   private Label lblLocationName;
   private Label lblLocationLatitude;
   private Label lblLocationLongitude;

   /**
    * Create a GridPane.
    *
    * @param tfLocationName      TextField for the location name. PRE: not null.
    * @param tfLocationLongitude TextField for the longitude. PRE: not null.
    * @param tfLocationLatitude  TextField for the latritude. PRE: not null.
    * @param cbEastWest          ChoiceBox for east/west. PRE: not null.
    * @param cbNorthSouth        ChoiceBox for north/south. PRE: not null.
    * @return The populated GridPane.
    */
   public GridPane createGridPane(final TextField tfLocationName, final TextField tfLocationLongitude, final TextField tfLocationLatitude,
                                  final ChoiceBox<String> cbEastWest, final ChoiceBox<String> cbNorthSouth) {
      checkNotNull(tfLocationName);
      checkNotNull(tfLocationLongitude);
      checkNotNull(tfLocationLatitude);
      checkNotNull(cbEastWest);
      checkNotNull(cbNorthSouth);
      initialize();
      GridPane gridPane = new GridPaneBuilder().setPrefHeight(INPUT_LOCATION_HEIGHT).setHGap(GAP).setVGap(GAP).setPadding(GAP).build();
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

   private void initialize() {
      rosetta = Rosetta.getRosetta();
      lblLocationName = LabelFactory.createLabel(rosetta.getText("ui.charts.input.location.name"), INPUT_DATA_WIDTH);
      lblLocationLatitude = LabelFactory.createLabel(rosetta.getText("ui.charts.input.location.latitude"), INPUT_HALF_DATA_WIDTH);
      lblLocationLongitude = LabelFactory.createLabel(rosetta.getText("ui.charts.input.location.longitude"), INPUT_HALF_DATA_WIDTH);
   }

}
