/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.analysis

import com.radixpro.enigma.references.MidpointTypes

class AnalyzedMidpoint(override val first: AnalyzablePoint,
                       override val second: AnalyzablePoint,
                       val centerPoint: AnalyzablePoint,
                       val midpointType: MidpointTypes,
                       override val actualOrb: Double,
                       override val maxOrb: Double) : IAnalyzedPair {
    override val percOrb: Double

    private fun calculatePercOrb(): Double {
        return actualOrb / maxOrb * 100.0
    }

    init {
        percOrb = calculatePercOrb()
    }
}