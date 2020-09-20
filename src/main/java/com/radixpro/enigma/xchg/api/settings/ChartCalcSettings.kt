/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.xchg.api.settings

import com.radixpro.enigma.references.Ayanamshas
import com.radixpro.enigma.references.EclipticProjections
import com.radixpro.enigma.references.HouseSystems
import com.radixpro.enigma.references.ObserverPositions
import com.radixpro.enigma.xchg.domain.IChartPoints

class ChartCalcSettings(val points: List<IChartPoints>,
                        val obsPos: ObserverPositions,
                        val eclProj: EclipticProjections,
                        val ayanamsha: Ayanamshas,
                        val houseSystem: HouseSystems)