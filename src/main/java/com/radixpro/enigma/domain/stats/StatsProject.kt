/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.stats

import com.radixpro.enigma.domain.config.BaseAstronConfig

/**
 * Project for statistical research.
 */
data class StatsProject(val name: String,
                        val description: String,
                        val baseAstronConfig: BaseAstronConfig,
                        val scenarios: List<IStatsScenario>)