/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.api

import com.radixpro.enigma.statistics.api.converters.ProjectConverter
import com.radixpro.enigma.statistics.api.xchg.ApiResult
import com.radixpro.enigma.statistics.core.IStatsProject
import com.radixpro.enigma.statistics.core.Scenario
import com.radixpro.enigma.statistics.process.ScenarioHandler
import com.radixpro.enigma.statistics.process.StatsProjHandler
import com.radixpro.enigma.statistics.ui.domain.StatsProjectFe

interface ProjectApi {
    fun save(projectFe: StatsProjectFe): ApiResult
    fun read(projName: String): IStatsProject
    fun readAllNames(): List<String>
}


interface ScenarioApi {
    fun save(scenario: Scenario)
    fun read(scenarioName: String, projectName: String): Scenario    // TODO change into response
    fun readAllNames(projectName: String): List<Scenario>   // TODO change into response
}

class StatsProjApi(private val handler: StatsProjHandler, private val converter: ProjectConverter) : ProjectApi {

    override fun save(projectFe: StatsProjectFe): ApiResult {
        val projectBe = converter.feRequestToBe(projectFe)
        return handler.saveProject(projectBe)
    }

    override fun read(projName: String): IStatsProject {
        return handler.read(projName)
    }

    override fun readAllNames(): List<String> {
        return handler.readAllNames()
    }

}

class ScenarioGeneralApi(private val handler: ScenarioHandler) : ScenarioApi {

    override fun save(scenario: Scenario) {
        handler.saveScenario(scenario)
    }

    override fun read(scenarioName: String, projectName: String): Scenario {
        return handler.readScenario(scenarioName, projectName)
    }

    override fun readAllNames(projectName: String): List<Scenario> {
        return handler.readAllScenarios(projectName)
    }

}
