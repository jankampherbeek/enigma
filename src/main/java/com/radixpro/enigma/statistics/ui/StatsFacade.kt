/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.ui

import com.radixpro.enigma.statistics.api.ScenGeneralApi
import com.radixpro.enigma.statistics.api.StatsProcessApi
import com.radixpro.enigma.statistics.api.StatsProjApi
import com.radixpro.enigma.statistics.api.xchg.ApiResult
import com.radixpro.enigma.statistics.ui.domain.ScenarioFe
import com.radixpro.enigma.statistics.ui.domain.StatsProjectFe

class StatsFacade(private val projApi: StatsProjApi,
                  private val processApi: StatsProcessApi,
                  private val scenGeneralApi: ScenGeneralApi) {

    fun saveProject(projectFe: StatsProjectFe): ApiResult {
        return projApi.save(projectFe)
    }

    fun readScenarioNames(projName: String): List<String> {
        return scenGeneralApi.readAllNames(projName)
    }

    fun writeScenario(scenarioFe: ScenarioFe): ApiResult {
        return scenGeneralApi.save(scenarioFe)
    }

    fun readScenario(scenName: String, projName: String): ScenarioFe {
        return scenGeneralApi.read(scenName, projName)
    }

    fun processScenario(scenFe: ScenarioFe, dataType: String): String {
        return processApi.processScenario(scenFe, dataType)
    }

}