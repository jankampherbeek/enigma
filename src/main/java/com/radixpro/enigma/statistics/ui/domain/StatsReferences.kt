/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.statistics.ui.domain

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
    PROG(5, "statsscenariotype.prog.name", "statsscenariotype.prog.descr");

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


enum class StatsRangeTypes(val rbKey: String) {
    SIGNS("statsrangetype.signs"),
    HOUSES("statsrangetype.houses"),
    DECANATES("statsrangetype.decanates"),
    DODECATEMORIA("statsrangetype.dodecatemoria"),
    DEGREES("statsrangetype.degrees")
}

enum class StatsMinMaxTypesFe(val rbKey: String) {
    ECLIPTIC_DISTANCE("statsminmaxtype.ecldistance"),
    DECLINATION("statsminmaxtype.declination")
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

/**
 * Reference points for statistical comparisons, currently only intersections.
 */
enum class ReferencePoints(val rbKey: String) {
    MC("stats.referencepoints.mc"),
    IC("stats.referencepoints.ic"),
    ASC("stats.referencepoints.asc"),
    DESC("stats.referencepoints.desc"),
    ZERO_ARIES("stats.referencepoints.zeroaries"),
    MEAN_NODE("stats.referencepoints.meannode"),
    TRUE_NODE("stats.referencepoints.truenode"),
    VERTEX("stats.referencepoints.vertex"),
    ANTI_VERTEX("stats.referencepoints.antivertex"),
    EASTPOINT("stats.referencepoints.eastpoint")
}