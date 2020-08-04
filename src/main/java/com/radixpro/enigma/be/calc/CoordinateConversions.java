/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc;

import com.radixpro.enigma.shared.Range;
import com.radixpro.enigma.xchg.domain.Location;
import swisseph.SwissLib;

import static com.google.common.base.Preconditions.checkNotNull;
import static swisseph.SweConst.SE_ECL2HOR;

/**
 * Conversion between coordinates.
 */
public final class CoordinateConversions {

   private CoordinateConversions() {
      // prevent instantiation
   }

   /**
    * Convert ecliptic coordinates to equatorial coordinates.
    *
    * @param eclipticValues Array with Longitude and Latitude in degrees.
    * @param obliquity      Obliquity (Epsilon) in degrees.
    * @return Array with right ascension and declination in degrees.
    */
   public static double[] eclipticToEquatorial(final double[] eclipticValues, final double obliquity) {
      checkNotNull(eclipticValues);
      final double[] fullEclValues = {eclipticValues[0], eclipticValues[1], 1.0};
      var equatorialValues = new double[3];
      new SwissLib().swe_cotrans(fullEclValues, equatorialValues, -obliquity);  // obliquity must be negative !
      return equatorialValues;
   }

   /**
    * Converts right ascension to longitude in the correct quadrant.<br/>
    * Uses formula: <i>tan L = tan RA / cos E</i><br/>
    * RA = right ascension, L = longitude, E = epsilon.
    *
    * @param ra  The right ascension in decimal degrees. If rightAsc is not in range 0 - &lt; 360 this will be corrected.
    * @param eps The value for epsilon or obliquity, defining the angle between ecliptic and equator.
    * @return longitude in in decimal degrees, possible values 0 - &lt; 360.
    */
   public static double equatorialToEcliptic(final double ra, final double eps) {
      final double workRA = new Range(0, 360).checkValue(ra);
      double lon = EnigmaMath.atan2(EnigmaMath.tan(workRA), EnigmaMath.cos(eps));
      lon = new Range(0, 360).checkValue(lon);
      if (workRA < 180 && lon >= 180) lon -= 180;
      if (workRA >= 180 && lon < 180) lon += 180;
      return lon;
   }


   /**
    * Calculates declination for an ecliptic point without latitude.<br/>
    * Uses the formula: sin declination = sin lon . sin eps).
    *
    * @param lon Ecliptical longitude in degrees. If longitude is not in range 0 - &lt; 360 this will be corrected.
    * @param eps The value for epsilon or obliquity, defining the angle between ecliptic and equator.
    * @return The declination in degrees.
    */
   public static double longitudeToDeclination(final double lon, final double eps) {
      final double workLongitude = new Range(0, 360).checkValue(lon);
      return EnigmaMath.asin(EnigmaMath.sin(workLongitude) * EnigmaMath.sin(eps));
   }


   /**
    * Convert to horizontal position.
    *
    * @param jdUt     Julian day number for UT.
    * @param eclCoord ecliptical coördinates: index 0 = longitude, 1 = latitude, 2 = distance. PRE: not null.
    * @param location location. PRE: not null.
    * @return array with azimuth and altitude (in that sequence).
    */
   public static double[] eclipticToHorizontal(final double jdUt, final double[] eclCoord, final Location location) {
      // TODO Release 2020.2 Check handling of sidereal positions
      return SeFrontend.getFrontend().getHorizontalPosition(jdUt, checkNotNull(eclCoord), checkNotNull(location), SE_ECL2HOR);
   }

}
