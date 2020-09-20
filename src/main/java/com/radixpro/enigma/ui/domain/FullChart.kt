/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.ui.domain

import com.radixpro.enigma.domain.astronpos.CalculatedChart
import com.radixpro.enigma.xchg.domain.FullChartInputData

open class FullChart(val chartData: FullChartInputData, val calculatedChart: CalculatedChart)