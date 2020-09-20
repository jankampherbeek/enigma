/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.references

enum class MidpointTypes(val id: Int,
                         val angle: Double,
                         val fullRbId: String) {
    FULL(1, 360.0, "midpoints.full"),
    QUARTER(2, 90.0, "midpoints.quarter"),
    EIGHT(3, 45.0, "midpoints.eight"),
    SIXTEENTH(4, 22.5, "midpoints.sixteenth");

    fun getMidpointForId(id: Int): MidpointTypes? {
        for (midpoint in values()) {
            if (midpoint.id == id) {
                return midpoint
            }
        }
        return null
        // TODO Release 2020.2: throw exception if aspect is not found
    }
}