/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.handlers

import com.radixpro.enigma.be.persistency.PropertyDao
import com.radixpro.enigma.be.persistency.StatsProjDao
import com.radixpro.enigma.domain.stats.IStatsProject
import com.radixpro.enigma.domain.stats.StatsProject

class StatsProjHandler(private val statsProjDao: StatsProjDao, private val propDao: PropertyDao) {

    val location = propDao.read("projdir")[0].value

    fun saveProject(project: StatsProject): String {
        statsProjDao.save(project, location)
        // TODO handle exceptions
        return "OK"
    }

    fun read(projName: String): IStatsProject {
        return statsProjDao.read(projName, location)
    }

    fun readAllNames(): List<String> {
        return statsProjDao.readAllNames(location)
    }

}