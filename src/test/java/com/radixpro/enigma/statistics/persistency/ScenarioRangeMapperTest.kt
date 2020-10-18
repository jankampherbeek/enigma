/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.persistency

import com.radixpro.enigma.astronomy.ui.domain.CelObjects
import com.radixpro.enigma.astronomy.ui.domain.MundanePoints
import com.radixpro.enigma.statistics.core.ScenarioRange
import com.radixpro.enigma.statistics.ui.domain.ScenarioTypes
import com.radixpro.enigma.statistics.ui.domain.StatsRangeTypes
import org.json.simple.JSONObject
import org.json.simple.JSONValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ScenarioRangeMapperTest {

    private var mapper: ScenarioMapper = ScenarioRangeMapper()

    private val jsonInput = "{\n" +
            "  \"name\" : \"scenTest\",\n" +
            "  \"description\" : \"Test scenario\",\n" +
            "  \"projectName\" : \"testProject\",\n" +
            "  \"scenarioType\" : \"RANGE\",\n" +
            "  \"rangeType\" : \"SIGNS\",\n" +
            "  \"celObjects\" : [ \"SUN\", \"PLUTO\" ],\n" +
            "  \"mundanePoints\" : [ \"ASC\", \"MC\" ]\n" +
            "}"

    @Test
    fun mapJsonForScenarioRange() {
        val scenario = mapper.map(JSONValue.parse(jsonInput) as JSONObject) as ScenarioRange
        assertEquals("scenTest", scenario.name, "Name for ScenarioRange is correct.")
        assertEquals("Test scenario", scenario.description, "Description for ScenarioRange is correct.")
        assertEquals("testProject", scenario.projectName, "ProjectName for ScenarioRange is correct.")
        assertEquals(ScenarioTypes.RANGE, scenario.scenarioType, "ScenarioType for ScenarioRange is correct.")
        assertEquals(StatsRangeTypes.SIGNS, scenario.rangeType, "RangeType for ScenarioRange is correct.")
        val celObjects = scenario.celObjects
        assertEquals(2, celObjects.size, "Number of CelObjects is correct.")
        assertEquals(CelObjects.SUN, celObjects[0], "First CelObject is SUN.")
        val mundanePoints = scenario.mundanePoints
        assertEquals(2, mundanePoints.size, "Number of MundanePoints is correct.")
        assertEquals(MundanePoints.MC, mundanePoints[1], "Second MundanePoint is MC.")
    }

}
