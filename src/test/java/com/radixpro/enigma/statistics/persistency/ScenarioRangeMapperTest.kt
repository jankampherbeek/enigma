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
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

internal class ScenarioRangeMapperTest {

    private lateinit var mapper: ScenarioMapper
    private lateinit var scenario: ScenarioRange
    private val jsonInput = "{\n" +
            "  \"name\" : \"scenTest\",\n" +
            "  \"description\" : \"Test scenario\",\n" +
            "  \"projectName\" : \"testProject\",\n" +
            "  \"scenarioType\" : \"RANGE\",\n" +
            "  \"rangeType\" : \"SIGNS\",\n" +
            "  \"celObjects\" : [ \"SUN\", \"PLUTO\" ],\n" +
            "  \"mundanePoints\" : [ \"ASC\", \"MC\" ]\n" +
            "}"

    @BeforeEach
    fun init() {
        mapper = ScenarioRangeMapper()
        scenario = mapper.map(JSONValue.parse(jsonInput) as JSONObject) as ScenarioRange
    }

    @Test
    fun `Json is correctly mapped to ScenarioRange `() {
        assertAll(
                Executable { assertEquals("scenTest", scenario.name, "Name for ScenarioRange should be: scenTest.") },
                Executable { assertEquals("Test scenario", scenario.description, "Description for ScenarioRange should be: TestScenario.") },
                Executable { assertEquals("testProject", scenario.projectName, "ProjectName for ScenarioRange should be: testProject.") },
                Executable { assertEquals(ScenarioTypes.RANGE, scenario.scenarioType, "ScenarioType for ScenarioRange should be: ScenarioTypes.RANGE.") },
                Executable { assertEquals(StatsRangeTypes.SIGNS, scenario.rangeType, "RangeType for ScenarioRange should be: StatsRangeTypes,SIGNS.") }
        )
    }

    @Nested
    inner class ContentOfObjectsInScenario() {
        @Test
        fun `Number of CelObjects is correct`() {
            assertEquals(2, scenario.celObjects.size, "Number of CelObjects should be 2.")
        }

        @Test
        fun `Content of Celobjects is correct`() {
            val celObjects = scenario.celObjects
            assertAll(
                    Executable { assertEquals(CelObjects.SUN, celObjects[0], "First CelObject should be SUN.") },
                    Executable { assertEquals(CelObjects.PLUTO, celObjects[1], "Second CelObject should be PLUTO.") }
            )
        }

        @Test
        fun `Number of MundanePoints is correct`() {
            assertEquals(2, scenario.mundanePoints.size, "Number of MundanePoints should be 2.")
        }

        @Test
        fun `Content of MundanePoints is correct`() {
            val mundanePoints = scenario.mundanePoints
            assertAll(
                    Executable { assertEquals(MundanePoints.ASC, mundanePoints[0], "First MundanePoint should be ASC.") },
                    Executable { assertEquals(MundanePoints.MC, mundanePoints[1], "Second MundanePoint should be MC.") }
            )
        }
    }

}
