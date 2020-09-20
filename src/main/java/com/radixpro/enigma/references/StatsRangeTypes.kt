/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.references

import com.radixpro.enigma.shared.exceptions.UnknownIdException

/**
 * Range types (areas) to be used in statistical research.
 */
enum class StatsRangeTypes(val id: Int,
                           val rbName: String) {
    SIGNS(1, "statsrangetype.signs"),
    HOUSES(2, "statsrangetype.houses"),
    DECANS(3, "statsrangetype.decans");

    companion object {
        @JvmStatic
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