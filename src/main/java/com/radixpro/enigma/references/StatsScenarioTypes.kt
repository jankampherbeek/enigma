/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.references

import com.radixpro.enigma.shared.exceptions.UnknownIdException

/**
 * Scenario types for statistical research.
 */
enum class StatsScenarioTypes(val id: Int,
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
        fun getScenarioTypeForId(id: Int): StatsScenarioTypes {
            for (type in values()) {
                if (type.id == id) {
                    return type
                }
            }
            throw UnknownIdException("Tried to read StatsScenarioType with invalid id : $id")
        }
    }
}