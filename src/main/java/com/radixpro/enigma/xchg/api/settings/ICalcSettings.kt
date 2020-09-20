/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.xchg.api.settings

import com.radixpro.enigma.references.Ayanamshas
import com.radixpro.enigma.xchg.domain.IChartPoints

interface ICalcSettings {
    val points: List<IChartPoints>
    val ayamsha: Ayanamshas?
    val isSidereal: Boolean
}