/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.stats

import com.radixpro.enigma.references.AspectTypes

/**
 * Definition of aspects for statistical research.
 */
class StatsAspects(val aspectType: AspectTypes,
                   override val orb: Double) : IStatsDualRelations