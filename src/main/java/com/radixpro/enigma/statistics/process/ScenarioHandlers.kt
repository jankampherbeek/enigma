/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.share.exceptions.SaveException
import com.radixpro.enigma.share.persistency.FileSystemReader
import com.radixpro.enigma.share.persistency.Reader
import com.radixpro.enigma.statistics.api.xchg.ApiResult
import com.radixpro.enigma.statistics.core.ScenarioBe
import com.radixpro.enigma.statistics.persistency.ScenPersister
import com.radixpro.enigma.statistics.persistency.ScenarioFileMapper
import com.radixpro.enigma.statistics.persistency.ScenarioMapper
import java.io.File

//interface ScenarioHandler {
//    fun saveScenario(scenarioBe: ScenarioBe): ApiResult
//    fun readScenario(scenarioName: String, projectName: String): ScenarioBe
//}

//class ScenHandlerFactory {
//
//    fun getHandler(type: ScenarioTypes): ScenarioHandler {
//        if (type == ScenarioTypes.RANGE) return injectScenarioRangeHandler()
//        return injectScenarioRangeHandler()   // FIXME handle all types of handlers
//    }
//
//    fun getGeneralHandler(): ScenarioGeneralHandler {
//        return injectScenarioGeneralHandler()
//    }
//}

class ScenarioGeneralHandler(val reader: FileSystemReader, val mapper: ScenarioFileMapper, private val pathConstructor: StatsPathConstructor) {

    fun retrieveScenarioNames(projectName: String): List<String> {
        val projPath = pathConstructor.pathForProjectDir(projectName)
        val fileNames = reader.readFileItems(projPath, "scen_", ".json")
        return mapper.mapFileNamesToScenarioNames(fileNames)
    }

}

/**
 * Handler for reading and writing scenarios.
 * Write:
 * ScenGeneralApi uses ScenMinMaxConverter to create a backend scenario from a frontend scenario. The converter is retreived via ScenConverterFactory.
 * The Api uses ScenHandler to process the results. The handler is retrieved from ScenHandlerFactory.
 * The handler user StatsPathConstructor to define the location of the files and ScenPersister to save the content. ScenPersister uses JsonWriter.
 * Read:
 * ScenGeneralApi uses ScenHandler to retrieve the data and MinMaxConverter to create a frontend scenario from a backend scenario. Factories are used, see above.
 * The handler uses ScenarioMapper to translate the Json resutls to an object.
 * After receiving the results from the Handler, the API converts the results to a frontend Scenario using
 */
class ScenHandler(val persister: ScenPersister,
                  val reader: Reader,
                  val mapper: ScenarioMapper,
                  private val pathConstructor: StatsPathConstructor) {

    fun saveScenario(scenarioBe: ScenarioBe): ApiResult {
        return try {
            val fullPath = pathConstructor.pathForScenario(scenarioBe.name, scenarioBe.projectName)
            persister.saveScenario(scenarioBe, fullPath)
            ApiResult(true, "")
        } catch (se: SaveException) {
            ApiResult(false, se.message)
        }
    }

    fun readScenario(scenarioName: String, projectName: String): ScenarioBe {
        val fullPath = pathConstructor.pathForScenario(scenarioName, projectName)
        val scenarioFile = File(fullPath)
        val jsonObject = reader.readObjectFromFile(scenarioFile)
        return mapper.map(jsonObject)
    }

    fun deleteScenario(scenarioName: String, projectName: String): ApiResult {
        return try {
            val fullPath = pathConstructor.pathForScenario(scenarioName, projectName)
            persister.deleteScenario(fullPath)
            ApiResult(true, "")
        } catch (e: Exception) {
            ApiResult(false, "Error deleting $scenarioName")
        }
    }
}

