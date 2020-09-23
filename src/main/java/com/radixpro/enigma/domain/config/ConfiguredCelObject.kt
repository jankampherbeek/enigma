/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.config

import com.radixpro.enigma.references.CelestialObjects
import java.io.Serializable

data class ConfiguredCelObject(val celObject: CelestialObjects,
                               val glyph: String,
                               val orbPercentage: Int,
                               val isShowInDrawing: Boolean) : Serializable