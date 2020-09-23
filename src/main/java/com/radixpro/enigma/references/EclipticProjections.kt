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
import java.util.*

enum class EclipticProjections(val id: Int, val nameForRB: String) {
    TROPICAL(1, "eclipticprojections.tropical"),
    SIDEREAL(2, "eclipticprojections.sidereal");

    companion object {
        private val LOG = Logger.getLogger(EclipticProjections::class.java)

        @JvmStatic
        fun getProjectionForId(id: Int): EclipticProjections {
            for (eclipticProjection in values()) {
                if (eclipticProjection.id == id) {
                    return eclipticProjection
                }
            }
            LOG.error("Could not find EclipticPRojection for id : $id. Returned TROPICAL instead.")
            return TROPICAL
        }

        @JvmStatic
        val observableList: ObservableList<String>
            get() {
                val eclipticalProjNames: MutableList<String> = ArrayList()
                for (eclipticProjection in values()) {
                    eclipticalProjNames.add(Rosetta.getText(eclipticProjection.nameForRB))
                }
                return FXCollections.observableArrayList(eclipticalProjNames)
            }

        @JvmStatic
        val indexMappings: IndexMappingsList
            get() {
                val idList: MutableList<IndexMapping> = ArrayList()
                var runningIndex = 0
                for (eclipticProjection in values()) {
                    idList.add(IndexMapping(runningIndex++, eclipticProjection.id))
                }
                return IndexMappingsList(idList)
            }
    }
}