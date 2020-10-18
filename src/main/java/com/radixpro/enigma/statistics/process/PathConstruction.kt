/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.share.api.PropertyApi

interface PathConstructor {
    fun pathForProject(projectName: String): String
    fun pathForScenario(scenarioName: String, projectName: String): String

}

class StatsPathConstructor(val propApi: PropertyApi) : PathConstructor {

    override fun pathForProject(projectName: String): String {
        val projDir = propApi.read("projdir")[0]
        return "${projDir.value}/proj/$projectName/proj_$projectName.json"
    }

    override fun pathForScenario(scenarioName: String, projectName: String): String {
        val projDir = propApi.read("projdir")[0]
        return "${projDir.value}/proj/$projectName/scen_$scenarioName.json"
    }
}