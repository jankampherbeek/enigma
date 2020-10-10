/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.statistics.core

import com.radixpro.enigma.domain.astronpos.CalculatedChart
import com.radixpro.enigma.domain.input.ChartInputData
import com.radixpro.enigma.xchg.domain.IChartPoints

interface Scenario {
    val name: String
    val description: String
    val players: List<IChartPoints>
    val scenarioType: ScenarioTypes
}

data class ScenarioRange(override val name: String,
                         override val description: String,
                         override val players: List<IChartPoints>,
                         override val scenarioType: ScenarioTypes,
                         val rangeType: StatsRangeTypes,
                         val selection: List<Int>) : Scenario

data class DataFileDescription(val name: String, val description: String, val nrOfRecords: Int)

data class StatsFullChart(val ident: String, val info: String, val chart: CalculatedChart)

data class InputDataSet(val name: String,
                        val description: String,
                        val origFileName: String,
                        val dateTime: String,
                        val inputData: List<ChartInputData>)