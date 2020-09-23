/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.stats

import com.radixpro.enigma.references.StatsScenarioTypes

/**
 * Scenario for statistical research into minimum and maximum positions.
 */
data class StatsScenarioMinMax(override val rbName: String,
                               override val rbDescription: String,
                               override val players: List<Player>) : IStatsScenario {
    override val scenarioType: StatsScenarioTypes
        get() = StatsScenarioTypes.MINMAX
}