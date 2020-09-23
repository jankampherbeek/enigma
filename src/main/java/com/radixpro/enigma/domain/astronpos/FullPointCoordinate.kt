/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.astronpos

/**
 * All coordinates for position and speed within a specific coordinatesystem.
 */
data class FullPointCoordinate(val position: CoordinateSet3D,
                               val speed: CoordinateSet3D)