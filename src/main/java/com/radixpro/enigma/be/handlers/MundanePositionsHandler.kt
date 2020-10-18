/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.handlers

import com.radixpro.enigma.be.calc.CoordinateConversions
import com.radixpro.enigma.be.calc.SeFrontend
import com.radixpro.enigma.be.calc.assist.SePositionResultHouses
import com.radixpro.enigma.domain.astronpos.AllMundanePositions
import com.radixpro.enigma.domain.astronpos.CoordinateSet
import com.radixpro.enigma.domain.astronpos.IPosition
import com.radixpro.enigma.domain.astronpos.MundanePosition
import com.radixpro.enigma.domain.input.Location
import com.radixpro.enigma.references.Ayanamshas
import com.radixpro.enigma.references.EclipticProjections
import com.radixpro.enigma.references.HouseSystems
import com.radixpro.enigma.references.MundanePointsAstron
import swisseph.SweConst
import java.util.*

/**
 * Takes care of calculating mundane positions.
 */
class MundanePositionsHandler(private val obliquityHandler: ObliquityHandler) {
    private val seFrontend: SeFrontend = SeFrontend

    /**
     * Define mundane positions.
     *
     * @param jdUt        Julian day for UT.
     * @param eclProj     Type of projection to the ecliptic.
     * @param ayanamsha   The ayanamsha to be used (could be 'NONE').
     * @param houseSystem The housystem to be used.
     * @param location    Location.
     * @return
     */
    fun definePositions(jdUt: Double, eclProj: EclipticProjections, ayanamsha: Ayanamshas,
                        houseSystem: HouseSystems, location: Location): AllMundanePositions {
        var seFlags = SweConst.SEFLG_SWIEPH
        if (eclProj === EclipticProjections.SIDEREAL) seFlags = seFlags or SweConst.SEFLG_SIDEREAL
        val seId = houseSystem.seId[0].toInt()
        val nrOfCusps = houseSystem.nrOfCusps
        val positionsForHouses = seFrontend.getPositionsForHouses(jdUt, seFlags, location, seId, nrOfCusps)
        return convert(positionsForHouses, jdUt, seId, nrOfCusps, seFlags, location, houseSystem)
    }

    private fun convert(positions: SePositionResultHouses, jdUt: Double, seId: Int, nrOfCusps: Int, seFlags: Int,
                        location: Location, houseSystem: HouseSystems): AllMundanePositions {
        val cusps = positions.cusps
        val ascMc = positions.ascMc
        val obliquity = obliquityHandler.calcTrueObliquity(jdUt)
        val allCusps: MutableList<IPosition> = ArrayList()
        for (cusp in cusps) {
            allCusps.add(createMundanePosition(cusp, obliquity, jdUt, location, MundanePointsAstron.CUSP))
        }
        val specPoints: MutableList<IPosition> = ArrayList()
        specPoints.add(createMundanePosition(ascMc[0], obliquity, jdUt, location, MundanePointsAstron.ASC))
        specPoints.add(createMundanePosition(ascMc[1], obliquity, jdUt, location, MundanePointsAstron.MC))
        // skip 3: ARMC
        specPoints.add(createMundanePosition(ascMc[3], obliquity, jdUt, location, MundanePointsAstron.VERTEX))
        specPoints.add(createMundanePosition(ascMc[4], obliquity, jdUt, location, MundanePointsAstron.EAST_POINT))
        return AllMundanePositions(allCusps, specPoints)
    }

    private fun createMundanePosition(longitude: Double, obliquity: Double, jdUt: Double, location: Location,
                                      mundanePointAstron: MundanePointsAstron): MundanePosition {
        val eclValues = doubleArrayOf(longitude, 0.0, 1.0) // longitude, latitude, distance
        val equaPositions = CoordinateConversions.eclipticToEquatorial(eclValues, obliquity) // RA, decl
        val eqPos = CoordinateSet(equaPositions[0], equaPositions[1])
        val horizontalPosition = seFrontend.getHorizontalPosition(jdUt, eclValues, location, SweConst.SE_ECL2HOR)
        val horPos = CoordinateSet(horizontalPosition[0], horizontalPosition[1]) // true altitude, index 2 = apparent altitude
        return MundanePosition(mundanePointAstron, longitude, eqPos, horPos)
    }

}