/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.xchg.api

import com.radixpro.enigma.be.persistency.VersionDao
import com.radixpro.enigma.shared.exceptions.DatabaseException
import org.apache.log4j.Logger

class VersionApi(private val dao: VersionDao) {
    fun latestVersion(): String {
        return dao.readLatest()
    }

    fun defineNewVersion(newVersion: String) {
        try {
            dao.insert(newVersion)
        } catch (e: DatabaseException) {
            LOG.error(e.message)
        }
    }

    companion object {
        private val LOG = Logger.getLogger(VersionApi::class.java)
    }
}