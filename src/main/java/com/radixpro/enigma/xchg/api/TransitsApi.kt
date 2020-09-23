/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.xchg.api

import com.radixpro.enigma.be.handlers.EphProgCalcHandler
import com.radixpro.enigma.be.handlers.ProgAspectHandler
import com.radixpro.enigma.domain.reqresp.EphProgAspectResponse
import com.radixpro.enigma.domain.reqresp.IProgCalcRequest
import com.radixpro.enigma.domain.reqresp.ProgAnalyzeRequest
import com.radixpro.enigma.domain.reqresp.SimpleProgResponse

class TransitsApi(private val calcHandler: EphProgCalcHandler,
                  private val aspectHandler: ProgAspectHandler) {
    fun calculateTransits(request: IProgCalcRequest): SimpleProgResponse {
        return calcHandler.retrievePositions(request)
    }

    fun defineAspects(request: ProgAnalyzeRequest): EphProgAspectResponse {
        return aspectHandler.analyzeAspects(request)
    }
}