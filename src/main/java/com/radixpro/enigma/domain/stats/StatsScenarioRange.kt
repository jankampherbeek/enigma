/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.stats

import com.radixpro.enigma.references.StatsRangeTypes
import com.radixpro.enigma.references.StatsScenarioTypes

/**
 * Scenario for statistical research into ranges (areas).
 */
data class StatsScenarioRange(val rbName: String,
                              val rbDescription: String,
                              val players: List<Player>,
                              val scenarioType: StatsScenarioTypes,
                              val rangeType: StatsRangeTypes,
                              val selection: List<Int>) : StatsScenario()