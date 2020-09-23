/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.stats

import com.radixpro.enigma.references.HouseSystems
import com.radixpro.enigma.references.StatsRangeTypes

/**
 * Definition of houses for statistical research.
 */
data class StatsHouses(val houseSystem: HouseSystems,
                       override val index: Int) : IStatsRangeRelations {
    override val rangeType: StatsRangeTypes = StatsRangeTypes.HOUSES

}