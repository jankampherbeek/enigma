/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.handlers

import com.radixpro.enigma.be.calc.CoordinateConversions
import com.radixpro.enigma.be.calc.SeFrontend
import com.radixpro.enigma.be.calc.assist.CombinedFlags
import com.radixpro.enigma.be.calc.assist.SePositionResultCelObjects
import com.radixpro.enigma.domain.astronpos.*
import com.radixpro.enigma.domain.input.Location
import com.radixpro.enigma.domain.reqresp.IProgCalcRequest
import com.radixpro.enigma.domain.reqresp.SimpleProgResponse
import com.radixpro.enigma.references.CelestialObjects
import com.radixpro.enigma.references.SeFlags
import com.radixpro.enigma.xchg.api.settings.ICalcSettings
import java.util.*

/**
 * Handler for the calculation of progressive positions based on ephemeris calculations.
 */
class EphProgCalcHandler(val seFrontend: SeFrontend) {


    fun retrievePositions(request: IProgCalcRequest): SimpleProgResponse {
        val posVos = calculatePositions(request.dateTime.jd, request.location, request.settings)
        return SimpleProgResponse(posVos, request)
    }

    private fun calculatePositions(jdUt: Double,
                                   location: Location?,
                                   settings: ICalcSettings): List<IPosition> {
        val ayanamsha = settings.ayamsha
        val sidereal = settings.isSidereal
        val flagListEcl: MutableList<SeFlags> = ArrayList()
        val flagListEq: MutableList<SeFlags> = ArrayList()
        flagListEcl.add(SeFlags.SWISSEPH)
        flagListEq.add(SeFlags.SWISSEPH)
        flagListEq.add(SeFlags.EQUATORIAL)
        if (sidereal) {
            flagListEcl.add(SeFlags.SIDEREAL)
            flagListEq.add(SeFlags.SIDEREAL)
        }
        // TODO handle topocentric and sidereal
        val eclFlags = CombinedFlags().getCombinedValue(flagListEcl).toInt()
        val eqFlags = CombinedFlags().getCombinedValue(flagListEq).toInt()
        val posResults: MutableList<IPosition> = ArrayList()
        val points = settings.points
        for (point in points) {
            val seId = (point as CelestialObjects).seId.toInt()
            val posEcl = seFrontend.getPositionsForCelBody(jdUt, seId, eclFlags, location!!)
            val posEq = seFrontend.getPositionsForCelBody(jdUt, seId, eqFlags, location)
            val coordSet = doubleArrayOf(posEcl.allPositions[0], posEcl.allPositions[1], posEcl.allPositions[2])
            val horCoordinates = CoordinateConversions.eclipticToHorizontal(jdUt, coordSet, location)
            val fullHorCoordinates = CoordinateSet(horCoordinates[0], horCoordinates[1])
            val fullEclCoordinates = createFullPointCoordinate(posEcl)
            val fullEqCoordinates = createFullPointCoordinate(posEq)
            posResults.add(FullPointPosition(point, fullEclCoordinates, fullEqCoordinates, fullHorCoordinates))
        }
        return posResults
    }

    private fun createFullPointCoordinate(sePos: SePositionResultCelObjects): FullPointCoordinate {
        val posCoordinates = CoordinateSet3D(sePos.allPositions[0], sePos.allPositions[1], sePos.allPositions[2])
        val speedCoordinates = CoordinateSet3D(sePos.allPositions[3], sePos.allPositions[4], sePos.allPositions[5])
        return FullPointCoordinate(posCoordinates, speedCoordinates)
    }

}