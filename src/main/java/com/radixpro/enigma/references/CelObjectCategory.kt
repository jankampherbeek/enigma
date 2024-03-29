/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.references

/**
 * Enum with categories for celestial objects.
 */
enum class CelObjectCategory(val id: Int,
                             val nameForRB: String) {
    UNKNOWN(0, "celobjectcat.unknown"),
    CLASSICS(1, "celobjectcat.classic"),
    MODERN(2, "celobjectcat.modern"),
    EXTRA_PLUT(3, "celobjectcat.extraplut"),
    ASTEROIDS(4, "celobjectcat.asteroids"),
    CENTAURS(5, "celobjectcat.centaurs"),
    INTERSECTIONS(6, "celobjectcat.intersections"),
    HYPOTHETS(7, "celobjectcat.hypothets");

}