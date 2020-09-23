/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */
package com.radixpro.enigma.xchg.api

import com.radixpro.enigma.be.persistency.ConfigurationDao
import com.radixpro.enigma.domain.config.Configuration
import com.radixpro.enigma.shared.FailFastHandler
import com.radixpro.enigma.shared.exceptions.DatabaseException
import org.apache.log4j.Logger
import java.util.*

class PersistedConfigurationApi(private val dao: ConfigurationDao) {
    fun insert(configuration: Configuration): Int {
        var configId = -1
        try {
            configId = dao.insert(configuration)
        } catch (de: DatabaseException) {
            FailFastHandler().terminate(de.message)
        }
        return configId
    }

    fun update(configuration: Configuration) {
        try {
            dao.update(configuration)
        } catch (de: DatabaseException) {
            FailFastHandler().terminate(de.message)
        }
    }

    fun delete(id: Int) {
        try {
            dao.delete(id)
        } catch (e: Exception) {
            FailFastHandler().terminate(e.message)
        }
    }

    fun read(id: Int): List<Configuration?> {
        var configs: List<Configuration?> = ArrayList()
        try {
            configs = dao.read(id)
        } catch (e: Exception) {
            FailFastHandler().terminate(e.message)
        }
        return configs
    }

    fun search(searchName: String): List<Configuration?> {
        var configs: List<Configuration?> = ArrayList()
        try {
            configs = dao.search(searchName)
        } catch (de: Exception) {
            FailFastHandler().terminate(de.message)
        }
        return configs
    }

    fun readAll(): List<Configuration?> {
        var configs: List<Configuration?> = ArrayList()
        try {
            configs = dao.readAll()
            LOG.info("Read configurations, size of list : " + configs.size)
        } catch (e: Exception) {
            FailFastHandler().terminate(e.message)
        }
        return configs
    }

    companion object {
        private val LOG = Logger.getLogger(PersistedConfigurationApi::class.java)
    }
}