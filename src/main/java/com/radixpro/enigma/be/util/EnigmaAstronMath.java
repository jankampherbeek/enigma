/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.util;

import com.radixpro.enigma.be.exceptions.EnigmaMathException;
import com.radixpro.enigma.shared.Range;

/**
 * Utility class for astronomical calculations.
 * All calculations are performed in degrees, no conversion from and to radians is required.
 * All methods are static.
 */
public class EnigmaAstronMath {


   /**
    * Calculates semi-arc for a given position in right ascension.
    * This method does not take ecliptical latitude into account, all positions are assumed to be on the ecliptic.<br/>
    * The formula used is : <i>SA = 90 + asin(sin RA . tan E . tan GL)</i><br/>
    * SA = semi-arc, RA = right ascension, E = epsilon, GL = geographic latitude.
    *
    * @param rightAscension The right ascension in degrees. If rightAsc is not in range 0 - &lt; 360 this will be corrected.
    * @param geoLatitude    The geographic latitude in decimal degrees. If gLat is not in range -90 &lt; gLat &lt; 90 an exception is thrown.
    * @param epsilon        The value for epsilon or obliquity, defining the angle between ecliptic and equator.
    * @return The semi-arc in degrees.
    */
   public static final double semiArc(final double rightAscension, final double geoLatitude, final double epsilon) throws EnigmaMathException {
      final double workRa = new Range(0, 360).checkValue(rightAscension);
      final double factorX = EnigmaMath.asin(EnigmaMath.tan(epsilon) * EnigmaMath.tan(geoLatitude));
      if (geoLatitude <= -90 || geoLatitude >= 90) throw new EnigmaMathException("Error in latitude while calculating semiArc : " + geoLatitude);
      return 90 + EnigmaMath.asin(EnigmaMath.sin(workRa) * EnigmaMath.sin(factorX));
   }


   /**
    * Calculates longitude of ascendant for a given right ascension of the MC.
    *
    * @param raMc   Right Ascension of the MC.
    * @param geoLat Geographic latitude.
    * @param eps    Obliquity.
    * @return calculated longitude.
    */
   public static double ascFromRamc(double raMc, double geoLat, double eps) {
      double obliqueAscensionAsc = new Range(0, 360).checkValue(raMc + 90);
      double sinOA = EnigmaMath.sin(obliqueAscensionAsc);
      double cosOA = EnigmaMath.cos(obliqueAscensionAsc);
      double cosE = EnigmaMath.cos(eps);
      double tanE = EnigmaMath.tan(eps);
      double tanGLat = EnigmaMath.tan(geoLat);
      double lengthOfAscendant = EnigmaMath.atan2(sinOA, (cosOA - (tanE * tanGLat)) * cosE);
      return new Range(0, 360).checkValue(lengthOfAscendant);
   }


}
