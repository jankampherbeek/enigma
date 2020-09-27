/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.calc


import com.radixpro.enigma.domain.astronpos.CoordinateSet
import com.radixpro.enigma.domain.input.Location
import com.radixpro.enigma.shared.FailFastHandler
import org.apache.log4j.Logger

/**
 * Calculates a CoordinateSet, a set of two coordinates.
 */
class CoordSetForDateTimeCalc {

    /**
     * Calculate two coordinates (longitude/latitude or right ascension/decliantion.).
     *
     * @param jdUt     Julian day number for UT.
     * @param id       id for the celestial body as used by the SE.
     * @param flags    combination of flags that incidcates the preferences. The flags also define the choice for eclitpical or equatorial.
     * @param location Location. Is only used for parallax. PRE: not null.
     * @return the calculated coordinates.
     */
    fun calcSet(jdUt: Double,
                id: Int,
                flags: Int,
                location: Location): CoordinateSet {
        val resultSet = SeFrontend.getPositionsForCelBody(jdUt, id, flags, location)
        if (resultSet.errorMsg.isNotEmpty()) {
            LOG.error("Error while calculating position for point : $id using flags : $flags and jdUt : $jdUt")
            FailFastHandler().terminate("Received error msg from SE : " + resultSet.errorMsg)
        }
        return CoordinateSet(resultSet.allPositions[0], resultSet.allPositions[1])
    }

    companion object {
        private val LOG = Logger.getLogger(CoordSetForDateTimeCalc::class.java)
    }


}