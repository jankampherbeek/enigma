/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens.blocks;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.domain.input.Location;
import com.radixpro.enigma.references.InputStatus;
import com.radixpro.enigma.shared.exceptions.InputBlockIncompleteException;
import com.radixpro.enigma.ui.creators.ChoiceBoxBuilder;
import com.radixpro.enigma.ui.creators.GridPaneBuilder;
import com.radixpro.enigma.ui.creators.LabelBuilderObs;
import com.radixpro.enigma.ui.creators.TextFieldBuilder;
import com.radixpro.enigma.ui.validators.ValidatedLatitude;
import com.radixpro.enigma.ui.validators.ValidatedLongitude;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.radixpro.enigma.ui.shared.UiDictionary.*;

/**
 * Input block for location.
 */
public class LocationInputBlock extends InputBlock {

   private Label lblLocationName;
   private Label lblLocationLatitude;
   private Label lblLocationLongitude;
   private TextField tfLocationLatitude;
   private TextField tfLocationLongitude;
   private TextField tfLocationName;
   private final ValidatedLongitude valLong;
   private final ValidatedLatitude valLat;
   private ChoiceBox<String> cbEastWest;
   private ChoiceBox<String> cbNorthSouth;
   private GridPane gridPane;
   private boolean longitudeValid = false;
   private boolean latitudeValid = false;

   public LocationInputBlock(@NotNull final ValidatedLongitude valLong, @NotNull final ValidatedLatitude valLat) {
      super();
      this.valLong = valLong;
      this.valLat = valLat;
   }

   public String getLatitudeInput() {
      return tfLocationLatitude.getText();
   }

   public String getLongitudeInput() {
      return tfLocationLongitude.getText();
   }

   public String getLocationName() {
      return tfLocationName.getText();
   }

   @Override
   protected void initialize() {
      lblLocationName = new LabelBuilderObs("ui.charts.input.location.name").setPrefWidth(INPUT_DATA_WIDTH).build();
      lblLocationLatitude = new LabelBuilderObs("ui.charts.input.location.latitude").setPrefWidth(INPUT_HALF_DATA_WIDTH).build();
      lblLocationLongitude = new LabelBuilderObs("ui.charts.input.location.longitude").setPrefWidth(INPUT_HALF_DATA_WIDTH).build();
      tfLocationLatitude = new TextFieldBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(INPUT_MINOR_DATA_WIDTH).setStyleClass(INPUT_STYLE).build();
      tfLocationLongitude = new TextFieldBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(INPUT_MINOR_DATA_WIDTH).setStyleClass(INPUT_STYLE).build();
      tfLocationName = new TextFieldBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(INPUT_DATA_WIDTH).setStyleClass(INPUT_STYLE).build();
      cbEastWest = new ChoiceBoxBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(INPUT_MICRO_DATA_WIDTH).setStyleClass(INPUT_STYLE).build();
      cbNorthSouth = new ChoiceBoxBuilder().setPrefHeight(INPUT_HEIGHT).setPrefWidth(INPUT_MICRO_DATA_WIDTH).setStyleClass(INPUT_STYLE).build();
      initLongitude();
      initLatitude();
      tfLocationLongitude.textProperty().addListener((observable, oldValue, newValue) -> validateLongitude(newValue));
      tfLocationLatitude.textProperty().addListener((observable, oldValue, newValue) -> validateLatitude(newValue));
      createGridPane();
   }

   private void createGridPane() {
      gridPane = new GridPaneBuilder().setPrefHeight(INPUT_LOCATION_HEIGHT).setHGap(GAP).setVGap(GAP).setPadding(GAP).build();
      gridPane.add(lblLocationName, 0, 0, 4, 1);
      gridPane.add(tfLocationName, 0, 1, 4, 1);
      gridPane.add(lblLocationLongitude, 0, 2, 2, 1);
      gridPane.add(lblLocationLatitude, 2, 2, 2, 1);
      gridPane.add(tfLocationLongitude, 0, 3, 1, 1);
      gridPane.add(cbEastWest, 1, 3, 1, 1);
      gridPane.add(tfLocationLatitude, 2, 3, 1, 1);
      gridPane.add(cbNorthSouth, 3, 3, 1, 1);
   }

   private void initLatitude() {
      List<String> latList = new ArrayList<>();
      latList.add(Rosetta.getText("ui.shared.direction.north.char"));
      latList.add(Rosetta.getText("ui.shared.direction.south.char"));
      ObservableList<String> observableList = FXCollections.observableArrayList(latList);
      cbNorthSouth.setItems(observableList);
      cbNorthSouth.getSelectionModel().select(0);
   }

   private void initLongitude() {
      List<String> longList = new ArrayList<>();
      longList.add(Rosetta.getText("ui.shared.direction.east.char"));
      longList.add(Rosetta.getText("ui.shared.direction.west.char"));
      ObservableList<String> observableList = FXCollections.observableArrayList(longList);
      cbEastWest.setItems(observableList);
      cbEastWest.getSelectionModel().select(0);
   }

   private void validateLongitude(final String newLongitude) {
      longitudeValid = valLong.validate(newLongitude);
      tfLocationLongitude.setStyle(longitudeValid ? INPUT_DEFAULT_STYLE : INPUT_ERROR_STYLE);
      checkStatus();
   }

   private void validateLatitude(final String newLatitude) {
      latitudeValid = valLat.validate(newLatitude);
      tfLocationLatitude.setStyle(latitudeValid ? INPUT_DEFAULT_STYLE : INPUT_ERROR_STYLE);
      checkStatus();
   }

   private void checkStatus() {
      boolean inputOk = (latitudeValid && longitudeValid);
      if (inputOk) inputStatus = InputStatus.READY;
   }

   /**
    * Returns populated GridPane.
    *
    * @return Instance of GridPane.
    */
   public GridPane getGridPane() {
      initialize();
      return gridPane;
   }

   /**
    * Return populated Location.
    *
    * @return Instance of Location.
    */
   public Location getLocation() throws InputBlockIncompleteException {
      if (inputStatus != InputStatus.READY)
         throw new InputBlockIncompleteException("Retrieving location for LocationInput while InputSatus is " + inputStatus.name());
      return new Location(valLat.getValue(), valLong.getValue());
   }

}
