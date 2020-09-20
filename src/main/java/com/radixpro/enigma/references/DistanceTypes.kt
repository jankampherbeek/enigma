/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.references

import com.radixpro.enigma.shared.exceptions.UnknownIdException

/**
 * Types of distances to be used in statistical research.
 */
enum class DistanceTypes(val id: Int,
                         val rbName: String,
                         val rbDescr: String) {
    RADIUS(1, "distancetype.radius.name", "distancetype.radius.descr"),
    LATITUDE(2, "distancetype.latitude.name", "distancetype.latitude.descr"),
    DECLINATION(3, "distancetype.declination.name", "distancetype.declination.descr"),
    ALTITUDE(4, "distancetype.altitude.name", "distancetype.altitude.descr");

    companion object {
        @JvmStatic
        @Throws(UnknownIdException::class)
        fun getDistanceTypeForId(id: Int): DistanceTypes {
            for (type in values()) {
                if (type.id == id) {
                    return type
                }
            }
            throw UnknownIdException("Tried to read DistanceType with invalid id : $id")
        }
    }
}