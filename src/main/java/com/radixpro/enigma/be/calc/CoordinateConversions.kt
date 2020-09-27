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
import com.radixpro.enigma.be.calc.SeFrontend.getHorizontalPosition
import com.radixpro.enigma.be.util.Range
import com.radixpro.enigma.domain.input.Location
import swisseph.SweConst
import swisseph.SwissLib

object CoordinateConversions {
    /**
     * Convert ecliptic coordinates to equatorial coordinates.
     *
     * @param eclipticValues Array with Longitude and Latitude in degrees.
     * @param obliquity      Obliquity (Epsilon) in degrees.
     * @return Array with right ascension and declination in degrees.
     */
    fun eclipticToEquatorial(eclipticValues: DoubleArray,
                             obliquity: Double): DoubleArray {
        val fullEclValues = doubleArrayOf(eclipticValues[0], eclipticValues[1], 1.0)
        val equatorialValues = DoubleArray(3)
        SwissLib().swe_cotrans(fullEclValues, equatorialValues, -obliquity) // obliquity must be negative !
        return equatorialValues
    }

    /**
     * Converts right ascension to longitude in the correct quadrant.<br></br>
     * Uses formula: tan L = tan RA / cos E<br></br>
     * RA = right ascension, L = longitude, E = epsilon.
     *
     * @param ra  The right ascension in decimal degrees. If rightAsc is not in range 0 - &lt; 360 this will be corrected.
     * @param eps The value for epsilon or obliquity, defining the angle between ecliptic and equator.
     * @return longitude in in decimal degrees, possible values 0 - &lt; 360.
     */
    fun equatorialToEcliptic(ra: Double, eps: Double): Double {
        val workRA = Range.checkValue(ra)
        var lon = atan2(tan(workRA), cos(eps))
        lon = Range.checkValue(lon)
        if (workRA < 180 && lon >= 180) lon -= 180.0
        if (workRA >= 180 && lon < 180) lon += 180.0
        return lon
    }

    /**
     * Calculates declination for an ecliptic point without latitude.<br></br>
     * Uses the formula: sin declination = sin lon . sin eps).
     *
     * @param lon Ecliptical longitude in degrees. If longitude is not in range 0 - &lt; 360 this will be corrected.
     * @param eps The value for epsilon or obliquity, defining the angle between ecliptic and equator.
     * @return The declination in degrees.
     */
    fun longitudeToDeclination(lon: Double, eps: Double): Double {
        val workLongitude = Range.checkValue(lon)
        return asin(sin(workLongitude) * sin(eps))
    }

    /**
     * Convert to horizontal position.
     *
     * @param jdUt     Julian day number for UT.
     * @param eclCoord ecliptical coördinates: index 0 = longitude, 1 = latitude, 2 = distance.
     * @param location location.
     * @return array with azimuth and altitude (in that sequence).
     */
    @JvmStatic
    fun eclipticToHorizontal(jdUt: Double, eclCoord: DoubleArray?, location: Location): DoubleArray {
        // TODO Release 2020.2 Check handling of sidereal positions
        return getHorizontalPosition(jdUt, eclCoord!!, location, SweConst.SE_ECL2HOR)
    }
}