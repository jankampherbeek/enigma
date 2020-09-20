/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.analysis

import com.radixpro.enigma.references.AspectTypes

/**
 * Actual aspect between a transit position and a radix postion.
 */
class AnalyzedAspectTransit(override val first: AnalyzablePoint,
                            override val second: AnalyzablePoint,
                            val aspectType: AspectTypes,
                            override val actualOrb: Double,
                            override val maxOrb: Double) : IAnalyzedPair {
    override val percOrb: Double = actualOrb / maxOrb * 100.0

}