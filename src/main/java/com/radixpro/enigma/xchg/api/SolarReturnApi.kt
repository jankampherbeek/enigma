/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.xchg.api

import com.radixpro.enigma.Rosetta
import com.radixpro.enigma.be.handlers.SolarReturnHandler
import com.radixpro.enigma.domain.astronpos.CalculatedChart
import com.radixpro.enigma.domain.reqresp.SolarReturnRequest
import com.radixpro.enigma.domain.reqresp.SolarReturnResponse
import com.radixpro.enigma.shared.exceptions.NoPositionFoundException
import org.apache.log4j.Logger

class SolarReturnApi(private val handler: SolarReturnHandler) {
    fun calculateSolarReturn(request: SolarReturnRequest): SolarReturnResponse {
        var solarReturnChart: CalculatedChart? = null
        var resultMsg = "OK"
        try {
            solarReturnChart = handler.getSolarReturnChart(request.longSun, request.birthDateTime, request.ageForReturn,
                    request.location, request.settings)
        } catch (e: NoPositionFoundException) {
            LOG.error("Could not find position in given period. NoPositionFoundException : " + e.message)

            resultMsg = Rosetta.getText("error.positionsearch")
        }
        return SolarReturnResponse(solarReturnChart, resultMsg)
    }

    companion object {
        private val LOG = Logger.getLogger(SolarReturnApi::class.java)
    }

}