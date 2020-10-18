/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.share.process

import com.radixpro.enigma.share.persistency.PropertyPersister
import com.radixpro.enigma.share.persistency.PropertyRetriever
import com.radixpro.enigma.shared.FailFastHandler
import com.radixpro.enigma.shared.Property
import com.radixpro.enigma.shared.exceptions.DatabaseException

interface PropertyHandler {
    fun add(property: Property)
    fun change(property: Property)
    fun retrieve(searchKey: String): List<Property>
}


class GlobalPropertyHandler(val persister: PropertyPersister, val retriever: PropertyRetriever) : PropertyHandler {

    override fun add(property: Property) {
        try {
            persister.add(property)
        } catch (de: DatabaseException) {
            FailFastHandler().terminate(de.message)
        }
    }

    override fun change(property: Property) {
        try {
            persister.change(property)
        } catch (de: DatabaseException) {
            FailFastHandler().terminate(de.message)
        }
    }

    override fun retrieve(searchKey: String): List<Property> {
        return retriever.read(searchKey)
    }


}