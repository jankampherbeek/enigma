/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.xchg.api

import com.radixpro.enigma.be.handlers.InputDataFileHandler
import com.radixpro.enigma.domain.reqresp.InputDataFileRequest
import com.radixpro.enigma.domain.reqresp.InputDataFileResponse

/**
 * Api for converting and saving input data.
 */
class InputDataFileApi(private val handler: InputDataFileHandler) {

    fun addDataFile(request: InputDataFileRequest?): InputDataFileResponse {
        return handler.handleDataFile(request)
    }
}