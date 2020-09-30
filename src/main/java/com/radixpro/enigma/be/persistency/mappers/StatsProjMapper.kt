/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.persistency.mappers

import com.radixpro.enigma.domain.config.BaseAstronConfig
import com.radixpro.enigma.domain.stats.DataFileDescription
import com.radixpro.enigma.domain.stats.StatsProject
import com.radixpro.enigma.references.Ayanamshas
import com.radixpro.enigma.references.EclipticProjections
import com.radixpro.enigma.references.HouseSystems
import com.radixpro.enigma.references.ObserverPositions
import org.json.simple.JSONArray
import org.json.simple.JSONObject

/**
 * Converts from Json to StatsProject
 */
class StatsProjMapper {

    fun jsonToStatsProject(jsonObject: JSONObject): StatsProject {
        return constructStatsProject(jsonObject)
    }

    private fun constructStatsProject(jsonObject: JSONObject): StatsProject {
        val name = jsonObject["name"] as String
        val description = jsonObject["description"] as String
        val config = createConfig(jsonObject["baseAstronConfig"] as JSONObject)
        val dataFiles = createDataFiles(jsonObject["dataFiles"] as JSONArray)
        return StatsProject(name, description, config, dataFiles)
    }

    private fun createConfig(jsonObject: JSONObject): BaseAstronConfig {
        val houseSystem = HouseSystems.valueOf(jsonObject["houseSystem"] as String)
        val ayanamsha = Ayanamshas.valueOf(jsonObject["ayanamsha"] as String)
        val eclProj = EclipticProjections.valueOf(jsonObject["eclipticProjection"] as String)
        val obsPos = ObserverPositions.valueOf(jsonObject["observerPosition"] as String)
        return BaseAstronConfig(houseSystem, ayanamsha, eclProj, obsPos)
    }

    private fun createDataFiles(jsonArray: JSONArray): List<DataFileDescription> {
        val dataFiles: MutableList<DataFileDescription> = ArrayList()
        for (dataObject in jsonArray) {
            val jsonObject = dataObject as JSONObject
            val name = jsonObject["name"] as String
            val description = jsonObject["description"] as String
            val nrOfRecords = jsonObject["nrOfRecords"].toString().toInt()
            dataFiles.add(DataFileDescription(name, description, nrOfRecords))
        }
        return dataFiles
    }

}
