/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.statistics.core

import com.radixpro.enigma.domain.input.ChartInputData
import com.radixpro.enigma.references.CelestialObjects
import com.radixpro.enigma.references.HouseSystems
import com.radixpro.enigma.references.MundanePointsAstron
import com.radixpro.enigma.statistics.ui.domain.ScenarioTypes
import com.radixpro.enigma.statistics.ui.domain.StatsRangeTypes
import com.radixpro.enigma.xchg.domain.IChartPoints

interface ScenarioBe {
    val name: String
    val description: String
    val projectName: String
    val scenarioType: ScenarioTypes
}

interface StatsResults {
    val scenario: ScenarioBe
}

enum class DataTypes {
    TEST,
    CONTROL
}

enum class StatsMinMaxTypesBe {
    ECLIPTIC_DISTANCE,
    DECLINATION
}


/**
 * Combination of results for segments, to be saved in Json format.
 * Generated equals and hashcode because there is an array in this dataclass.
 */
data class RangeSegmentResults(override val scenario: ScenarioBe,
                               val summedResults: Array<Array<Int>>,
                               val details: List<ScenRangePositionsPerChart>) : StatsResults {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RangeSegmentResults

        if (scenario != other.scenario) return false
        if (!summedResults.contentDeepEquals(other.summedResults)) return false
        if (details != other.details) return false

        return true
    }

    override fun hashCode(): Int {
        var result = scenario.hashCode()
        result = 31 * result + summedResults.contentDeepHashCode()
        result = 31 * result + details.hashCode()
        return result
    }
}

data class ScenRangeBe(override val name: String,
                       override val description: String,
                       override val projectName: String,
                       override val scenarioType: ScenarioTypes,
                       val rangeType: StatsRangeTypes,
                       val houseSystem: HouseSystems,
                       val celObjects: List<CelestialObjects>,
                       val mundanePoints: List<MundanePointsAstron>) : ScenarioBe

data class ScenMinMaxBe(override val name: String,
                        override val description: String,
                        override val projectName: String,
                        override val scenarioType: ScenarioTypes,
                        val minMaxTypes: StatsMinMaxTypesBe,
                        val celObjects: List<CelestialObjects>,
                        val mundanePoints: List<MundanePointsAstron>) : ScenarioBe


data class ScenRangePosition(val point: IChartPoints, val position: Double, val segment: Int)

data class ScenRangePositionsPerChart(val chartId: Int,
                                      val positions: List<ScenRangePosition>)

data class RangeSegmentsForPoint(val point: IChartPoints, val segmentTotals: List<Int>, val totalForPoint: Int)

data class DataFileDescription(val name: String,
                               val description: String,
                               val nrOfRecords: Int)

data class InputDataSet(val name: String,
                        val description: String,
                        val origFileName: String,
                        val dateTime: String,
                        val inputData: List<ChartInputData>)