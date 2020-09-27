/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.analysis

import com.radixpro.enigma.domain.analysis.AnalyzablePoint
import com.radixpro.enigma.domain.analysis.AnalyzedAspectTransit
import com.radixpro.enigma.domain.analysis.IAnalyzedPair
import com.radixpro.enigma.domain.astronpos.IPosition
import com.radixpro.enigma.references.AspectCategory
import com.radixpro.enigma.references.AspectTypes
import java.util.*
import kotlin.math.max
import kotlin.math.min

/**
 * Finds aspects between a progressive position and a radix position.
 */
class ProgRadixAspects {
    fun findAspects(aspectTypes: List<AspectTypes>, candidateProg: IPosition, candidateRx: IPosition, orb: Double): List<IAnalyzedPair> {
        val pos1 = min(candidateProg.longitude, candidateRx.longitude)
        val pos2 = max(candidateProg.longitude, candidateRx.longitude)
        val distance1 = pos2 - pos1
        val distance2 = pos1 - pos2 + 360.0
        val aspects: MutableList<IAnalyzedPair> = ArrayList()
        for (asp in aspectTypes) {
            val angle = asp.angle
            if (asp.aspectCategory !== AspectCategory.DECLINATION) {          // TODO support parallel and contra-parallel
                var actualOrb = 360.0
                var found = false
                if (Math.abs(distance1 - angle) <= orb) {
                    actualOrb = Math.abs(distance1 - angle)
                    found = true
                } else if (Math.abs(distance2 - angle) < orb) {
                    actualOrb = Math.abs(distance2 - angle)
                    found = true
                }
                if (found) {
                    val pointTransit = AnalyzablePoint(candidateProg.chartPoint, candidateProg.longitude)
                    val pointRadix = AnalyzablePoint(candidateRx.chartPoint, candidateRx.longitude)
                    aspects.add(AnalyzedAspectTransit(pointTransit, pointRadix, asp, actualOrb, orb))
                }
            }
        }
        return aspects
    }
}