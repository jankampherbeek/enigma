/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */
package com.radixpro.enigma.xchg.domain

import java.io.Serializable

/**
 * DTO for a specific Geographic coordinate.
 */
data class GeographicCoordinate(val degrees: Int, val minutes: Int, val seconds: Int, val direction: String, val value: Double) : Serializable