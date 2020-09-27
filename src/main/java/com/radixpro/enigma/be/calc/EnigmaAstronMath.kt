/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.calc

import com.radixpro.enigma.be.calc.EnigmaMath.asin
import com.radixpro.enigma.be.calc.EnigmaMath.atan2
import com.radixpro.enigma.be.calc.EnigmaMath.cos
import com.radixpro.enigma.be.calc.EnigmaMath.sin
import com.radixpro.enigma.be.calc.EnigmaMath.tan
import com.radixpro.enigma.be.util.Range
import com.radixpro.enigma.shared.exceptions.EnigmaMathException

/**
 * Utility class for astronomical calculations.
 * All calculations are performed in degrees, no conversion from and to radians is required.
 * All methods are static.
 */
object EnigmaAstronMath {
    /**
     * Calculates semi-arc for a given position in right ascension.
     * This method does not take ecliptical latitude into account, all positions are assumed to be on the ecliptic.<br></br>
     * The formula used is : *SA = 90 + asin(sin RA . tan E . tan GL)*<br></br>
     * SA = semi-arc, RA = right ascension, E = epsilon, GL = geographic latitude.
     *
     * @param rightAscension The right ascension in degrees. If rightAsc is not in range 0 - &lt; 360 this will be corrected.
     * @param geoLatitude    The geographic latitude in decimal degrees. If gLat is not in range -90 &lt; gLat &lt; 90 an exception is thrown.
     * @param epsilon        The value for epsilon or obliquity, defining the angle between ecliptic and equator.
     * @return The semi-arc in degrees.
     */
    @Throws(EnigmaMathException::class)
    fun semiArc(rightAscension: Double, geoLatitude: Double, epsilon: Double): Double {
        val workRa = Range.checkValue(rightAscension)
        val factorX = asin(tan(epsilon) * tan(geoLatitude))
        if (geoLatitude <= -90 || geoLatitude >= 90) throw EnigmaMathException("Error in latitude while calculating semiArc : $geoLatitude")
        return 90 + asin(sin(workRa) * sin(factorX))
    }

    /**
     * Calculates longitude of ascendant for a given right ascension of the MC.
     *
     * @param raMc   Right Ascension of the MC.
     * @param geoLat Geographic latitude.
     * @param eps    Obliquity.
     * @return calculated longitude.
     */
    fun ascFromRamc(raMc: Double, geoLat: Double, eps: Double): Double {
        val obliqueAscensionAsc = Range.checkValue(raMc + 90)
        val sinOA = sin(obliqueAscensionAsc)
        val cosOA = cos(obliqueAscensionAsc)
        val cosE = cos(eps)
        val tanE = tan(eps)
        val tanGLat = tan(geoLat)
        val lengthOfAscendant = atan2(sinOA, (cosOA - tanE * tanGLat) * cosE)
        return Range.checkValue(lengthOfAscendant)
    }
}