/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.astrondata;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Coordinates excluding distance.
 */
public class CoordinateSet {

   private final double mainCoord;
   private final double deviation;

   /**
    * Constructor defines all properties.
    *
    * @param mainCoord main coordinate (e.g. longitude, ra or azimuth). PRE: 0.0 <= mainCoord < 360.0
    * @param deviation deviation (e.g. latitude, declination or altitude). PRE: -90.0 <= deviation <= 90.0
    */
   public CoordinateSet(final double mainCoord, final double deviation) {
      checkArgument(0.0 <= mainCoord && mainCoord < 360.0);
      checkArgument(-90.0 <= deviation && deviation <= 90.0);
      this.mainCoord = mainCoord;
      this.deviation = deviation;
   }

   public double getMainCoord() {
      return mainCoord;
   }

   public double getDeviation() {
      return deviation;
   }


}
