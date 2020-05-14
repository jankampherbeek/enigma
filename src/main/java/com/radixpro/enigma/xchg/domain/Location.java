/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Geographic location. Is part of the persisted data for a chart.
 */
public class Location implements Serializable {

   private final GeographicCoordinate longInput;
   private final GeographicCoordinate latInput;
   private final String name;
   private final String formattedLocation;
   private double geoLat;
   private double geoLong;

   /**
    * Constructor defines all fields.
    *
    * @param longInput geographic longitude.
    * @param latInput  geographic latitude.
    * @param name      Name of location.
    */
   public Location(final GeographicCoordinate longInput, final GeographicCoordinate latInput, final String name) {
      this.longInput = checkNotNull(longInput);
      this.latInput = checkNotNull(latInput);
      this.name = checkNotNull(name);
      this.geoLat = latInput.getValue();
      this.geoLong = longInput.getValue();
      formattedLocation = formatLocation();

   }

   public double getGeoLat() {
      return geoLat;
   }

   public double getGeoLong() {
      return geoLong;
   }

   public GeographicCoordinate getLongInput() {
      return longInput;
   }

   public GeographicCoordinate getLatInput() {
      return latInput;
   }

   public String getName() {
      return name;
   }

   public String getFormattedLocation() {
      return formattedLocation;
   }

   private String formatLocation() {
      return name + " / " +
            String.format("%d:%d:%d ", longInput.getDegrees(), longInput.getMinutes(), longInput.getSeconds()) +
            longInput.getDirection() + " / " +
            String.format("%d:%d:%d ", latInput.getDegrees(), latInput.getMinutes(), latInput.getSeconds()) +
            latInput.getDirection();
   }
}
