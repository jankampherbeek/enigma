/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.xchg.api

import com.radixpro.enigma.be.handlers.PrimaryHandler
import com.radixpro.enigma.domain.reqresp.PrimaryCalcRequest
import com.radixpro.enigma.domain.reqresp.SimpleProgResponse

class PrimaryApi(private val primaryHandler: PrimaryHandler) {

    fun calculatePrimary(request: PrimaryCalcRequest): SimpleProgResponse {
        return primaryHandler.performCalculations(request)
    }
}