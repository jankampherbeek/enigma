/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.xchg.api

import com.radixpro.enigma.be.handlers.EphProgCalcHandler
import com.radixpro.enigma.be.handlers.ProgAspectHandler
import com.radixpro.enigma.be.handlers.SecundaryDateHandler
import com.radixpro.enigma.domain.reqresp.*

class SecundaryApi(private val calcHandler: EphProgCalcHandler,
                   private val secundaryDateHandler: SecundaryDateHandler,
                   private val aspectHandler: ProgAspectHandler) {

    fun calculateSecundary(request: SecundaryCalcRequest): SimpleProgResponse {
        val eventDateTime = request.dateTime
        val birthDateTime = request.birthDateTime
        val secDateTime = secundaryDateHandler.calcSecundaryDate(birthDateTime, eventDateTime)
        val secRequest: IProgCalcRequest = SecundaryCalcRequest(secDateTime, birthDateTime, request.location, request.settings)
        return calcHandler.retrievePositions(secRequest)
    }

    fun defineAspects(request: ProgAnalyzeRequest): EphProgAspectResponse {
        return aspectHandler.analyzeAspects(request)
    }
}