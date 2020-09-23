/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.astronpos

import com.radixpro.enigma.references.MundanePoints

/**
 * Position for a point in the mundane frame (houses etc.).
 *
 * @param chartPoint Type of mundane point (MC, Vertex,cusp etc.).
 * @param longitude    Ecliptical longitude (latitude does not apply for mundane points). PRE: 0.0 <= longitude < 360.0
 * @param eqPos        Equatorial coordinates.
 * @param horPos       Horizontal coordinates.
 */
data class MundanePosition(override val chartPoint: MundanePoints,
                           override val longitude: Double,
                           val eqPos: CoordinateSet,
                           val horPos: CoordinateSet) : IPosition {
    override val declination: Double
        get() = eqPos.deviation
}