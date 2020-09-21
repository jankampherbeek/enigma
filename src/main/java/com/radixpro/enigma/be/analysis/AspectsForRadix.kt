/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.analysis

import com.radixpro.enigma.domain.analysis.AnalyzablePoint
import com.radixpro.enigma.domain.analysis.AnalyzedAspect
import com.radixpro.enigma.domain.analysis.IAnalyzedPair
import com.radixpro.enigma.domain.config.AspectConfiguration
import com.radixpro.enigma.references.AspectCategory
import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class AspectsForRadix {
    /**
     * Calculate aspects between multiple points.
     *
     * @param candidates The points where aspects will be defined for. PRE: not null, size > 1.
     * @param config     Configuration for aspects. PRE: not null.
     * @return the calculated aspects.
     */
    fun analyze(candidates: List<AnalyzablePoint>,
                config: AspectConfiguration): List<IAnalyzedPair> {
        require(1 < candidates.size)
        return performAnalysis(candidates, config)
    }

    private fun performAnalysis(candidates: List<AnalyzablePoint>,
                                config: AspectConfiguration): List<IAnalyzedPair> {
        val results: MutableList<IAnalyzedPair> = ArrayList()
        val nrOfCandidates = candidates.size
        for (i in 0 until nrOfCandidates) {
            for (j in i + 1 until nrOfCandidates) {
                val candidateFirst = candidates[i]
                val candidateSecond = candidates[j]
                val pos1 = min(candidateFirst.position, candidateSecond.position)
                val pos2 = max(candidateFirst.position, candidateSecond.position)
                val distance1 = pos2 - pos1
                val distance2 = pos1 - pos2 + 360.0
                checkCandidates(config, results, candidateFirst, candidateSecond, distance1, distance2)
            }
        }
        return results
    }

    private fun checkCandidates(config: AspectConfiguration, results: MutableList<IAnalyzedPair>,
                                candidateFirst: AnalyzablePoint, candidateSecond: AnalyzablePoint,
                                distance1: Double, distance2: Double) {
        for (configAspect in config.aspects) {
            if (configAspect.aspect.aspectCategory !== AspectCategory.DECLINATION) {      // TODO support parallel and contra-parallel
                val effectiveMaxOrb = configAspect.orbPercentage * config.baseOrb / 100.0
                val angle = configAspect.aspect.angle
                var actualOrb = 0.0
                var found = false
                if (abs(distance1 - angle) <= effectiveMaxOrb) {
                    actualOrb = abs(distance1 - angle)
                    found = true
                } else if (abs(distance2 - angle) < effectiveMaxOrb) {
                    actualOrb = abs(distance2 - angle)
                    found = true
                }
                if (found) results.add(AnalyzedAspect(candidateFirst, candidateSecond, configAspect.aspect, actualOrb, effectiveMaxOrb))
            }
        }
    }
}