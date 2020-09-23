/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.xchg.api

import com.radixpro.enigma.be.handlers.CalculatedChartHandler
import com.radixpro.enigma.domain.astronpos.CalculatedChart
import com.radixpro.enigma.domain.reqresp.CalculatedChartRequest
import com.radixpro.enigma.domain.reqresp.CalculatedChartResponse

class CalculatedChartApi(private val handler: CalculatedChartHandler) {

    fun calcChart(request: CalculatedChartRequest): CalculatedChartResponse {
        var calculatedChart: CalculatedChart? = null
        var resultMsg = "OK"
        try {
            calculatedChart = handler.defineChart(request.settings, request.dateTimeJulian, request.location)
        } catch (e: Exception) {
            resultMsg = "Exception when calculating a chart : " + e.message
        }
        return CalculatedChartResponse(calculatedChart!!, resultMsg)
    }
}