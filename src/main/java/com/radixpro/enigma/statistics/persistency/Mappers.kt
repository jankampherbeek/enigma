/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.persistency

import com.radixpro.enigma.references.CelestialObjects
import com.radixpro.enigma.references.HouseSystems
import com.radixpro.enigma.references.MundanePointsAstron
import com.radixpro.enigma.share.exceptions.ItemNotFoundException
import com.radixpro.enigma.statistics.core.ScenMinMaxBe
import com.radixpro.enigma.statistics.core.ScenRangeBe
import com.radixpro.enigma.statistics.core.ScenarioBe
import com.radixpro.enigma.statistics.core.StatsMinMaxTypesBe
import com.radixpro.enigma.statistics.ui.domain.ScenarioTypes
import com.radixpro.enigma.statistics.ui.domain.StatsRangeTypes
import org.apache.log4j.Logger
import org.json.simple.JSONArray
import org.json.simple.JSONObject

interface ScenarioMapper {
    fun map(jsonObject: JSONObject): ScenarioBe
    fun map(jsonArray: JSONArray): List<String>

    fun constructScenarioType(name: String): ScenarioTypes {
        val values = ScenarioTypes.values()
        for (item in values) {
            if (name == item.toString()) return item
        }
        log.error("Could not find ScenarioTypes for $name when parsing Json for ScenarioRange.")
        throw (RuntimeException("ScenarioType not found."))
    }
}

interface ScenarioFileMapper {
    fun mapFileNamesToScenarioNames(fileNames: List<String>): List<String>
}

private val log = Logger.getLogger(ScenarioMapper::class.java)

private fun constructCelObject(name: String): CelestialObjects {
    val values = CelestialObjects.values()
    for (item in values) {
        if (name == item.toString()) return item
    }
    log.error("Could not find CelObjects for $name when parsing Json for ScenarioRange.")
    throw (RuntimeException("CelObject not found."))
}

private fun constructMundanePoint(name: String): MundanePointsAstron {
    val values = MundanePointsAstron.values()
    for (item in values) {
        if (name == item.toString()) return item
    }
    log.error("Could not find MundanePoints for $name when parsing Json for ScenarioRange.")
    throw (RuntimeException("MundanePoint not found."))
}

private fun constructAllCelObjects(jsonObjects: JSONArray): MutableList<CelestialObjects> {
    val allCelObjects: MutableList<CelestialObjects> = ArrayList()
    for (celObjectName in jsonObjects) {
        val name = celObjectName as String
        val celObject = constructCelObject(name)
        allCelObjects.add(celObject)
    }
    return allCelObjects
}

private fun constructAllMundanePoints(jsonObjects: JSONArray): MutableList<MundanePointsAstron> {
    val allMundanePoints: MutableList<MundanePointsAstron> = ArrayList()
    for (mundanePointName in jsonObjects) {
        val name = mundanePointName as String
        val mundanePoint = constructMundanePoint(name)
        allMundanePoints.add(mundanePoint)
    }
    return allMundanePoints
}

class ScenarioGeneralFileMapper() : ScenarioFileMapper {
    override fun mapFileNamesToScenarioNames(fileNames: List<String>): List<String> {
        val scenarioNames: MutableList<String> = ArrayList()
        for (fileName in fileNames) {
            val startPos = 5
            val endPos = fileName.indexOf(".json") - 1
            scenarioNames.add(fileName.substring(startPos..endPos))
        }
        return scenarioNames.toList()
    }
}

class ScenarioSpecificMapper : ScenarioMapper {

    override fun map(jsonObject: JSONObject): ScenarioBe {
        return constructObject(jsonObject)
    }

    override fun map(jsonArray: JSONArray): List<String> {
        TODO("Not yet implemented")
    }

    private fun constructObject(jsonObject: JSONObject): ScenarioBe {
        return when (constructScenarioType(jsonObject["scenarioType"] as String)) {
            ScenarioTypes.RANGE -> ScenarioRangeMapper().map(jsonObject)
            ScenarioTypes.MINMAX -> ScenMinMaxMapper().map(jsonObject)
            else -> throw ItemNotFoundException("Could not find Scenario Type when constructing ScenarioBe in ScenarioSpecificMapper.")
        }
    }
}


class ScenarioRangeMapper : ScenarioMapper {

    override fun map(jsonObject: JSONObject): ScenarioBe {
        return constructObject(jsonObject)
    }

    override fun map(jsonArray: JSONArray): List<String> {
        TODO("Not yet implemented")
    }

    private fun constructObject(jsonObject: JSONObject): ScenRangeBe {
        val name = jsonObject["name"] as String
        val description = jsonObject["description"] as String
        val projectName = jsonObject["projectName"] as String
        val scenarioType = constructScenarioType(jsonObject["scenarioType"] as String)
        val rangeType = constructRangeType(jsonObject["rangeType"] as String)
        val houseSystem = constructHouseSystem(jsonObject["houseSystem"] as String)
        val celObjects = constructAllCelObjects(jsonObject["celObjects"] as JSONArray)
        val mundanePoints = constructAllMundanePoints(jsonObject["mundanePoints"] as JSONArray)
        return ScenRangeBe(name, description, projectName, scenarioType, rangeType, houseSystem, celObjects, mundanePoints)
    }

    private fun constructRangeType(name: String): StatsRangeTypes {
        val values = StatsRangeTypes.values()
        for (item in values) {
            if (name == item.toString()) return item
        }
        log.error("Could not find StatsRangeTypes for $name when parsing Json for ScenarioRange.")
        throw (RuntimeException("StatsRangeType not found."))
    }

    private fun constructHouseSystem(name: String): HouseSystems {
        val values = HouseSystems.values()
        for (item in values) {
            if (name == item.toString()) return item
        }
        log.error("Could not find HouseSystems for $name when parsing Json for ScenarioRange.")
        throw (RuntimeException("Houseystem not found."))
    }

}

class ScenMinMaxMapper : ScenarioMapper {

    override fun map(jsonObject: JSONObject): ScenarioBe {
        return constructObject(jsonObject)
    }

    override fun map(jsonArray: JSONArray): List<String> {
        TODO("Not yet implemented")
    }

    private fun constructObject(jsonObject: JSONObject): ScenMinMaxBe {
        val name = jsonObject["name"] as String
        val description = jsonObject["description"] as String
        val projectName = jsonObject["projectName"] as String
        val scenarioType = constructScenarioType(jsonObject["scenarioType"] as String)
        val minMaxType = constructMinMaxType(jsonObject["minMaxType"] as String)
        var refPoint = "";
        if (jsonObject.containsKey("refPoint")) refPoint = jsonObject["refPoint"] as String
        var celObjects: MutableList<CelestialObjects> = ArrayList()
        if (jsonObject.containsKey("celObjects")) celObjects = constructAllCelObjects(jsonObject["celObjects"] as JSONArray)
        var mundanePoints: MutableList<MundanePointsAstron> = ArrayList()
        if (jsonObject.containsKey("mundanePoints")) mundanePoints = constructAllMundanePoints(jsonObject["mundanePoints"] as JSONArray)
        return ScenMinMaxBe(name, description, projectName, scenarioType, minMaxType, refPoint, celObjects, mundanePoints)
    }

    private fun constructMinMaxType(name: String): StatsMinMaxTypesBe {
        val values = StatsMinMaxTypesBe.values()
        for (item in values) {
            if (name == item.toString()) return item
        }
        throw (ItemNotFoundException("Could not find $name in StatsRangeTypes wehn mapping to ScenMinMaxBe."))
    }


}

