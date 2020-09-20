/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.references

import com.radixpro.enigma.shared.exceptions.UnknownIdException

/**
 * Great circles used for mesurement of celestial positions.
 */
enum class GreatCircles(val id: Int,
                        val rbName: String) {
    ECLIPTIC(1, "greatcircles.ecliptic"),
    EQUATOR(2, "greatcircles.equator"),
    HORIZON(3, "greatcircles.horizon"),
    ALTITUDE(4, "greatcircles.altitude");

    companion object {
        @JvmStatic
        @Throws(UnknownIdException::class)
        fun getGreatCircleForId(id: Int): GreatCircles {
            for (circle in values()) {
                if (circle.id == id) {
                    return circle
                }
            }
            throw UnknownIdException("Tried to read GreatCircles with invalid id : $id")
        }
    }
}