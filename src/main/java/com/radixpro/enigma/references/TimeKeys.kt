/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.references

import com.radixpro.enigma.Rosetta
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import java.util.*

/**
 * Time keys for progressions and directions.
 * TODO create abstract parent for enums with lookups, e.g. for TimeKeys, Ratings, ObserverPositions, EclipticProjections etc.
 */
enum class TimeKeys(val id: Int, val nameForRB: String) {
    NOT_DEFINED(0, "timekeys.notdefined"),
    REAL_SECUNDARY_SUN(1, "timekeys.realsecsun"),
    NAIBOD(2, "timekeys.naibod");

    fun getTimeKeyForId(id: Int): TimeKeys {
        for (timeKey in values()) {
            if (timeKey.id == id) {
                return timeKey
            }
        }
        return NOT_DEFINED
    }

    fun timeKeyForName(keyName: String): TimeKeys {
        val rosetta = Rosetta.getRosetta()
        for (timeKey in values()) {
            if (rosetta.getText(timeKey.nameForRB) == keyName) {
                return timeKey
            }
        }
        return NOT_DEFINED
    }

    val observableList: ObservableList<String>
        get() {
            val rosetta = Rosetta.getRosetta()
            val keyNames: MutableList<String> = ArrayList()
            for (timeKey in values()) {
                if (timeKey != NOT_DEFINED) keyNames.add(rosetta.getText(timeKey.nameForRB))
            }
            return FXCollections.observableArrayList(keyNames)
        }
}