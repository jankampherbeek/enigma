/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.persistency

import com.radixpro.enigma.references.CelestialObjects
import com.radixpro.enigma.references.MundanePointsAstron
import com.radixpro.enigma.statistics.core.ScenMinMaxBe
import com.radixpro.enigma.statistics.core.StatsMinMaxTypesBe
import com.radixpro.enigma.statistics.ui.domain.ScenarioTypes
import org.json.simple.JSONObject
import org.json.simple.JSONValue
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

internal class ScenMinMaxBeMapperTest {

    private lateinit var mapper: ScenarioMapper
    private lateinit var scenarioBe: ScenMinMaxBe
    private val jsonInput = "{\n" +
            "  \"name\" : \"scenTest\",\n" +
            "  \"description\" : \"Test scenario\",\n" +
            "  \"projectName\" : \"testProject\",\n" +
            "  \"scenarioType\" : \"MINMAX\",\n" +
            "  \"minMaxTypes\" : \"ECLIPTIC_DISTANCE\",\n" +
            "  \"refPoint\" : \"MC\",\n" +
            "  \"celObjects\" : [ \"MOON\", \"JUPITER\" ],\n" +
            "  \"mundanePoints\" : [ \"VERTEX\", \"ASC\" ]\n" +
            "}"

    @BeforeEach
    fun init() {
        mapper = ScenMinMaxMapper()
        scenarioBe = mapper.map(JSONValue.parse(jsonInput) as JSONObject) as ScenMinMaxBe
    }


    @Test
    fun `Json is correctly mapped to ScenarioMinMax`() {
        assertAll(
                Executable { assertEquals("scenTest", scenarioBe.name, "Name for ScenarioMinMax should be: scenTest.") },
                Executable { assertEquals("Test scenario", scenarioBe.description, "Description for ScenarioMinMax should be: Test scenario.") },
                Executable { assertEquals("testProject", scenarioBe.projectName, "ProjectName for ScenarioMinMax should be: testProject.") },
                Executable { assertEquals(ScenarioTypes.MINMAX, scenarioBe.scenarioType, "ScenarioType for ScenarioMinMax should be: ScenarioTypes.MINMAX.") },
                Executable { assertEquals(StatsMinMaxTypesBe.ECLIPTIC_DISTANCE, scenarioBe.minMaxTypes, "MinMaxType for ScenarioMinMax should be: MinMaxTypes.ECLIPTIC_DISTANCE.") }
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
                    Executable { assertEquals(CelestialObjects.MOON, celObjects[0], "First CelObject should be MOON.") },
                    Executable { assertEquals(CelestialObjects.JUPITER, celObjects[1], "Second CelObject should be JUPITER.") }
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
                    Executable { assertEquals(MundanePointsAstron.VERTEX, mundanePoints[0], "First MundanePoint should be VERTEX.") },
                    Executable { assertEquals(MundanePointsAstron.ASC, mundanePoints[1], "Second MundanePoint should be ASC.") }
            )
        }
    }
}

