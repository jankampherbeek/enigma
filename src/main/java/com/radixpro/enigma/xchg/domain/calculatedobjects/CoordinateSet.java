/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.calculatedobjects;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Positions for main circle and the deviation from the main circle. Typically longitude and latitude, right ascensiona nd declination or azimuth and altitude.
 */
public class CoordinateSet {

   private final double mainPos;
   private final double deviation;

   /**
    * Constructor defines all properties.
    *
    * @param mainPos   Position at main circle. PRE: 0.0 <= mainPos < 360.0
    * @param deviation Deviation from main circle. PRE: -90.0 <= deviation <= 90.0
    */
   public CoordinateSet(final double mainPos, final double deviation) {
      checkArgument(mainPos >= 0.0 && mainPos < 360.0);
      checkArgument(deviation >= -90.0 && deviation <= 90.0);
      this.mainPos = mainPos;
      this.deviation = deviation;
   }

   public double getMainPos() {
      return mainPos;
   }

   public double getDeviation() {
      return deviation;
   }

}
