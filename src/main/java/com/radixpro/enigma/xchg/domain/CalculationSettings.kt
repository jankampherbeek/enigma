/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */
package com.radixpro.enigma.xchg.domain

import com.radixpro.enigma.domain.config.Configuration
import com.radixpro.enigma.references.*
import java.util.*

class CalculationSettings {
    val celBodies: List<CelestialObjects>
    val houseSystem: HouseSystems
    val ayanamsha: Ayanamshas
    val isSidereal: Boolean
    val isTopocentric: Boolean

    constructor(celBodies: List<CelestialObjects>, houseSystem: HouseSystems, ayanamsha: Ayanamshas,
                sidereal: Boolean, topocentric: Boolean) {
        this.celBodies = celBodies
        this.houseSystem = houseSystem
        this.ayanamsha = ayanamsha
        isSidereal = sidereal
        isTopocentric = topocentric
    }

    constructor(configuration: Configuration) {
        celBodies = constructCelObjects(configuration)
        houseSystem = configuration.astronConfiguration.houseSystem
        ayanamsha = configuration.astronConfiguration.ayanamsha
        isSidereal = configuration.astronConfiguration.eclipticProjection === EclipticProjections.SIDEREAL
        isTopocentric = configuration.astronConfiguration.observerPosition === ObserverPositions.TOPOCENTRIC
    }

    private fun constructCelObjects(configuration: Configuration): List<CelestialObjects> {
        val celObjects: MutableList<CelestialObjects> = ArrayList()
        val confCelObjects = configuration.astronConfiguration.celObjects
        for ((celObject) in confCelObjects) {
            celObjects.add(celObject)
        }
        return celObjects
    }
}