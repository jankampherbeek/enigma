/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.api

import com.radixpro.enigma.statistics.api.converters.ProjectConverter
import com.radixpro.enigma.statistics.api.converters.ScenConverterFactory
import com.radixpro.enigma.statistics.api.xchg.ApiResult
import com.radixpro.enigma.statistics.core.IStatsProject
import com.radixpro.enigma.statistics.process.ScenarioHandlerFactory
import com.radixpro.enigma.statistics.process.StatsProjHandler
import com.radixpro.enigma.statistics.ui.domain.ScenarioFe
import com.radixpro.enigma.statistics.ui.domain.ScenarioTypes
import com.radixpro.enigma.statistics.ui.domain.StatsProjectFe

interface ProjectApi {
    fun save(projectFe: StatsProjectFe): ApiResult
    fun read(projName: String): IStatsProject
    fun readAllNames(): List<String>
}


interface ScenarioApi {
    fun save(scenarioFe: ScenarioFe): ApiResult
    fun read(scenName: String, typeName: String, projName: String): ScenarioFe
}

class StatsProjApi(private val handler: StatsProjHandler, private val converter: ProjectConverter) : ProjectApi {

    override fun save(projectFe: StatsProjectFe): ApiResult {
        val projectBe = converter.feRequestToBe(projectFe)
        return handler.saveProject(projectBe)
    }

    override fun read(projName: String): IStatsProject {
        return handler.read(projName)
    }

    override fun readAllNames(): List<String> {
        return handler.readAllNames()
    }

}

class ScenGeneralApi(private val handlerFactory: ScenarioHandlerFactory,
                     private val converterFactory: ScenConverterFactory) : ScenarioApi {

    fun readAllNames(projectName: String): List<String> {
        val handler = handlerFactory.getGeneralHandler()
        return handler.retrieveScenarioNames(projectName)
    }

    override fun save(scenarioFe: ScenarioFe): ApiResult {
        val type = ScenarioTypes.valueOf(scenarioFe.typeName)
        val handler = handlerFactory.getHandler(type)
        val converter = converterFactory.getConverter(type)
        return handler.saveScenario(converter.feRequestToBe(scenarioFe))
    }

    override fun read(scenName: String, typeName: String, projName: String): ScenarioFe {
        val type = ScenarioTypes.valueOf(typeName)
        val handler = handlerFactory.getHandler(type)
        val converter = converterFactory.getConverter(type)
        return converter.beRequestToFe(handler.readScenario(scenName, projName))
    }

}
