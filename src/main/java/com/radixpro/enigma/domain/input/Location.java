/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.input;

/**
 * Simple value object for Location
 */
public class Location {

   final double geoLat;
   final double geoLon;

   public Location(final double geoLat, final double geoLon) {
      this.geoLat = geoLat;
      this.geoLon = geoLon;
   }

   public double getGeoLat() {
      return geoLat;
   }

   public double getGeoLon() {
      return geoLon;
   }
}
