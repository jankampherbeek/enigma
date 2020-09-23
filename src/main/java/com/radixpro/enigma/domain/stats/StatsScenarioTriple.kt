/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.stats

import com.radixpro.enigma.references.StatsScenarioTypes

/**
 * Scenario for statistical research into relations between two objects.
 */
data class StatsScenarioTriple(override val rbName: String,
                               override val rbDescription: String,
                               override val players: List<Player>,
                               override val scenarioType: StatsScenarioTypes,
                               val tripleRelation: IStatsTripleRelations) : IStatsScenario