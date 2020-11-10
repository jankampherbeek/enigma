/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.persistency

import com.radixpro.enigma.references.CelestialObjects
import com.radixpro.enigma.references.MundanePointsAstron
import com.radixpro.enigma.statistics.core.ScenRangeBe
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

internal class ScenRangeBeMapperTest {

    private lateinit var mapper: ScenarioMapper
    private lateinit var scenarioBe: ScenRangeBe
    private val jsonInput = "{\n" +
            "  \"name\" : \"scenTest\",\n" +
            "  \"description\" : \"Test scenario\",\n" +
            "  \"projectName\" : \"testProject\",\n" +
            "  \"scenarioType\" : \"RANGE\",\n" +
            "  \"rangeType\" : \"SIGNS\",\n" +
            "  \"houseSystem\" : \"PLACIDUS\",\n" +
            "  \"celObjects\" : [ \"SUN\", \"PLUTO\" ],\n" +
            "  \"mundanePoints\" : [ \"ASC\", \"MC\" ]\n" +
            "}"

    @BeforeEach
    fun init() {
        mapper = ScenarioRangeMapper()
        scenarioBe = mapper.map(JSONValue.parse(jsonInput) as JSONObject) as ScenRangeBe
    }

    @Test
    fun `Json is correctly mapped to ScenarioRange `() {
        assertAll(
                Executable { assertEquals("scenTest", scenarioBe.name, "Name for ScenarioRange should be: scenTest.") },
                Executable { assertEquals("Test scenario", scenarioBe.description, "Description for ScenarioRange should be: TestScenario.") },
                Executable { assertEquals("testProject", scenarioBe.projectName, "ProjectName for ScenarioRange should be: testProject.") },
                Executable { assertEquals(ScenarioTypes.RANGE, scenarioBe.scenarioType, "ScenarioType for ScenarioRange should be: ScenarioTypes.RANGE.") },
                Executable { assertEquals(StatsRangeTypes.SIGNS, scenarioBe.rangeType, "RangeType for ScenarioRange should be: StatsRangeTypes,SIGNS.") }
        )
    }

    @Nested
    inner class ContentOfObjectsInScenarioBe() {
        @Test
        fun `Number of CelObjects is correct`() {
            assertEquals(2, scenarioBe.celObjects.size, "Number of CelObjects should be 2.")
        }

        @Test
        fun `Content of CelestialObjects is correct`() {
            val celObjects = scenarioBe.celObjects
            assertAll(
                    Executable { assertEquals(CelestialObjects.SUN, celObjects[0], "First CelObject should be SUN.") },
                    Executable { assertEquals(CelestialObjects.PLUTO, celObjects[1], "Second CelObject should be PLUTO.") }
            )
        }

        @Test
        fun `Number of MundanePoints is correct`() {
            assertEquals(2, scenarioBe.mundanePoints.size, "Number of MundanePoints should be 2.")
        }

        @Test
        fun `Content of MundanePoints is correct`() {
            val mundanePoints = scenarioBe.mundanePoints
            assertAll(
                    Executable { assertEquals(MundanePointsAstron.ASC, mundanePoints[0], "First MundanePoint should be ASC.") },
                    Executable { assertEquals(MundanePointsAstron.MC, mundanePoints[1], "Second MundanePoint should be MC.") }
            )
        }
    }

}
