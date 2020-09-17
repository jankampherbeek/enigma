/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.stats

import com.radixpro.enigma.xchg.domain.IChartPoints

/**
 * A celestial object or point that can be analyzed statistically.
 */
class Player(val point: IChartPoints,
             val position: Double)