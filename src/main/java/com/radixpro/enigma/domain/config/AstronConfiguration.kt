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
import java.io.Serializable

/**
 * Astronomical parts of the configuration.
 * TODO use BaseAstronConfig, removed mutability
 * @param houseSystem        Selected house system.
 * @param ayanamsha          Selected ayanamasha, Ayanamshas.NONE for tropical zodiac.
 * @param eclipticProjection Tropical or sidereal zodiac.
 * @param observerPosition   Positionof the observer.
 * @param celObjects         The supported celestial objects.
 */
class AstronConfiguration(var houseSystem: HouseSystems,
                          var ayanamsha: Ayanamshas,
                          var eclipticProjection: EclipticProjections,
                          var observerPosition: ObserverPositions,
                          var celObjects: List<ConfiguredCelObject>) : Serializable