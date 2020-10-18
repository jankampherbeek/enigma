/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.persistency

import com.radixpro.enigma.share.persistency.Writer
import com.radixpro.enigma.statistics.core.Scenario

interface ScenarioPersister {
    fun saveScenario(scenario: Scenario, pathFileName: String)
}


class ScenarioRangePersister(val writer: Writer) : ScenarioPersister {

    override fun saveScenario(scenario: Scenario, pathFileName: String) {
        writer.write2File(pathFileName, scenario, true)
    }

}