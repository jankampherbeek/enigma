/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.statistics.api

import com.radixpro.enigma.statistics.process.GlobalDataHandler

class GlobalDataApi(private val handler: GlobalDataHandler) {

    fun addDataFile(request: InputDataFileRequest): InputDataFileResponse {
        return handler.addDataFile(request)
    }


}