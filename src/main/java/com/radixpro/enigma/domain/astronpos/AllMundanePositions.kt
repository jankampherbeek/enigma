/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.astronpos

/**
 * @param cusps      cusps from 1 -- number of cusps for housesystem.
 * @param specPoints specific points (Mc, Ascendant, Vertex, Eastpoint).
 */
class AllMundanePositions(val cusps: List<IPosition>, val specPoints: List<IPosition>) {
    val asc: IPosition
        get() = specPoints[0]
    val mc: IPosition
        get() = specPoints[1]
    val vertex: IPosition
        get() = specPoints[2]
    val eastPoint: IPosition
        get() = specPoints[3]
}