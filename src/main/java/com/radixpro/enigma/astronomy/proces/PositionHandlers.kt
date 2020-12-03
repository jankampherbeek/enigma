/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.astronomy.proces

import com.radixpro.enigma.be.calc.CoordinateConversions
import com.radixpro.enigma.be.calc.SeFrontend
import com.radixpro.enigma.be.handlers.ObliquityHandler
import com.radixpro.enigma.domain.input.Location
import com.radixpro.enigma.references.CelestialObjects
import com.radixpro.enigma.references.MundanePointsAstron
import com.radixpro.enigma.share.exceptions.ItemNotFoundException
import com.radixpro.enigma.xchg.domain.IChartPoints

/**
 * Calculates positions for Celestial Objects and for the Mundane Points MC, ASC, Vertex, Eastpoint. Other cusps are not supporterd.
 */
class GeneralPositionHandler() {

    /**
     * Calculates longitude and no other values.
     */
    fun calcLongitude(point: IChartPoints, jdUt: Double, flags: Int, location: Location): Double {
        when (point) {
            is CelestialObjects -> return calcCelObject(point, jdUt, flags, location)[0]
            is MundanePointsAstron -> return calcMundPoint(point, jdUt, flags, location)
        }
        throw ItemNotFoundException("Could not find CelestialObjects or MundanePointsAstron for point in GeneralPositionHandler.calculateLOngitude.")
    }


    fun calcDeclination(point: IChartPoints, jdUt: Double, flags: Int, location: Location): Double {
        when (point) {
            is CelestialObjects -> return calcDeclCelObject(point, jdUt, flags, location)
            is MundanePointsAstron -> return calcDeclMundPoint(point, jdUt, flags, location)
        }
        throw ItemNotFoundException("Could not find CelestialObjects or MundanePointsAstron for point in GeneralPositionHandler.calculateLOngitude.")
    }

    private fun calcCelObject(point: CelestialObjects, jdUt: Double, flags: Int, location: Location): DoubleArray {
        val positions = SeFrontend.getPositionsForCelBody(jdUt, point.seId.toInt(), flags, location)
        return positions.allPositions
    }

    private fun calcMundPoint(point: MundanePointsAstron, jdUt: Double, flags: Int, location: Location): Double {
        val houseSystemId = 'W'.toInt()    // Use Whole Sign Houses to enable calculation, ignore the results.
        val nrOfCusps = 12
        val positions = SeFrontend.getPositionsForHouses(jdUt, flags.toInt(), location, houseSystemId, nrOfCusps)

        return when (point) {
            MundanePointsAstron.ASC -> positions.ascMc[0]
            MundanePointsAstron.MC -> positions.ascMc[1]
            MundanePointsAstron.VERTEX -> positions.ascMc[3]
            else -> {
                throw ItemNotFoundException("Expected ASC, MC, or VERTEX but found ${point.name} in GeneralPositionHandler.calculateMundPoint().")
            }
        }
    }

    private fun calcDeclCelObject(point: CelestialObjects, jdUt: Double, flags: Int, location: Location): Double {
        val obliquity = ObliquityHandler().calcTrueObliquity(jdUt)
        val eclPositions = calcCelObject(point, jdUt, flags, location)
        return CoordinateConversions.eclipticToEquatorial(eclPositions, obliquity)[1]
    }

    private fun calcDeclMundPoint(point: MundanePointsAstron, jdUt: Double, flags: Int, location: Location): Double {
        val obliquity = ObliquityHandler().calcTrueObliquity(jdUt)
        val lonLat = DoubleArray(2)
        lonLat[0] = calcMundPoint(point, jdUt, flags, location)
        lonLat[1] = 0.0
        return CoordinateConversions.eclipticToEquatorial(lonLat, obliquity)[1]
    }


}