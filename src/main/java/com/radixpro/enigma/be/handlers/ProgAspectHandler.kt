/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.handlers

import com.radixpro.enigma.be.analysis.ProgRadixAspects
import com.radixpro.enigma.domain.analysis.IAnalyzedPair
import com.radixpro.enigma.domain.astronpos.CalculatedChart
import com.radixpro.enigma.domain.astronpos.IPosition
import com.radixpro.enigma.domain.reqresp.EphProgAspectResponse
import com.radixpro.enigma.domain.reqresp.ProgAnalyzeRequest
import com.radixpro.enigma.references.AspectTypes
import java.util.*

/**
 * Handler for analysing aspects from progressive positions.
 * TODO: replace chartPositions with calculatedChart and progPositions with List for iPosition.
 */
class ProgAspectHandler(private val progRadixAspects: ProgRadixAspects) {

    fun analyzeAspects(request: ProgAnalyzeRequest): EphProgAspectResponse {
        return analyze(request.calculatedChart, request.progPositions, request.aspects, request.orb)
    }

    private fun analyze(calcChart: CalculatedChart,
                        progPositions: List<IPosition>,
                        aspectTypes: List<AspectTypes>,
                        orb: Double): EphProgAspectResponse {
        val chartId = 1L // FIXME use real chartId
        val aspects: MutableList<IAnalyzedPair> = ArrayList()
        for (trPos in progPositions) {
            for (rxBodyPos in calcChart.celPoints) {
                aspects.addAll(progRadixAspects.findAspects(aspectTypes, trPos, rxBodyPos, orb))
            }
            for (rxMundPos in calcChart.mundPoints.specPoints) {
                aspects.addAll(progRadixAspects.findAspects(aspectTypes, trPos, rxMundPos, orb))
            }
        }
        return EphProgAspectResponse(chartId, aspects)
    }
}