/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.persistency

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class ScenarioGeneralFileMapperTest {

    private val mapper: ScenarioFileMapper = ScenarioGeneralFileMapper()

    @Test
    fun `List with file names is correctly converted to list with scenario names`() {
        mapper.mapFileNamesToScenarioNames(fileNames()) shouldBe scenarioNames()
    }

    private fun fileNames(): List<String> {
        return listOf("scen_AAA.json", "scen_BBB.json", "scen_CCC.json")
    }

    private fun scenarioNames(): List<String> {
        return listOf("AAA", "BBB", "CCC")
    }

}