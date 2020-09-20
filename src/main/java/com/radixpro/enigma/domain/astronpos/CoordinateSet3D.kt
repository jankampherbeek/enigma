/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.astronpos

/**
 * Coordinates including distance.
 */
class CoordinateSet3D(mainCoord: Double,
                      deviation: Double,
                      val distance: Double) : CoordinateSet(mainCoord, deviation)