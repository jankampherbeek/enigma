/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.statistics.core.InputDataSet
import com.radixpro.enigma.statistics.core.StatsProject
import com.radixpro.enigma.statistics.persistency.ProjectDataDao

/**
 * Read datafile in csv format, convert it to JSON and save it in the projectfolder.
 */
class ProjectDataHandler(private val projectDataDao: ProjectDataDao, private val projectHandler: StatsProjHandler) {

    fun readChartData(projName: String): InputDataSet {
        val project = projectHandler.read(projName) as StatsProject
        return projectDataDao.readChartDataData(project)
    }


}