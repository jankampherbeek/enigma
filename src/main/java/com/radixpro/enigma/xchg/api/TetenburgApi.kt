/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.xchg.api

import com.radixpro.enigma.be.handlers.TetenburgHandler
import com.radixpro.enigma.domain.reqresp.TetenburgRequest
import com.radixpro.enigma.domain.reqresp.TetenburgResponse
import org.apache.log4j.Logger

/**
 * Api for the calculation of critical points according to the theory of Ton Tetenburg.
 */
class TetenburgApi(private val handler: TetenburgHandler) {
    fun calculateCriticalPoint(request: TetenburgRequest): TetenburgResponse {
        var resultMsg = "OK"
        var longAsc = 0.0
        try {
            longAsc = handler.criticalPoint(request.birthDateTime.jd, request.progDateTime.jd, request.location.geoLat,
                    request.longMcRadix, request.solarSpeed)
        } catch (e: Exception) {
            LOG.error("Exception when retrieving TetenburgResponse : " + e.message)
            resultMsg = "Could not calculate critical point."
            // TODO replace with resourcebundle entry
        }
        return TetenburgResponse(longAsc, resultMsg)
    }

    companion object {
        private val LOG = Logger.getLogger(TetenburgApi::class.java)
    }
}