/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.handlers

import com.radixpro.enigma.be.persistency.PropertyDao
import com.radixpro.enigma.be.persistency.StatsProjDao
import com.radixpro.enigma.domain.stats.StatsProject

class StatsProjHandler(private val statsProjDao: StatsProjDao, private val propDao: PropertyDao) {

    fun saveProject(project: StatsProject): String {
        val location = propDao.read("projdir")[0].value
        statsProjDao.save(project, location)
        // TODO handle exceptions
        return "OK"
    }

}