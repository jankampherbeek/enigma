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
import com.radixpro.enigma.references.SeFlags
import com.radixpro.enigma.share.persistency.Reader
import com.radixpro.enigma.share.persistency.Writer
import com.radixpro.enigma.statistics.core.*
import com.radixpro.enigma.statistics.ui.domain.StatsRangeTypes

interface ScenProcessor {
    val calculator: StatsCalculator
    fun process(scenario: ScenarioBe): String
}

class ScenRangeProcessor(override val calculator: StatsCalculator,
                         val projHandler: StatsProjHandler,
                         val dataHandler: InternalDataFileHandler,
                         val reader: Reader,
                         val writer: Writer,
                         val combinedFlags: CombinedFlags,
                         val seFrontend: SeFrontend) : ScenProcessor {


    override fun process(scenario: ScenarioBe): String {
        val actualScenario = scenario as ScenRangeBe
        val rangeType = scenario.rangeType
        val project = projHandler.read(actualScenario.projectName) as StatsProject
        val inputDataSet = dataHandler.readData(project)

        return if (rangeType == StatsRangeTypes.HOUSES) processMundanePositions()
        else processEclipticalPositions(project, actualScenario, rangeType, inputDataSet)
    }

    fun processEclipticalPositions(project: StatsProject, scenario: ScenRangeBe, rangeType: StatsRangeTypes, inputDataSet: InputDataSet): String {
        val divider = defineDivider(rangeType)
        val allData = inputDataSet.inputData
        val celObjects = scenario.celObjects
        val flags = combinedFlags.getCombinedValue(listOf(SeFlags.SWISSEPH, SeFlags.SPEED))
        val positionsPerChart = definePositions(allData, celObjects, flags, divider);
        val results = defineSegmentTotals(positionsPerChart.toList(), scenario, divider)
        val rangeSegmentResults = RangeSegmentResults(scenario, results, positionsPerChart)
        val pathToFilename = ""
        writer.write2File(pathToFilename, rangeSegmentResults, true)
        val csvText = CsvTextForRange().createTextLines(rangeSegmentResults, divider)
//        writer.write2File(pathToFilename, csvText)    TODO use writer for csv
        // create text for UI
        // return text for UI

        return ""
    }


    private fun processMundanePositions(): String {
        // swe_house_pos

        // double swe_house_pos(
        //double armc,         /* ARMC */
        //double geolat,       /* geographic latitude, in degrees */
        //double eps,               /* ecliptic obliquity, in degrees */
        //int hsys,                 /* house method, one of the letters PKRCAV */
        //double *xpin,        /* array of 2 doubles: ecl. longitude and latitude of the planet */
        //char *serr);              /* return area for error or warning message *

        return ""
    }

    private fun calcLongitude(chart: ChartInputData, flags: Long): Double {
        return seFrontend.getPositionsForCelBody(chart.dateTime.jd, chart.id, flags.toInt(), chart.location).allPositions[0]
    }

    private fun calcSegment(lon: Double, divider: Int): Int {
        val segmentSize: Int = (360.0 / divider) as Int
        return 1 + (lon / segmentSize) as Int
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

    private fun definePositions(allData: List<ChartInputData>,
                                celObjects: List<CelestialObjects>,
                                flags: Long,
                                divider: Int): MutableList<ScenRangePositionsPerChart> {
        val positionsPerChart: MutableList<ScenRangePositionsPerChart> = mutableListOf()
        for (chart: ChartInputData in allData) {
            val chartId = chart.id
            val positions: MutableList<ScenRangePosition> = mutableListOf()
            for (celObject: CelestialObjects in celObjects) {
                val lon = calcLongitude(chart, flags)
                val segment = calcSegment(lon, divider)
                positions.add(ScenRangePosition(celObject, lon, segment))
            }
            positionsPerChart.add(ScenRangePositionsPerChart(chartId, positions.toList()))
        }
        return positionsPerChart
    }
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


object ProcessorFactory {


    // TODO remove remarks and complete code
//    fun createProcessor(scenarioBe: ScenarioBe): ScenProcessor {
//        val calculator = PointsCalculator(SeFrontend)
//        if (ScenarioTypes.RANGE == scenarioBe.scenarioType) return ScenRangeProcessor(calculator)
//        // todo implement other scenario's
//        return ScenRangeProcessor(calculator)
//    }

}