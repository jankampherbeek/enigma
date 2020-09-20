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
    DUAL(1, "statsscenariotype.dual.name", "statsscenariotype.dual.descr"),
    TRIPLE(2, "statsscenariotype.triple.name", "statsscenariotype.triple.descr"),
    MULTIPLE_SINGLE(3, "statsscenariotype.multiplesingle.name", "statsscenariotype.multiplesingle.descr"),
    RANGE(4, "statsscenariotype.range.name", "statsscenariotype.range.descr"),
    MINMAX(5, "statsscenariotype.minmax.name", "statsscenariotype.minmax.descr");

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