/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.persistency

import com.radixpro.enigma.share.persistency.JsonReader
import org.json.simple.JSONObject
import java.io.File

interface ScenarioRetriever {
    fun retrieveScenario(scenarioFile: File): JSONObject
}

class ScenarioRangeRetriever(val reader: JsonReader) : ScenarioRetriever {

    override fun retrieveScenario(scenarioFile: File): JSONObject {
        return reader.readObjectFromFile(scenarioFile)
    }

}