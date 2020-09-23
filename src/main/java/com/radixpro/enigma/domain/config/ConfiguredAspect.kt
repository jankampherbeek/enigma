/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.config

import com.radixpro.enigma.references.AspectTypes
import java.io.Serializable

data class ConfiguredAspect(val aspect: AspectTypes,
                            val orbPercentage: Int,
                            val glyph: String,
                            val isShowInDrawing: Boolean) : Serializable