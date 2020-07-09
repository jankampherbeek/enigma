/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.calc.assist;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Container for the result of a SE calculation for celestial bodies.
 */
public class SePositionResultCelObjects {

   private final double[] allPositions;
   private final String errorMsg;


   /**
    * Container for the positions of a celestial object.
    *
    * @param allPositions Array with the following values from 0..5 : main position, deviation, distance,
    *                     speed of main position, speed of deviation, speed of distance.
    * @param errorMsg     Error message or "OK".
    */
   public SePositionResultCelObjects(final double[] allPositions, final String errorMsg) {
      this.allPositions = checkNotNull(allPositions);
      this.errorMsg = checkNotNull(errorMsg);
   }

   public double[] getAllPositions() {
      return this.allPositions;
   }

   public String getErrorMsg() {
      return this.errorMsg;
   }
}
