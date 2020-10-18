/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.share.persistency.Reader
import com.radixpro.enigma.statistics.core.Scenario
import com.radixpro.enigma.statistics.persistency.ScenarioMapper
import com.radixpro.enigma.statistics.persistency.ScenarioPersister
import java.io.File

interface ScenarioHandler {
    fun saveScenario(scenario: Scenario)
    fun readScenario(scenarioName: String, projectName: String): Scenario
    fun readAllScenarios(projectName: String): List<Scenario>
}

class ScenarioRangeHandler(val persister: ScenarioPersister,
                           val reader: Reader,
                           val mapper: ScenarioMapper,
                           private val pathConstructor: PathConstructor) : ScenarioHandler {

    override fun saveScenario(scenario: Scenario) {
        val fullPath = pathConstructor.pathForScenario(scenario.name, scenario.projectName)
        persister.saveScenario(scenario, fullPath)
    }

    override fun readScenario(scenarioName: String, projectName: String): Scenario {
        val fullPath = pathConstructor.pathForProject(projectName)
        val scenarioFile = File(fullPath)
        val jsonObject = reader.readObjectFromFile(scenarioFile)
        return mapper.map(jsonObject)
    }

    override fun readAllScenarios(projectName: String): List<Scenario> {
        TODO("Not yet implemented")   // does not fit in specific SCenario......
    }


}