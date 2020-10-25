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
import com.radixpro.enigma.statistics.di.StatsInjector.injectScenarioGeneralHandler
import com.radixpro.enigma.statistics.di.StatsInjector.injectScenarioRangeHandler
import com.radixpro.enigma.statistics.persistency.ScenarioFileMapper
import com.radixpro.enigma.statistics.persistency.ScenarioMapper
import com.radixpro.enigma.statistics.persistency.ScenarioPersister
import com.radixpro.enigma.statistics.ui.domain.ScenarioTypes
import java.io.File

interface ScenarioHandler {
    fun saveScenario(scenarioBe: ScenarioBe): ApiResult
    fun readScenario(scenarioName: String, projectName: String): ScenarioBe
}

class ScenarioHandlerFactory {

    fun getHandler(type: ScenarioTypes): ScenarioHandler {
        if (type == ScenarioTypes.RANGE) return injectScenarioRangeHandler()
        return injectScenarioRangeHandler()   // FIXME handle all types of handlers
    }

    fun getGeneralHandler(): ScenarioGeneralHandler {
        return injectScenarioGeneralHandler()
    }
}

class ScenarioGeneralHandler(val reader: FileSystemReader, val mapper: ScenarioFileMapper, private val pathConstructor: PathConstructor) {

    fun retrieveScenarioNames(projectName: String): List<String> {
        val projPath = pathConstructor.pathForProject(projectName)
        val fileNames = reader.readFileItems(projPath, "scen_", ".json")
        return mapper.mapFileNamesToScenarioNames(fileNames)
    }

}

class ScenarioRangeHandler(val persister: ScenarioPersister,
                           val reader: Reader,
                           val mapper: ScenarioMapper,
                           private val pathConstructor: PathConstructor) : ScenarioHandler {

    override fun saveScenario(scenarioBe: ScenarioBe): ApiResult {
        return try {
            val fullPath = pathConstructor.pathForScenario(scenarioBe.name, scenarioBe.projectName)
            persister.saveScenario(scenarioBe, fullPath)
            ApiResult(true, "")
        } catch (se: SaveException) {
            ApiResult(false, se.message)
        }
    }

    override fun readScenario(scenarioName: String, projectName: String): ScenarioBe {
        val fullPath = pathConstructor.pathForProject(projectName)
        val scenarioFile = File(fullPath)
        val jsonObject = reader.readObjectFromFile(scenarioFile)
        return mapper.map(jsonObject)
    }


}