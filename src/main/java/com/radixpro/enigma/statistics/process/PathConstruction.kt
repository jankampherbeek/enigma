/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.share.api.PropertyApi
import com.radixpro.enigma.statistics.core.StatsProject


class StatsPathConstructor(val propApi: PropertyApi) {

    val separator: String = java.io.File.separator

    fun pathForProject(projectName: String): String {
        return "${pathForProjectDir(projectName)}proj_$projectName.json"
    }

    fun pathForProjectDir(projectName: String): String {
        val projDir = propApi.read("projdir")[0]
        return "${projDir.value}${separator}proj$separator$projectName$separator"
    }

    fun pathForScenario(scenarioName: String, projectName: String): String {
        val projDir = propApi.read("projdir")[0]
        return "${projDir.value}${separator}proj$separator$projectName${separator}scen_${scenarioName}.json"
    }

    fun pathForJsonResult(scenarioName: String, projectName: String): String {
        val projDir = propApi.read("projdir")[0]
        return "${projDir.value}${separator}proj$separator$projectName${separator}results_${scenarioName}.json"
    }

    fun pathForCsvResult(scenarioName: String, projectName: String): String {
        val projDir = propApi.read("projdir")[0]
        return "${projDir.value}${separator}proj$separator$projectName${separator}results_${scenarioName}.csv"
    }

    fun pathForProjectData(project: StatsProject): String {
        val projDir = propApi.read("projdir")[0]
        val projName = project.name
        val dataName = project.dataFileName
        return "${projDir.value}${separator}proj$separator$projName${separator}in_${dataName}.json"
    }

    fun pathForControlData(project: StatsProject): String {
        val projDir = propApi.read("projdir")[0]
        val projName = project.name
        val dataName = project.dataFileName
        return "${projDir.value}${separator}proj$separator$projName${separator}ctrl_${dataName}.json"
    }

    fun pathForGlobalData(): String {
        val projDir = propApi.read("projdir")[0]
        return "${projDir.value}${separator}data$separator"
    }


}