/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.calc

import com.radixpro.enigma.domain.input.Location
import com.radixpro.enigma.references.CelestialObjects
import com.radixpro.enigma.shared.exceptions.NoPositionFoundException

/**
 * Finds the Julian Day (for UT) for a given position within a time range. Should only be used if retrogradation cannot occur (Sun, Moon, Mean Node, or
 * heliocentric positions).
 */
class JdFromPosCalc(private val coordSetCalc: CoordSetForDateTimeCalc) {

    @Throws(NoPositionFoundException::class)
    fun findJd(startJd: Double,
               endJd: Double,
               position: Double,
               point: CelestialObjects,
               flags: Int,
               location: Location): Double {
        require(startJd < endJd)
        return jdForPosition(startJd, endJd, position, point, flags, location)
    }

    @Throws(NoPositionFoundException::class)
    private fun jdForPosition(startJd: Double,
                              endJd: Double,
                              position: Double,
                              point: CelestialObjects,
                              flags: Int,
                              location: Location): Double {
        var posEnd: Double
        var posCheck: Double
        var tempStart = startJd
        var tempEnd = endJd
        var tempCheck = startJd + (endJd - startJd) / 2.0
        var counter = 0
        var found = false
        while (counter < 10000 && !found) {
            counter++
            posEnd = coordSetCalc.calcSet(tempEnd, point.seId.toInt(), flags, location).mainCoord
            posCheck = coordSetCalc.calcSet(tempCheck, point.seId.toInt(), flags, location).mainCoord
            if (posEnd - posCheck > 180.0) posCheck += 180.0
            if (Math.abs(posCheck - position) < 0.000001) found = true
            if (position < posCheck) tempEnd = tempCheck else tempStart = tempCheck
            tempCheck = tempStart + (tempEnd - tempStart) / 2.0
        }
        return if (found) tempCheck else throw NoPositionFoundException("Could not find position for " + point.id)
    }
}