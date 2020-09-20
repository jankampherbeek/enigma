/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.stats

import com.radixpro.enigma.references.MidpointTypes

/**
 * Definition of midpoints for statistical research.
 */
class StatsMidpoints(val midpointType: MidpointTypes,
                     override val orb: Double) : IStatsTripleRelations