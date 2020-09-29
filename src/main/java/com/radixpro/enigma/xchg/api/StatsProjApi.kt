/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api

import com.radixpro.enigma.be.handlers.StatsProjHandler
import com.radixpro.enigma.domain.reqresp.StatsProjResponse
import com.radixpro.enigma.domain.stats.StatsProject

class StatsProjApi(private val handler: StatsProjHandler) {

    fun saveProject(project: StatsProject): StatsProjResponse {
        val resultMsg = handler.saveProject(project)
        return StatsProjResponse(resultMsg.contentEquals("OK"), resultMsg)
    }

}