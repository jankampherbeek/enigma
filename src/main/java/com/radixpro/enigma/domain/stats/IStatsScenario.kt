/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.stats

import com.radixpro.enigma.references.StatsScenarioTypes

/**
 * Interface for statistical scenario's
 * @Deprecated
 */
interface IStatsScenario {
    val rbName: String
    val rbDescription: String
    val players: List<Player>
    val scenarioType: StatsScenarioTypes
}