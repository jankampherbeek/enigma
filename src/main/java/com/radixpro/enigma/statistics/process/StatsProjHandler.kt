/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.be.persistency.PropertyDao
import com.radixpro.enigma.be.persistency.StatsProjDaoH2
import com.radixpro.enigma.statistics.core.IStatsProject
import com.radixpro.enigma.statistics.core.StatsProject

class StatsProjHandler(private val statsProjDaoH2: StatsProjDaoH2, private val propDao: PropertyDao, private val controlDataCharts: ControlDataCharts) {

    val location = propDao.read("projdir")[0].value

    fun saveProject(project: StatsProject): String {
        statsProjDaoH2.save(project, location)
        createControlFiles(project)
        // TODO handle exceptions
        return "OK"
    }

    fun read(projName: String): IStatsProject {
        return statsProjDaoH2.read(projName, location)
    }

    fun readAllNames(): List<String> {
        return statsProjDaoH2.readAllNames(location)
    }

    private fun createControlFiles(project: StatsProject) {
        controlDataCharts.createFile(project);
    }

}