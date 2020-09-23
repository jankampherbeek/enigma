/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.xchg.api.settings

import com.radixpro.enigma.references.Ayanamshas
import com.radixpro.enigma.xchg.domain.IChartPoints

class ProgSettings(points: List<IChartPoints>, ayanamsha: Ayanamshas, sidereal: Boolean, topocentric: Boolean) : ICalcSettings {
    override val points: List<IChartPoints>
    override val ayanamsha: Ayanamshas
    override val isSidereal: Boolean
    val isTopocentric: Boolean

    init {
        require(points.isNotEmpty())
        this.points = points
        this.ayanamsha = ayanamsha
        isSidereal = sidereal
        isTopocentric = topocentric
    }
}