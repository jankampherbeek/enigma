/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.calc

import com.radixpro.enigma.be.calc.assist.SePositionResultCelObjects
import com.radixpro.enigma.be.calc.assist.SePositionResultHouses
import com.radixpro.enigma.domain.input.Location
import com.radixpro.enigma.references.HouseSystems
import com.radixpro.enigma.references.SeFlags
import com.radixpro.enigma.shared.common.EnigmaDictionary.SE_LOCATION
import org.apache.log4j.Logger
import swisseph.SweDate
import swisseph.SwissEph

/**
 * Simple wrapper to access the Java port to the SE by Thomas Mack.
 * Implemented as a singleton to prevent multiple instantiations.
 */
object SeFrontend {

    private val LOG = Logger.getLogger(SeFrontend::class.java)
    private const val PATH: String = SE_LOCATION
    private val swissEph = SwissEph(PATH)

    /**
     * Calculate ecliptical or equatorial positions for a body
     *
     * @param jdUt  Julian Day based on Ephemeris Time
     * @param id    indicates the body
     * @param flags combined settings for the SE
     * @return calculated positions. Array contains for ecliptical positions: from 0..5: Longitude, latitude, distance in AU, speed long, speed lat, speed dist,
     * and for equatorial positions from 0..5: right ascension, declination, distance in AU, speed RA, speed decl, speed dist.
     */
    fun getPositionsForCelBody(jdUt: Double,
                               id: Int,
                               flags: Int,
                               location: Location): SePositionResultCelObjects {
        val allPositions = DoubleArray(6)
        val errorMsg = StringBuffer()                  // StringBuilder not possible because Java Port to the SE uses a StringBuffer.
        if (flags == (flags or SeFlags.TOPOCENTRIC.seValue.toInt())) {
            swissEph.swe_set_topo(location.geoLon, location.geoLat, 0.0)
        }
        swissEph.swe_calc_ut(jdUt, id, flags, allPositions, errorMsg)
        return SePositionResultCelObjects(allPositions, errorMsg.toString())
    }

    /**
     * Calculate epsilon
     *
     * @param jdUt  Julian Day based on Ephemeris Time
     * @param id    indicates the body
     * @param flags combined settings for the SE
     * @return calculated positions
     */
    fun getPositionsForEpsilon(jdUt: Double,
                               id: Int,
                               flags: Int): SePositionResultCelObjects {
        val allPositions = DoubleArray(6)
        val errorMsg = StringBuffer()                   // StringBuilder not possible because Java Port to the SE uses a StringBuffer.
        swissEph.swe_calc_ut(jdUt, id, flags, allPositions, errorMsg)
        return SePositionResultCelObjects(allPositions, errorMsg.toString())
    }

    /**
     * Calculate horizontal positions for a body
     *
     * @param jdUt     Julian day based on ephemeris time
     * @param eclCoord ecliptical coördinates: index 0 = longitude, 1 = latitude, 2 = distance
     * @param location geographic latitude and longitude
     * @param flags    combined settings for the SE
     * @return calculated positions
     */
    fun getHorizontalPosition(jdUt: Double,
                              eclCoord: DoubleArray,
                              location: Location,
                              flags: Int): DoubleArray {
        require(3 == eclCoord.size)
        val geoPos = doubleArrayOf(location.geoLon, location.geoLat, 0.0)
        val eclPos = doubleArrayOf(eclCoord[0], eclCoord[1], eclCoord[2])
        val atPress = 0.0
        val atTemp = 0.0
        val azAlt = DoubleArray(3)
        swissEph.swe_azalt(jdUt, flags, geoPos, atPress, atTemp, eclPos, azAlt)
        return azAlt
    }

    /**
     * Calculate positions for houses
     *
     * @param jdUt      Julian Day based on Universal Time
     * @param flags     combined settings for the SE
     * @param location  geographic latitude and longitude
     * @param system    the housesystem to use
     * @param nrOfCusps number of cusps for the current housesystem
     * @return calculated positions
     */
    fun getPositionsForHouses(jdUt: Double,
                              flags: Int,
                              location: Location,
                              system: Int,
                              nrOfCusps: Int): SePositionResultHouses {
        val cusps = DoubleArray(nrOfCusps + 1)
        val ascMc = DoubleArray(10)
        val tempCusps = DoubleArray(100)
        swissEph.swe_houses(jdUt, flags, location.geoLat, location.geoLon, system, tempCusps, ascMc)
        if (nrOfCusps >= 0) System.arraycopy(tempCusps, 1, cusps, 1, nrOfCusps)
        return SePositionResultHouses(ascMc, cusps)
    }

    /**
     * Relative position in a house.
     * Returns a Double where the integer part indicates the house and the floating point part the relative distnace from the cusp.
     * E.g. 6.33 means sixth house and 33% separated from the cusp.
     */
    fun getPositionInHouse(armc: Double,
                           geoLat: Double,
                           eps: Double,
                           hsys: Int,
                           lonLat: DoubleArray,
                           error: StringBuffer): Double {
        return swissEph.swe_house_pos(armc, geoLat, eps, hsys, lonLat, error)
    }

    /**
     * Calculates the ascendant from the ARMC.
     *
     * @param armc   Value for ARMC. PRE: 0.0 <= armc < 360.0
     * @param geoLat Geographic latitude. PRE: -90.0 < geoLat < 90.0
     * @param eps    Obliquity.
     * @return Longitude of ascendant.
     */
    fun ascFromMc(armc: Double,
                  geoLat: Double,
                  eps: Double): Double {
        require(0.0 <= armc && armc < 360.0)
        require(-90.0 < geoLat && geoLat < 90.0)
        val cusps = DoubleArray(13)
        val ascMc = DoubleArray(10)
        val hSys = HouseSystems.WHOLESIGN.seId[0].toInt()
        swissEph.swe_houses_armc(armc, geoLat, eps, hSys, cusps, ascMc)
        return ascMc[0]
    }

    /**
     * Checks if a date is valid.
     *
     * @param year      The year of the date.
     * @param month     The month of the date.
     * @param day       The day of the date.
     * @param gregorian True if gregorian calendar, otherwise false.
     * @return True if date is valid, otherwise false.
     */
    fun isValidDate(year: Int,
                    month: Int,
                    day: Int,
                    gregorian: Boolean): Boolean {
        val sweDate1 = SweDate(year, month, day, 0.0, gregorian)
        val calculatedJulDay = sweDate1.julDay
        val sweDate2 = SweDate(calculatedJulDay, gregorian)
        return sweDate1.year == sweDate2.year && sweDate1.month == sweDate2.month && sweDate1.day == sweDate2.day
    }

}