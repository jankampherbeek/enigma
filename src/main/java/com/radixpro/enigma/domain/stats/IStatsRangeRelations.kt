/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.stats

import com.radixpro.enigma.references.StatsRangeTypes

/**
 * Interface for definition of the position of an object in a specific area. E.g. a house.
 */
interface IStatsRangeRelations {
    val index: Int
    val rangeType: StatsRangeTypes
}