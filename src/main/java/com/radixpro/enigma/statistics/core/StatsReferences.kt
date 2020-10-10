/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.statistics.core

import com.radixpro.enigma.Rosetta
import com.radixpro.enigma.shared.exceptions.UnknownIdException
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import java.util.*

enum class ScenarioTypes(val id: Int,
                         val rbName: String,
                         val rbDescr: String) {
    RANGE(1, "statsscenariotype.range.name", "statsscenariotype.range.descr"),
    MINMAX(2, "statsscenariotype.minmax.name", "statsscenariotype.minmax.descr"),
    COUNTS(3, "statsscenariotype.counts.name", "statsscenariotype.counts.descr"),
    COMBI(4, "statsscenariotype.combi.name", "statsscenariotype.combi.descr"),
    PROG(5, "statsscenariotype.prog.name", "statsscenariotype.prog.descr"),
//    DUAL(6, "statsscenariotype.dual.name", "statsscenariotype.dual.descr"),
//    TRIPLE(7, "statsscenariotype.triple.name", "statsscenariotype.triple.descr"),
//    MULTIPLE_SINGLE(8, "statsscenariotype.multiplesingle.name", "statsscenariotype.multiplesingle.descr")
    ;

    companion object {
        @JvmStatic
        @Throws(UnknownIdException::class)
        fun getScenarioTypeForId(id: Int): ScenarioTypes {
            for (type in values()) {
                if (type.id == id) {
                    return type
                }
            }
            throw UnknownIdException("Tried to read StatsScenarioType with invalid id : $id")
        }
    }
}


enum class StatsRangeTypes(val id: Int,
                           val rbName: String) {
    SIGNS(1, "statsrangetype.signs"),
    HOUSES(2, "statsrangetype.houses");

    companion object {
        @Throws(UnknownIdException::class)
        fun getRangeTypeForId(id: Int): StatsRangeTypes {
            for (type in values()) {
                if (type.id == id) {
                    return type
                }
            }
            throw UnknownIdException("Tried to read StatsRangeType with invalid id : $id")
        }
    }
}


enum class DataInputFormats(val rbKey: String) {
    UNDEFINED("datainputformat.undefined"),
    CSV_CHARTS_STANDARD("datainputformat.csv.chart.standard"),
    CSV_EVENTS_STANDARD("datainputformat.csv.event.standard");

    companion object {
        @JvmStatic
        val observableList: ObservableList<String>
            get() {
                val formatKeys: MutableList<String> = ArrayList()
                for (format in values()) {
                    formatKeys.add(Rosetta.getText(format.rbKey))
                }
                return FXCollections.observableArrayList(formatKeys)
            }

        @JvmStatic
        fun formatForName(name: String): DataInputFormats {
            for (format in values()) {
                if (Rosetta.getText(format.rbKey) == name) {
                    return format
                }
            }
            return UNDEFINED
        }
    }
}