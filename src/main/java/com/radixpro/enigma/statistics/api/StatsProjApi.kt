/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.api

import com.radixpro.enigma.domain.reqresp.StatsProjResponse
import com.radixpro.enigma.statistics.core.IStatsProject
import com.radixpro.enigma.statistics.core.StatsProject
import com.radixpro.enigma.statistics.process.StatsProjHandler

class StatsProjApi(private val handler: StatsProjHandler) {

    fun saveProject(project: StatsProject): StatsProjResponse {
        val resultMsg = handler.saveProject(project)
        return StatsProjResponse(resultMsg.contentEquals("OK"), resultMsg)
    }

    fun read(projName: String): IStatsProject {
        return handler.read(projName)
    }

    fun readAllNames(): List<String> {
        return handler.readAllNames()
    }

}