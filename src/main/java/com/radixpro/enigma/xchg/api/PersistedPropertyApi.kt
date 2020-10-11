/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */
package com.radixpro.enigma.xchg.api

import com.radixpro.enigma.be.persistency.PropertyDao
import com.radixpro.enigma.shared.FailFastHandler
import com.radixpro.enigma.shared.Property
import com.radixpro.enigma.shared.exceptions.DatabaseException
import java.util.*

class PersistedPropertyApi(private val dao: PropertyDao) {

    // TODO Create handler and do not access dao directly
    fun insert(property: Property) {
        try {
            dao.insert(property)
        } catch (de: DatabaseException) {
            FailFastHandler().terminate(de.message)
        }
    }

    fun update(property: Property) {
        try {
            dao.update(property)
        } catch (de: DatabaseException) {
            FailFastHandler().terminate(de.message)
        }
    }

    fun read(key: String): List<Property?> {
        var propList: List<Property> = ArrayList()
        try {
            propList = dao.read(key)
        } catch (e: Exception) {
            FailFastHandler().terminate(e.message)
        }
        return propList
    }
}