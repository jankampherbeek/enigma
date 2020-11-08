/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.be.calc.SeFrontend
import com.radixpro.enigma.domain.input.Location
import com.radixpro.enigma.references.CelestialObjects
import com.radixpro.enigma.references.MundanePointsAstron

interface StatsCalculator {
    fun celObject(celObject: CelestialObjects, jdUt: Double, flags: Int, location: Location): Double
    fun mundanePoint(mundPoint: MundanePointsAstron, jdUt: Double, flags: Int, location: Location, systemId: Int, nrOfCusps: Int): Double
}

class PointsCalculator(val se: SeFrontend) : StatsCalculator {

    override fun celObject(celObject: CelestialObjects, jdUt: Double, flags: Int, location: Location): Double {
        return se.getPositionsForCelBody(jdUt, celObject.seId.toInt(), flags, location).allPositions[0]
    }

    override fun mundanePoint(mundPoint: MundanePointsAstron, jdUt: Double, flags: Int, location: Location, systemId: Int, nrOfCusps: Int): Double {
        val positionsForHouses = se.getPositionsForHouses(jdUt, flags, location, systemId, nrOfCusps)
        val index = mundPoint.id - 1
        return if (index < 6) positionsForHouses.ascMc[index]
        else 0.0;
    }

}