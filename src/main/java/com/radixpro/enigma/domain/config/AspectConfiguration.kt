/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.config

import com.radixpro.enigma.references.AspectOrbStructures
import java.io.Serializable

data class AspectConfiguration(var aspects: List<ConfiguredAspect>,
                               val baseOrb: Double,
                               val orbStructure: AspectOrbStructures,
                               val isDrawInOutGoing: Boolean) : Serializable