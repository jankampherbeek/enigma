/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.astronpos

import com.radixpro.enigma.xchg.domain.IChartPoints

/**
 * Full set of positions with all coordinates for a specific celestial object.
 *
 * @param chartPoint definition of celestial object.
 * @param eclPos    ecliptical position.
 * @param eqPos     equatorial positon.
 * @param horPos    horizontal position.
 */
class FullPointPosition(override val chartPoint: IChartPoints,
                        val eclPos: FullPointCoordinate,
                        val eqPos: FullPointCoordinate,
                        val horPos: CoordinateSet) : IPosition {
    override val longitude: Double
        get() = eclPos.position.mainCoord
    override val declination: Double
        get() = eqPos.position.deviation
}