/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.stats

import com.radixpro.enigma.references.DistanceTypes
import com.radixpro.enigma.references.MinMaxType

/**
 * Definition of minimum/maximum positions for statistical research.
 */
data class StatsMinMax(override val minMaxType: MinMaxType,
                       override val distanceType: DistanceTypes,
                       override val separation: Double) : IStatsMinMaxRelations