/*
 *
 *  * Jan Kampherbeek, (c) 2020.
 *  * Enigma is open source.
 *  * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.converters;

import swisseph.SwissLib;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Conversion between ecliptic and equatorial coordinates.
 */
public class EclipticEquatorialConversions {

   private final SwissLib swissLib;

   public EclipticEquatorialConversions(final SwissLib swissLib) {
      this.swissLib = checkNotNull(swissLib);
   }

   /**
    * Convert ecliptic coordinates to equatorial coordinates.
    *
    * @param eclipticValues Array with Longitude and Latitude in degrees.
    * @param obliquity      Obliquity (Epsilon) in degrees.
    * @return Array with right ascensiona nd declination in degrees.
    */
   public double[] convertToEquatorial(final double[] eclipticValues, final double obliquity) {
      checkNotNull(eclipticValues);
      var equatorialValues = new double[3];
      swissLib.swe_cotrans(eclipticValues, equatorialValues, -obliquity);  // obliquity must be negative !
      return equatorialValues;
   }

}
