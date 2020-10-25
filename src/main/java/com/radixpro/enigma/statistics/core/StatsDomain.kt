/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.statistics.core

import com.radixpro.enigma.astronomy.ui.domain.CelObjects
import com.radixpro.enigma.astronomy.ui.domain.MundanePoints
import com.radixpro.enigma.domain.astronpos.CalculatedChart
import com.radixpro.enigma.domain.input.ChartInputData
import com.radixpro.enigma.statistics.ui.domain.ScenarioTypes
import com.radixpro.enigma.statistics.ui.domain.StatsRangeTypes

interface ScenarioBe {
    val name: String
    val description: String
    val projectName: String
    val scenarioType: ScenarioTypes
}

data class ScenRangeBe(override val name: String,
                       override val description: String,
                       override val projectName: String,
                       override val scenarioType: ScenarioTypes,
                       val rangeType: StatsRangeTypes,
                       val celObjects: List<CelObjects>,
                       val mundanePoints: List<MundanePoints>) : ScenarioBe

data class DataFileDescription(val name: String, val description: String, val nrOfRecords: Int)

data class StatsFullChart(val ident: String, val info: String, val chart: CalculatedChart)

data class InputDataSet(val name: String,
                        val description: String,
                        val origFileName: String,
                        val dateTime: String,
                        val inputData: List<ChartInputData>)