/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.persistency

import com.radixpro.enigma.shared.exceptions.DatabaseException
import org.apache.log4j.Logger
import java.sql.SQLException

class VersionDao {
    private val appDb: AppDb = AppDb.getInstance()
    fun readLatest(): String {
        val con = appDb.connection
        val query = "SELECT versionTxt FROM VERSIONS WHERE id = SELECT MAX(id) FROM VERSIONS;"
        var versionTxt = ""
        try {
            val stmt = con.createStatement()
            val rSet = stmt.executeQuery(query)
            while (rSet.next()) {
                versionTxt = rSet.getString("versiontxt")
            }
        } catch (throwables: SQLException) {
            versionTxt = "0.0"
            LOG.error("Error when reading version, assuming database is not yet installed and returning version 0.0.")
        } finally {
            appDb.closeConnection()
        }
        return versionTxt
    }

    @Throws(DatabaseException::class)
    fun insert(newVersion: String) {
        val con = appDb.connection
        val query = "INSERT INTO versions (id, versiontxt) VALUES(versionsseq.NEXTVAL, ?)"
        try {
            val pStmt = con.prepareStatement(query)
            pStmt.setString(1, newVersion)
            val rowsChanged = pStmt.executeUpdate()
            if (rowsChanged != 1) throw DatabaseException("Inserting version did not succeed. Using query : $query . Changed rows : $rowsChanged")
            con.commit()
        } catch (e: SQLException) {
            throw DatabaseException("SQLException when inserting version. Using query : " + query + " . Exeption : " + e.message)
        } finally {
            appDb.closeConnection()
        }
    }

    companion object {
        private val LOG = Logger.getLogger(VersionDao::class.java)
    }

}