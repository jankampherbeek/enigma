/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.astronpos

import com.radixpro.enigma.xchg.domain.IChartPoints

/**
 * Interface for most used elements of a position.
 */
interface IPosition {
    val longitude: Double
    val declination: Double
    val chartPoint: IChartPoints
}