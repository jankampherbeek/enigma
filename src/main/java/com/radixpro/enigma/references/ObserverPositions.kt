/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.references

import com.radixpro.enigma.Rosetta
import com.radixpro.enigma.xchg.domain.helpers.IndexMapping
import com.radixpro.enigma.xchg.domain.helpers.IndexMappingsList
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import org.apache.log4j.Logger
import java.io.Serializable
import java.util.*

enum class ObserverPositions(val id: Int, val nameForRB: String) : Serializable {
    GEOCENTRIC(1, "observerpositions.geocentric"),
    TOPOCENTRIC(2, "observerpositions.topocentric");

    companion object {
        private val LOG = Logger.getLogger(ObserverPositions::class.java)

        @JvmStatic
        fun getObserverPositionForId(id: Int): ObserverPositions {
            for (observerPos in values()) {
                if (observerPos.id == id) {
                    return observerPos
                }
            }
            LOG.error("Could not find ObserverPosition for id : $id. Returned GEOCENTRIC instead.")
            return GEOCENTRIC
        }

        @JvmStatic
        val observableList: ObservableList<String>
            get() {
                val rosetta = Rosetta.getRosetta()
                val observerPosNames: MutableList<String> = ArrayList()
                for (observerPosition in values()) {
                    observerPosNames.add(rosetta.getText(observerPosition.nameForRB))
                }
                return FXCollections.observableArrayList(observerPosNames)
            }

        @JvmStatic
        val indexMappings: IndexMappingsList
            get() {
                val idList: MutableList<IndexMapping> = ArrayList()
                var runningIndex = 0
                for (observerPosition in values()) {
                    idList.add(IndexMapping(runningIndex++, observerPosition.id))
                }
                return IndexMappingsList(idList)
            }
    }
}