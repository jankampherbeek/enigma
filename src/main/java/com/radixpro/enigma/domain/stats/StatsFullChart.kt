/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.stats

import com.radixpro.enigma.domain.astronpos.CalculatedChart

/**
 * Calculated chart as used in statistics.
 */
data class StatsFullChart(val ident: String, val info: String, val chart: CalculatedChart)
