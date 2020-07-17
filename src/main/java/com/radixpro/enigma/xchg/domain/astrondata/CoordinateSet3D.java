/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.astrondata;

/**
 * Coordinates including distance.
 */
public class CoordinateSet3D extends CoordinateSet {

   private final double distance;

   /**
    * Constructor defines all properties.
    *
    * @param mainCoord main coordinate (e.g. longitude, ra or azimuth). PRE: 0.0 <= mainCoord < 360.0
    * @param deviation deviation (e.g. latitude, declination or altitude). PRE: -90.0 <= deviation <= 90.0
    * @param distance  distance in AU.
    */
   public CoordinateSet3D(double mainCoord, double deviation, double distance) {
      super(mainCoord, deviation);
      this.distance = distance;
   }

   public double getDistance() {
      return distance;
   }
}
