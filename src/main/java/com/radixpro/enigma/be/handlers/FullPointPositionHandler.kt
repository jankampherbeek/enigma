/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.handlers

import com.radixpro.enigma.be.calc.SeFrontend
import com.radixpro.enigma.be.calc.assist.SePositionResultCelObjects
import com.radixpro.enigma.domain.astronpos.CoordinateSet
import com.radixpro.enigma.domain.astronpos.CoordinateSet3D
import com.radixpro.enigma.domain.astronpos.FullPointCoordinate
import com.radixpro.enigma.domain.astronpos.FullPointPosition
import com.radixpro.enigma.domain.input.Location
import com.radixpro.enigma.references.Ayanamshas
import com.radixpro.enigma.references.CelestialObjects
import com.radixpro.enigma.references.EclipticProjections
import com.radixpro.enigma.references.ObserverPositions
import com.radixpro.enigma.shared.FailFastHandler
import com.radixpro.enigma.xchg.domain.IChartPoints
import swisseph.SweConst


class FullPointPositionHandler {
    fun definePosition(celObject: IChartPoints, jdUt: Double, obsPos: ObserverPositions,
                       eclProj: EclipticProjections, ayanamsha: Ayanamshas, location: Location): FullPointPosition {
        val seId = (celObject as CelestialObjects).seId.toInt()
        var seFlags = SweConst.SEFLG_SWIEPH or SweConst.SEFLG_SPEED
        if (obsPos === ObserverPositions.TOPOCENTRIC) seFlags = seFlags or SweConst.SEFLG_TOPOCTR
        // TODO release 2020.2: check for heliocentric
        if (eclProj === EclipticProjections.SIDEREAL) seFlags = seFlags or SweConst.SEFLG_SIDEREAL
        // TODO release 2020.2: include ayanamsha and support for sidereal
        val positionResultEcl = SeFrontend.getPositionsForCelBody(jdUt, seId, seFlags, location)
        val eclPos = convert(positionResultEcl)
        seFlags = seFlags or SweConst.SEFLG_EQUATORIAL
        val positionResultEq = SeFrontend.getPositionsForCelBody(jdUt, seId, seFlags, location)
        val eqPos = convert(positionResultEq)
        val coords = doubleArrayOf(eclPos.position.mainCoord, eclPos.position.deviation, 1.0)
        val positionsHor = SeFrontend.getHorizontalPosition(jdUt, coords, location, seFlags)
        val horPos = convert(positionsHor)
        return FullPointPosition(celObject, eclPos, eqPos, horPos)
    }

    private fun convert(positionResult: SePositionResultCelObjects): FullPointCoordinate {
        if (!positionResult.errorMsg.isEmpty()) FailFastHandler().terminate("Error when calculating cel point. " +
                positionResult.errorMsg)
        val allPositions = positionResult.allPositions
        val mainPos = allPositions[0]
        val devPos = allPositions[1]
        val distPos = allPositions[2]
        val mainSpeed = allPositions[3]
        val devSpeed = allPositions[4]
        val distSpeed = allPositions[5]
        val position = CoordinateSet3D(mainPos, devPos, distPos)
        val speed = CoordinateSet3D(mainSpeed, devSpeed, distSpeed)
        return FullPointCoordinate(position, speed)
    }

    private fun convert(positions: DoubleArray): CoordinateSet {
        return CoordinateSet(positions[0], positions[1])
    }

}