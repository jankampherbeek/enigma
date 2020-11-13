/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.statistics.core.InputDataSet
import com.radixpro.enigma.statistics.persistency.ProjectDataDao

/**
 * Read datafile in csv format, convert it to JSON and save it in the projectfolder.
 */
class ProjectDataHandler(private val projectDataDao: ProjectDataDao) {

    fun readChartData(projName: String): InputDataSet {
        return projectDataDao.readChartDataData(projName)
    }


}