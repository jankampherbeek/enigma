/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.share.persistency.Reader
import com.radixpro.enigma.share.persistency.Writer
import com.radixpro.enigma.statistics.core.Scenario
import com.radixpro.enigma.statistics.persistency.ScenarioMapper
import java.io.File

interface ScenarioHandler {
    fun saveScenario(pathFile: String, scenario: Scenario)
    fun readScenario(pathFile: String): Scenario
//    fun allScenarios(projectName: String): List<Scenario>
}

class ScenarioRangeHandler(val writer: Writer, val reader: Reader, val mapper: ScenarioMapper) : ScenarioHandler {

    override fun saveScenario(pathFile: String, scenario: Scenario) {
        writer.write2File(pathFile, scenario, true)
    }

    override fun readScenario(pathFile: String): Scenario {
        val fulllFileName = File(pathFile)
        val jsonObject = reader.readObjectFromFile(fulllFileName)
        return mapper.map(jsonObject)
    }

//    override fun allScenarios(projectName: String) {
//       // misschien toch facade tussen JJsonReader en Handler, hierin kun je ook helperclass gebruiken voor lezen van een lijst van Scenarios.
//    }


    // pathFile niet meegeven maar via helperclass laten bepalen.

}