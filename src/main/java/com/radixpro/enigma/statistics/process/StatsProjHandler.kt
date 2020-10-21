/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.share.process.PropertyHandler
import com.radixpro.enigma.statistics.api.xchg.ApiResult
import com.radixpro.enigma.statistics.core.IStatsProject
import com.radixpro.enigma.statistics.core.StatsProject
import com.radixpro.enigma.statistics.persistency.StatsProjDao

class StatsProjHandler(private val statsProjDao: StatsProjDao, private val propHandler: PropertyHandler, private val controlDataCharts: ControlDataCharts) {

    val location = propHandler.retrieve("projdir")[0].value

    fun saveProject(project: StatsProject): ApiResult {
        statsProjDao.save(project, location)
        createControlFiles(project)
        // TODO handle exceptions
        return ApiResult(true, "OK")
    }

    fun read(projName: String): IStatsProject {
        return statsProjDao.read(projName, location)
    }

    fun readAllNames(): List<String> {
        return statsProjDao.readAllNames(location)
    }

    private fun createControlFiles(project: StatsProject) {
        controlDataCharts.createFile(project);
    }

}