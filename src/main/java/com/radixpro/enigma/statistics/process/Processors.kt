/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.be.calc.SeFrontend
import com.radixpro.enigma.be.calc.assist.CombinedFlags
import com.radixpro.enigma.domain.input.ChartInputData
import com.radixpro.enigma.references.CelestialObjects
import com.radixpro.enigma.references.HouseSystems
import com.radixpro.enigma.references.SeFlags
import com.radixpro.enigma.share.persistency.CsvWriter
import com.radixpro.enigma.share.persistency.JsonWriter
import com.radixpro.enigma.share.persistency.Reader
import com.radixpro.enigma.statistics.core.*
import com.radixpro.enigma.statistics.ui.domain.StatsRangeTypes
import swisseph.SweConst.SE_ECL_NUT

interface ScenProcessor {
    val calculator: StatsCalculator
    fun process(scenario: ScenarioBe): String
}

class ScenRangeProcessor(override val calculator: StatsCalculator,
                         val projHandler: StatsProjHandler,
                         val dataHandler: ProjectDataHandler,
                         val pathConstructor: StatsPathConstructor,
                         val reader: Reader,
                         val seFrontend: SeFrontend) : ScenProcessor {


    override fun process(scenario: ScenarioBe): String {
        val actualScenario = scenario as ScenRangeBe
        val rangeType = scenario.rangeType
//        val project = projHandler.read(actualScenario.projectName) as StatsProject
        val inputDataSet = dataHandler.readChartData(scenario.projectName)

        return if (rangeType == StatsRangeTypes.HOUSES) processMundanePositions(scenario, inputDataSet)
        else processEclipticalPositions(actualScenario, rangeType, inputDataSet)
    }

    // TODO combine main parts of precessEclipticalPositions and processMundanePositions
    private fun processEclipticalPositions(scenario: ScenRangeBe, rangeType: StatsRangeTypes, inputDataSet: InputDataSet): String {
        val divider = defineDivider(rangeType)
        val allData = inputDataSet.inputData
        val celObjects = scenario.celObjects
        val flags = CombinedFlags().getCombinedValue(listOf(SeFlags.SWISSEPH, SeFlags.SPEED))
        val positionsPerChart = defineEclipticPositions(allData, celObjects, flags, divider);
        val results = defineSegmentTotals(positionsPerChart.toList(), scenario, divider)
        val rangeSegmentResults = RangeSegmentResults(scenario, results, positionsPerChart)
        var pathToFilename = pathConstructor.pathForJsonResult(scenario.name, scenario.projectName)
        JsonWriter().write2File(pathToFilename, rangeSegmentResults, true)
        val csvText = CsvTextForRange().createTextLines(rangeSegmentResults, divider)
        pathToFilename = pathConstructor.pathForCsvResult(scenario.name, scenario.projectName)
        CsvWriter().write2File(pathToFilename, csvText)
        return FixedTextForRange().createFormattedText(rangeSegmentResults, divider)
    }


    private fun processMundanePositions(scenario: ScenRangeBe, inputDataSet: InputDataSet): String {
        val divider = scenario.houseSystem.nrOfCusps
        val allData = inputDataSet.inputData
        val celObjects = scenario.celObjects
        val flags = CombinedFlags().getCombinedValue(listOf(SeFlags.SWISSEPH, SeFlags.SPEED))
        val positionsPerChart = defineHousePositions(allData, celObjects, flags, scenario.houseSystem, divider);
        val results = defineSegmentTotals(positionsPerChart.toList(), scenario, divider)
        val rangeSegmentResults = RangeSegmentResults(scenario, results, positionsPerChart)
        var pathToFilename = pathConstructor.pathForJsonResult(scenario.name, scenario.projectName)
        JsonWriter().write2File(pathToFilename, rangeSegmentResults, true)
        val csvText = CsvTextForRange().createTextLines(rangeSegmentResults, divider)
        pathToFilename = pathConstructor.pathForCsvResult(scenario.name, scenario.projectName)
        CsvWriter().write2File(pathToFilename, csvText)
        return FixedTextForRange().createFormattedText(rangeSegmentResults, divider)
    }

    private fun calcLongitude(chart: ChartInputData, seId: Int, flags: Long): Double {
        return seFrontend.getPositionsForCelBody(chart.dateTime.jd, seId, flags.toInt(), chart.location).allPositions[0]
    }

    private fun calcSegment(lon: Double, divider: Int): Int {
        val segmentSize: Int = (360.0 / divider).toInt()
        return 1 + (lon / segmentSize).toInt()
    }

    private fun defineDivider(rangeType: StatsRangeTypes): Int {
        return when (rangeType) {
            StatsRangeTypes.SIGNS -> 12
            StatsRangeTypes.DECANATES -> 36
            StatsRangeTypes.DODECATEMORIA -> 144
            StatsRangeTypes.DEGREES -> 360
            else -> 1
        }
    }

    private fun defineEclipticPositions(allData: List<ChartInputData>,
                                        celObjects: List<CelestialObjects>,
                                        flags: Long,
                                        divider: Int): MutableList<ScenRangePositionsPerChart> {
        val positionsPerChart: MutableList<ScenRangePositionsPerChart> = mutableListOf()
        for (chart: ChartInputData in allData) {
            val chartId = chart.id
            val positions: MutableList<ScenRangePosition> = mutableListOf()
            for (celObject: CelestialObjects in celObjects) {
                val lon = calcLongitude(chart, celObject.seId.toInt(), flags)
                val segment = calcSegment(lon, divider)
                positions.add(ScenRangePosition(celObject, lon, segment))
            }
            positionsPerChart.add(ScenRangePositionsPerChart(chartId, positions.toList()))
        }
        return positionsPerChart
    }


    private fun defineHousePositions(allData: List<ChartInputData>,
                                     celObjects: List<CelestialObjects>,
                                     flags: Long,
                                     houseSystem: HouseSystems,
                                     divider: Int): MutableList<ScenRangePositionsPerChart> {
        val positionsPerChart: MutableList<ScenRangePositionsPerChart> = mutableListOf()
        for (chart: ChartInputData in allData) {
            val chartId = chart.id
            val positions: MutableList<ScenRangePosition> = mutableListOf()
            for (celObject: CelestialObjects in celObjects) {
                val lon = calcLongitude(chart, celObject.seId.toInt(), flags)
                val geoLat = chart.location.geoLat
//                val positionsForHouses = seFrontend.getPositionsForHouses(chart.dateTime.jd, flags.toInt(), chart.location,
//                        houseSystem.seId.toInt(), houseSystem.nrOfCusps)
                // FIXME use selected housesystem
                val positionsForHouses = seFrontend.getPositionsForHouses(chart.dateTime.jd, flags.toInt(), chart.location,
                        0, houseSystem.nrOfCusps)
                val armc = positionsForHouses.ascMc[2]
                val eps = seFrontend.getPositionsForEpsilon(chart.dateTime.jd, SE_ECL_NUT, flags.toInt()).allPositions[0]
                var error = StringBuffer()
                val lonLat = arrayOf(lon, 0.0).toDoubleArray()
//                val housePos = seFrontend.getPositionInHouse(armc, geoLat, eps, houseSystem.seId.toInt(), lonLat, error)
                // FIXME use selected housesystem
                val housePos = seFrontend.getPositionInHouse(armc, geoLat, eps, 0, lonLat, error)
                val segment = housePos.toInt()
                positions.add(ScenRangePosition(celObject, housePos, segment))
            }
            positionsPerChart.add(ScenRangePositionsPerChart(chartId, positions.toList()))
        }
        return positionsPerChart
    }

    private fun defineSegmentTotals(positionsPerChart: List<ScenRangePositionsPerChart>, scenario: ScenRangeBe, divider: Int): Array<Array<Int>> {
        val usedObjects = scenario.celObjects
        val objectSize = usedObjects.size
        val segmentSize = divider
        var matrix = Array(objectSize) { Array(segmentSize + 1) { 0 } }

        for (posPerChart: ScenRangePositionsPerChart in positionsPerChart) {
            for ((objectIndex: Int, pos: ScenRangePosition) in posPerChart.positions.withIndex()) {
                var segmentIndex = pos.segment
                matrix[objectIndex][pos.segment]++
                matrix[objectIndex][segmentSize]++
            }

        }
        return matrix
    }
}


object ProcessorFactory {


    // TODO remove remarks and complete code
//    fun createProcessor(scenarioBe: ScenarioBe): ScenProcessor {
//        val calculator = PointsCalculator(SeFrontend)
//        if (ScenarioTypes.RANGE == scenarioBe.scenarioType) return ScenRangeProcessor(calculator)
//        // todo implement other scenario's
//        return ScenRangeProcessor(calculator)
//    }

}