/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.config

import com.radixpro.enigma.references.Ayanamshas
import com.radixpro.enigma.references.EclipticProjections
import com.radixpro.enigma.references.HouseSystems
import com.radixpro.enigma.references.ObserverPositions

class BaseAstronConfig(val houseSystem: HouseSystems,
                       val ayanamsha: Ayanamshas,
                       val eclipticProjection: EclipticProjections,
                       val observerPosition: ObserverPositions)