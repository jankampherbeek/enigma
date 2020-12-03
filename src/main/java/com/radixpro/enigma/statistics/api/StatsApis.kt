/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.api

import com.radixpro.enigma.share.exceptions.ItemNotFoundException
import com.radixpro.enigma.statistics.api.xchg.ApiResult
import com.radixpro.enigma.statistics.converters.ProjectConverter
import com.radixpro.enigma.statistics.converters.ScenConverterFactory
import com.radixpro.enigma.statistics.core.IStatsProject
import com.radixpro.enigma.statistics.process.ScenHandler
import com.radixpro.enigma.statistics.process.ScenarioGeneralHandler
import com.radixpro.enigma.statistics.process.StatsProcessHandler
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
    fun read(scenName: String, projName: String): ScenarioFe
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

class ScenGeneralApi(private val generalHandler: ScenarioGeneralHandler,
                     private val scenHandler: ScenHandler,
                     private val converterFactory: ScenConverterFactory) : ScenarioApi {

    fun readAllNames(projectName: String): List<String> {
        return generalHandler.retrieveScenarioNames(projectName)
    }

    override fun save(scenarioFe: ScenarioFe): ApiResult {
        val type = ScenarioTypes.valueOf(scenarioFe.typeName)
        val converter = converterFactory.getConverter(type)
        return scenHandler.saveScenario(converter.feRequestToBe(scenarioFe))
    }

    override fun read(scenName: String, projName: String): ScenarioFe {
        val scenario = scenHandler.readScenario(scenName, projName)
        val type = scenario.scenarioType
        val converter = converterFactory.getConverter(type)
        return converter.beRequestToFe(scenario)
    }

}


class StatsProcessApi(private val handler: StatsProcessHandler, private val converterFactory: ScenConverterFactory) {

    fun processScenario(scenFe: ScenarioFe, dataType: String): String {
        return when (ScenarioTypes.valueOf(scenFe.typeName)) {
            ScenarioTypes.RANGE -> handleScenRange(handler, scenFe, dataType)
            ScenarioTypes.MINMAX -> handleScanMinMax(handler, scenFe, dataType)
            else -> throw ItemNotFoundException("Could not find scenario type in StatsProcessApi.")
        }
    }

    private fun handleScenRange(handler: StatsProcessHandler, scenFe: ScenarioFe, dataType: String): String {
        val converter = converterFactory.getConverter(ScenarioTypes.RANGE)
        val scenBe = converter.feRequestToBe(scenFe)
        return handler.handleProcess(scenBe, dataType)
    }

    private fun handleScanMinMax(handler: StatsProcessHandler, scenFe: ScenarioFe, dataType: String): String {
        val converter = converterFactory.getConverter(ScenarioTypes.MINMAX)
        val scenBe = converter.feRequestToBe(scenFe)
        return handler.handleProcess(scenBe, dataType)
    }
}
