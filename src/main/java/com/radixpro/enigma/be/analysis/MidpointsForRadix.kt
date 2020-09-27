/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.analysis

import com.google.common.base.Preconditions
import com.radixpro.enigma.domain.analysis.AnalyzablePoint
import com.radixpro.enigma.domain.analysis.AnalyzedMidpoint
import com.radixpro.enigma.domain.analysis.IAnalyzedPair
import com.radixpro.enigma.references.MidpointTypes
import java.util.*
import kotlin.math.abs

class MidpointsForRadix {
    private var results: MutableList<IAnalyzedPair> = ArrayList()

    fun analyze(candidates: List<AnalyzablePoint>): List<IAnalyzedPair> {
        Preconditions.checkArgument(1 < candidates.size)
        return performAnalysis(candidates)
    }

    private fun performAnalysis(candidates: List<AnalyzablePoint>): List<IAnalyzedPair> {
        results = ArrayList()
        val nrOfCandidates = candidates.size
        for (i in 0 until nrOfCandidates) {
            for (j in i + 1 until nrOfCandidates) {
                val candidateFirst = candidates[i]
                val candidateSecond = candidates[j]
                checkCandidates(candidates, candidateFirst, candidateSecond)
            }
        }
        return results
    }

    private fun checkCandidates(candidates: List<AnalyzablePoint>, candidateFirst: AnalyzablePoint, candidateSecond: AnalyzablePoint) {
        for (centerCandidate in candidates) {
            var matchFound = false
            for (midpointType in MidpointTypes.values()) {
                if (!matchFound) {
                    matchFound = checkForMatch(candidateFirst, candidateSecond, centerCandidate, midpointType)
                }
            }
        }
    }

    private fun checkForMatch(candidateFirst: AnalyzablePoint, candidateSecond: AnalyzablePoint, centerCandidate: AnalyzablePoint, midpointType: MidpointTypes): Boolean {
        val maxOrbis = 1.6
        val nrOfAnglesFirst: Double = ((candidateFirst.position / midpointType.angle).toInt()).toDouble()
        val nrOfAnglesSecond: Double = ((candidateSecond.position / midpointType.angle).toInt()).toDouble()
        val nrOfAnglesCenter: Double = ((centerCandidate.position / midpointType.angle).toInt()).toDouble()
        val pos1 = candidateFirst.position - nrOfAnglesFirst * midpointType.angle
        val pos2 = candidateSecond.position - nrOfAnglesSecond * midpointType.angle
        val posCenter = centerCandidate.position - nrOfAnglesCenter * midpointType.angle
        var posNorm = (pos1 + pos2) / 2.0
        if (posNorm > midpointType.angle / 2.0) posNorm -= midpointType.angle / 2.0
        var actOrbis = abs(posCenter - posNorm)
        if (actOrbis >= midpointType.angle / 4.0) actOrbis -= midpointType.angle / 2.0
        actOrbis = abs(actOrbis)
        if (actOrbis <= maxOrbis) {    // midpoint found
            results.add(AnalyzedMidpoint(candidateFirst, candidateSecond, centerCandidate, midpointType, actOrbis, maxOrbis))
            return true
        }
        return false
    }
}