/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.handlers

import com.radixpro.enigma.be.analysis.MidpointsForRadix
import com.radixpro.enigma.domain.analysis.AnalyzablePoint
import com.radixpro.enigma.domain.analysis.IAnalyzedPair
import com.radixpro.enigma.domain.astronpos.IPosition
import java.util.*

class MidpointsHandler(private val analyzer: MidpointsForRadix) {
    /**
     * Find active midpoints.
     *
     * @param celBodies     Celestial bodies to analyze. PRE: size >= 2.
     * @param mundaneValues MundaneValues, uses MC and Asc.
     * @return actual midpoints.
     */
    fun retrieveMidpoints(celBodies: List<IPosition>,
                          mundaneValues: List<IPosition>): List<IAnalyzedPair> {
        require(2 <= celBodies.size)
        val candidates = createCandidates(celBodies, mundaneValues)
        return analyzer.analyze(candidates)
    }

    private fun createCandidates(celBodies: List<IPosition>,
                                 mundaneValues: List<IPosition>): List<AnalyzablePoint> {
        val candidates: MutableList<AnalyzablePoint> = ArrayList()
        celBodies.forEach { pos -> candidates.add(AnalyzablePoint(pos.chartPoint, pos.longitude)) }
        mundaneValues.forEach { pos -> candidates.add(AnalyzablePoint(pos.chartPoint, pos.longitude)) }
        return candidates
    }
}