/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.share.persistency

import com.radixpro.enigma.be.persistency.AppDb
import com.radixpro.enigma.shared.Property
import com.radixpro.enigma.shared.exceptions.DatabaseException
import org.apache.log4j.Logger
import java.sql.SQLException
import java.util.*

interface PropertyPersister {
    fun add(newProp: Property)
    fun change(updateProp: Property)
}

interface PropertyRetriever {
    fun read(searchKey: String): List<Property>
}

class GlobalPropertyPersister : PropertyPersister {
    val log: Logger = Logger.getLogger(GlobalPropertyPersister::class.java)
    val appDb: AppDb = AppDb.initAppDb("prod")

    @Throws(DatabaseException::class)
    override fun add(newProp: Property) {
        val con = appDb.connection
        val insertProperties = "INSERT into properties(value, key) values(?, ?);"
        try {
            con.prepareStatement(insertProperties).use { pStmtProperties ->
                pStmtProperties.setString(1, newProp.value)
                pStmtProperties.setString(2, newProp.key)
                val result = pStmtProperties.executeUpdate()
                if (result != 1) {
                    con.rollback()
                    throw DatabaseException("Could not insert property ${newProp.key}")
                }
            }
        } catch (throwables: SQLException) {
            try {
                con.rollback()
                log.error("Trying rollback because insert of ${newProp.key} into properties resulted in a failure.")
            } catch (e: SQLException) {
                log.error("SQLException when trying to rollback : ${e.message}")
            }
            val errorTxt = "SQLException when inserting property ${newProp.key}. Exception : ${throwables.message}"
            log.error(errorTxt)
            throw DatabaseException(errorTxt)
        } finally {
            appDb.closeConnection()
        }
    }

    @Throws(DatabaseException::class)
    override fun change(updateProp: Property) {
        val con = appDb.connection
        val updateProperties = "UPDATE properties SET value = ?  WHERE key = ? ;"
        try {
            con.prepareStatement(updateProperties).use { pStmtProperties ->
                pStmtProperties.setString(1, updateProp.value)
                pStmtProperties.setString(2, updateProp.key)
                val result = pStmtProperties.executeUpdate()
                if (result != 1) {
                    con.rollback()
                    throw DatabaseException("Could not update property ${updateProp.key}")
                }
            }
        } catch (throwables: SQLException) {
            try {
                con.rollback()
            } catch (e: SQLException) {
                log.error("Trying rollback because update of ${updateProp.key} in properties resulted in a failure.")
                log.error("SQLException when trying to rollback : ${e.message}")
            }
            val errorTxt = "SQLException when updating property ${updateProp.key}. Exception :  ${throwables.message}"
            log.error(errorTxt)
            throw DatabaseException(errorTxt)
        } finally {
            appDb.closeConnection()
        }
    }


}

class GlobalPropertyRetriever : PropertyRetriever {

    val log: Logger = Logger.getLogger(GlobalPropertyRetriever::class.java)
    val appDb: AppDb = AppDb.initAppDb("prod")

    override fun read(searchKey: String): List<Property> {
        val properties: MutableList<Property> = ArrayList()
        val queryProperties = "SELECT key, value FROM properties where key = ?;"
        val con = appDb.connection
        try {
            con.prepareStatement(queryProperties).use { pStmt ->
                pStmt.setString(1, searchKey)
                pStmt.executeQuery().use { rsProperties ->
                    while (rsProperties.next()) {
                        properties.add(Property(searchKey, rsProperties.getString("value")))
                    }
                }
            }
        } catch (throwables: SQLException) {
            log.error("SQLException when reading property for key: $searchKey. Msg: ${throwables.message}")
        }
        return properties
    }
}