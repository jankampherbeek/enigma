/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.statistics.core.StatsFullChart

interface StatsCalculator {
    val positions: StatsFullChart;
}


class AllPointsCalculator() : StatsCalculator {
    override val positions: StatsFullChart
        get() = TODO("Not yet implemented")

}