/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.handlers

import com.google.common.base.Preconditions
import com.radixpro.enigma.be.analysis.AspectsForRadix
import com.radixpro.enigma.domain.analysis.AnalyzablePoint
import com.radixpro.enigma.domain.analysis.IAnalyzedPair
import com.radixpro.enigma.domain.astronpos.IPosition
import com.radixpro.enigma.domain.config.AspectConfiguration
import java.util.*

class AspectsHandler(private val analyzer: AspectsForRadix) {

    fun retrieveAspects(celBodies: List<IPosition>,
                        mundaneValues: List<IPosition>,
                        config: AspectConfiguration): List<IAnalyzedPair> {
        Preconditions.checkArgument(2 <= celBodies.size)
        val candidates = createCandidates(celBodies, mundaneValues)
        return analyzer.analyze(candidates, config)
    }

    private fun createCandidates(celBodies: List<IPosition>,
                                 mundaneValues: List<IPosition>): List<AnalyzablePoint> {
        val candidates: MutableList<AnalyzablePoint> = ArrayList()
        for (pos in celBodies) {
            candidates.add(AnalyzablePoint(pos.chartPoint, pos.longitude))
        }
        for (pos in mundaneValues) {
            candidates.add(AnalyzablePoint(pos.chartPoint, pos.longitude))
        }
        return candidates
    }
}