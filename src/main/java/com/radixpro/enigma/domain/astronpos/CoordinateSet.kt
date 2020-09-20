/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.astronpos

/**
 * Coordinates excluding distance.
 */
open class CoordinateSet(val mainCoord: Double,
                         val deviation: Double)