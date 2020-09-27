/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.persistency

import com.radixpro.enigma.shared.Property
import com.radixpro.enigma.shared.exceptions.DatabaseException
import java.sql.SQLException
import java.util.*

class PropertyDao() : DaoParent() {

    private val appDb = AppDb.initAppDb("prod")

    @Throws(DatabaseException::class)
    fun insert(newProp: Property) {
        val con = appDb.connection
        val insertProperties = "INSERT into properties(value, key) values(?, ?);"
        try {
            con.prepareStatement(insertProperties).use { pStmtProperties ->
                pStmtProperties.setString(1, newProp.value)
                pStmtProperties.setString(2, newProp.key)
                val result = pStmtProperties.executeUpdate()
                if (result != 1) {
                    con.rollback()
                    throw DatabaseException("Could not insert property " + newProp.key)
                }
            }
        } catch (throwables: SQLException) {
            try {
                con.rollback()
            } catch (e: SQLException) {
                LOG.error("SQLException when trying to rollback : " + e.message)
            }
            throw DatabaseException("SQLException when inserting property " + newProp.key + ". Exception :  " + throwables.message)
        } finally {
            appDb.closeConnection()
        }
    }

    /**
     * Updates the value of a property.
     *
     * @param updateProp Property with new content and he id to search for.
     * @throws DatabaseException is thrown for any database error.
     */
    @Throws(DatabaseException::class)
    fun update(updateProp: Property) {
        val con = appDb.connection
        val updateProperties = "UPDATE properties SET value = ?  WHERE key = ? ;"
        try {
            con.prepareStatement(updateProperties).use { pStmtProperties ->
                pStmtProperties.setString(1, updateProp.value)
                pStmtProperties.setString(2, updateProp.key)
                val result = pStmtProperties.executeUpdate()
                if (result != 1) {
                    con.rollback()
                    throw DatabaseException("Could not update property " + updateProp.key)
                }
            }
        } catch (throwables: SQLException) {
            try {
                con.rollback()
            } catch (e: SQLException) {
                LOG.error("SQLException when trying to rollback : " + e.message)
            }
            throw DatabaseException("SQLException when updating property " + updateProp.key + ". Exception :  " + throwables.message)
        } finally {
            appDb.closeConnection()
        }
    }

    /**
     * Find property for a specific key.
     *
     * @param key the key to search for.
     * @return A list with properties. The list should contain one or zero properties.
     */
    fun read(key: String): List<Property> {
        val properties: MutableList<Property> = ArrayList()
        val queryProperties = "SELECT key, value FROM properties where key = ?;"
        val con = appDb.connection
        try {
            con.prepareStatement(queryProperties).use { pStmt ->
                pStmt.setString(1, key)
                pStmt.executeQuery().use { rsProperties ->
                    while (rsProperties.next()) {
                        properties.add(Property(key, rsProperties.getString("value")))
                    }
                }
            }
        } catch (throwables: SQLException) {
            LOG.error("SQLException when reading property for key: " + key + " . Msg: " + throwables.message)
        }
        return properties
    }
}