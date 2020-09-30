/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.persistency.mappers

import com.radixpro.enigma.references.Ayanamshas
import com.radixpro.enigma.references.EclipticProjections
import com.radixpro.enigma.references.HouseSystems
import com.radixpro.enigma.references.ObserverPositions
import org.json.simple.JSONObject
import org.json.simple.JSONValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class StatsProjMapperTest {

    private val jsonString = "{\n" +
            "  \"name\" : \"Project5\",\n" +
            "  \"description\" : \"Project nr 5\",\n" +
            "  \"baseAstronConfig\" : {\n" +
            "    \"houseSystem\" : \"WHOLESIGN\",\n" +
            "    \"ayanamsha\" : \"NONE\",\n" +
            "    \"eclipticProjection\" : \"TROPICAL\",\n" +
            "    \"observerPosition\" : \"GEOCENTRIC\"\n" +
            "  },\n" +
            "  \"dataFiles\" : [ {\n" +
            "    \"name\" : \"Test1\",\n" +
            "    \"description\" : \"De eerste test\",\n" +
            "    \"nrOfRecords\" : 2\n" +
            "  } ]\n" +
            "}"

    @Test
    fun jsonToStatsProject() {
        val jsonObject = JSONValue.parse(jsonString) as JSONObject
        val statsProject = StatsProjMapper().jsonToStatsProject(jsonObject)
        assertEquals("Project5", statsProject.name)
        assertEquals("Project nr 5", statsProject.description)
        assertEquals(HouseSystems.WHOLESIGN, statsProject.baseAstronConfig.houseSystem)
        assertEquals(Ayanamshas.NONE, statsProject.baseAstronConfig.ayanamsha)
        assertEquals(EclipticProjections.TROPICAL, statsProject.baseAstronConfig.eclipticProjection)
        assertEquals(ObserverPositions.GEOCENTRIC, statsProject.baseAstronConfig.observerPosition)
        assertEquals(1, statsProject.dataFiles.size)
        assertEquals("Test1", statsProject.dataFiles[0].name)
        assertEquals(2, statsProject.dataFiles[0].nrOfRecords)
    }
}