/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */
package com.radixpro.enigma.share.api

import com.radixpro.enigma.share.process.PropertyHandler
import com.radixpro.enigma.shared.Property

interface PropertyApi {
    fun add(property: Property)
    fun change(property: Property)
    fun read(searchKey: String): List<Property>
}

class GlobalPropertyApi(val handler: PropertyHandler) : PropertyApi {
    override fun add(property: Property) {
        handler.add(property)
    }

    override fun change(property: Property) {
        handler.change(property)
    }

    override fun read(searchKey: String): List<Property> {
        return handler.retrieve(searchKey)
    }

}

//
//class PersistedPropertyApi(private val dao: PropertyDao) {
//
//    // TODO Create handler and do not access dao directly
//    fun insert(property: Property) {
//        try {
//            dao.insert(property)
//        } catch (de: DatabaseException) {
//            FailFastHandler().terminate(de.message)
//        }
//    }
//
//    fun update(property: Property) {
//        try {
//            dao.update(property)
//        } catch (de: DatabaseException) {
//            FailFastHandler().terminate(de.message)
//        }
//    }
//
//    fun read(key: String): List<Property?> {
//        var propList: List<Property> = ArrayList()
//        try {
//            propList = dao.read(key)
//        } catch (e: Exception) {
//            FailFastHandler().terminate(e.message)
//        }
//        return propList
//    }
//}