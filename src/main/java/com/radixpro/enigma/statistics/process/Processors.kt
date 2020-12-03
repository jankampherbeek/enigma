/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.astronomy.proces.GeneralPositionHandler
import com.radixpro.enigma.be.calc.SeFrontend
import com.radixpro.enigma.be.calc.assist.CombinedFlags
import com.radixpro.enigma.be.util.Range
import com.radixpro.enigma.domain.input.ChartInputData
import com.radixpro.enigma.references.CelestialObjects
import com.radixpro.enigma.references.HouseSystems
import com.radixpro.enigma.references.MundanePointsAstron
import com.radixpro.enigma.references.SeFlags
import com.radixpro.enigma.share.exceptions.ItemNotFoundException
import com.radixpro.enigma.share.persistency.CsvWriter
import com.radixpro.enigma.share.persistency.JsonWriter
import com.radixpro.enigma.share.persistency.Reader
import com.radixpro.enigma.statistics.core.*
import com.radixpro.enigma.statistics.ui.domain.StatsRangeTypes
import com.radixpro.enigma.xchg.domain.IChartPoints
import swisseph.SweConst.SE_ECL_NUT
import kotlin.math.abs

interface ScenProcessor {
    val calculator: StatsCalculator
    fun process(scenario: ScenarioBe, dataType: DataTypes): String
}

class ScenRangeProcessor(
    override val calculator: StatsCalculator,
    val projHandler: StatsProjHandler,
    val dataHandler: ProjectDataHandler,
    val positionHandler: GeneralPositionHandler,
    val pathConstructor: StatsPathConstructor,
    val reader: Reader
) : ScenProcessor {


    override fun process(scenario: ScenarioBe, dataType: DataTypes): String {
        val actualScenario = scenario as ScenRangeBe
        val rangeType = scenario.rangeType
        val inputDataSet = dataHandler.readChartData(scenario.projectName)

        val divider = if (rangeType != StatsRangeTypes.HOUSES) defineDivider(rangeType) else scenario.houseSystem.nrOfCusps
        val allData = inputDataSet.inputData
        val celObjects = scenario.celObjects
        val mundPoints = scenario.mundanePoints
        val flags = CombinedFlags().getCombinedValue(listOf(SeFlags.SWISSEPH, SeFlags.SPEED))

        val positionsPerChart = if (rangeType == StatsRangeTypes.HOUSES)
            defineHousePositions(allData, celObjects, flags, actualScenario.houseSystem)
        else defineEclipticPositions(allData, celObjects, mundPoints, actualScenario, flags, divider)

        val results = defineSegmentTotals(positionsPerChart.toList(), scenario, divider)
        val rangeSegmentResults = RangeSegmentResults(scenario, results, positionsPerChart)
        var pathToFilename = pathConstructor.pathForJsonResult(scenario.name, scenario.projectName)
        JsonWriter().write2File(pathToFilename, rangeSegmentResults, true)
        val csvText = CsvTextForRange().createTextLines(rangeSegmentResults, divider)
        pathToFilename = pathConstructor.pathForCsvResult(scenario.name, scenario.projectName)
        CsvWriter().write2File(pathToFilename, csvText)
        return FixedTextForRange().createFormattedText(rangeSegmentResults, divider)
    }


    private fun calcCelObjectLongitude(chart: ChartInputData, seId: Int, flags: Long): Double {
        return SeFrontend.getPositionsForCelBody(chart.dateTime.jd, seId, flags.toInt(), chart.location).allPositions[0]
    }

    private fun calcMundPointLongitude(chart: ChartInputData, mundPoint: MundanePointsAstron, scenario: ScenRangeBe, flags: Long): Double {
        return positionHandler.calcLongitude(mundPoint, chart.dateTime.jd, flags.toInt(), chart.location)
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

    // TODO move functionality to handler for calculation of ecliptic positions
    private fun defineEclipticPositions(allData: List<ChartInputData>,
                                        celObjects: List<CelestialObjects>,
                                        mundPoints: List<MundanePointsAstron>,
                                        scenario: ScenRangeBe,
                                        flags: Long,
                                        divider: Int): MutableList<ScenRangePositionsPerChart> {
        val positionsPerChart: MutableList<ScenRangePositionsPerChart> = mutableListOf()
        for (chart: ChartInputData in allData) {
            val chartId = chart.id
            val positions: MutableList<ScenRangePosition> = mutableListOf()
            for (celObject: CelestialObjects in celObjects) {
                val lon = calcCelObjectLongitude(chart, celObject.seId.toInt(), flags)
                val segment = calcSegment(lon, divider)
                positions.add(ScenRangePosition(celObject, lon, segment))
            }
            for (mundPoint: MundanePointsAstron in mundPoints) {
                val lon = calcMundPointLongitude(chart, mundPoint, scenario, flags)
                val segment = calcSegment(lon, divider)
                positions.add(ScenRangePosition(mundPoint, lon, segment))
            }
            positionsPerChart.add(ScenRangePositionsPerChart(chartId, positions.toList()))
        }
        return positionsPerChart
    }


    // TODO move functionality to handler for calculation of housesystems
    private fun defineHousePositions(allData: List<ChartInputData>,
                                     celObjects: List<CelestialObjects>,
                                     flags: Long,
                                     houseSystem: HouseSystems): MutableList<ScenRangePositionsPerChart> {
        val positionsPerChart: MutableList<ScenRangePositionsPerChart> = mutableListOf()
        for (chart: ChartInputData in allData) {
            val chartId = chart.id
            val positions: MutableList<ScenRangePosition> = mutableListOf()
            for (celObject: CelestialObjects in celObjects) {
                val lon = calcCelObjectLongitude(chart, celObject.seId.toInt(), flags)
                val geoLat = chart.location.geoLat
                val houseSystemId = houseSystem.seId[0].toInt()
                val positionsForHouses = SeFrontend.getPositionsForHouses(
                    chart.dateTime.jd, flags.toInt(), chart.location,
                    houseSystemId, houseSystem.nrOfCusps
                )
                val armc = positionsForHouses.ascMc[2]
                val eps = SeFrontend.getPositionsForEpsilon(chart.dateTime.jd, SE_ECL_NUT, flags.toInt()).allPositions[0]
                var error = StringBuffer()
                val lonLat = arrayOf(lon, 0.0).toDoubleArray()
                val housePos = SeFrontend.getPositionInHouse(armc, geoLat, eps, houseSystemId, lonLat, error)
                val segment = housePos.toInt()
                positions.add(ScenRangePosition(celObject, housePos, segment))
            }
            positionsPerChart.add(ScenRangePositionsPerChart(chartId, positions.toList()))
        }
        return positionsPerChart
    }

    private fun defineSegmentTotals(positionsPerChart: List<ScenRangePositionsPerChart>, scenario: ScenRangeBe, divider: Int): Array<Array<Int>> {
        val usedObjects = scenario.celObjects
        val usedMundPoints = scenario.mundanePoints
        val objectSize = usedObjects.size + usedMundPoints.size
        var matrix = Array(objectSize) { Array(divider + 1) { 0 } }

        for (posPerChart: ScenRangePositionsPerChart in positionsPerChart) {
            for ((objectIndex: Int, pos: ScenRangePosition) in posPerChart.positions.withIndex()) {
                var segmentIndex = pos.segment
                matrix[objectIndex][pos.segment - 1]++
                matrix[objectIndex][divider]++
            }
        }
        return matrix
    }
}


class ScenMinMaxProcessor(
    override val calculator: StatsCalculator,
    val projHandler: StatsProjHandler,
    val dataHandler: ProjectDataHandler,
    val positionHandler: GeneralPositionHandler,
    val pathConstructor: StatsPathConstructor,
    val reader: Reader
) : ScenProcessor {

    override fun process(scenario: ScenarioBe, dataType: DataTypes): String {
        // definieer startsituatie
        val actualScenario = scenario as ScenMinMaxBe
        val minMaxType = scenario.minMaxTypes
        val allCharts = dataHandler.readChartData(actualScenario.projectName).inputData

        // loop alle horoscopen na
        var fullResults: MinMaxResults
        fullResults = if (StatsMinMaxTypesBe.ECLIPTIC_DISTANCE == actualScenario.minMaxTypes) {
            defineTotalsForDistance(actualScenario, allCharts)
        } else if (StatsMinMaxTypesBe.DECLINATION == actualScenario.minMaxTypes) {
            // TODO change           fullResults = defineTotalsForDecl(actualScenario, allCharts)
            defineTotalsForDistance(actualScenario, allCharts)
        } else throw ItemNotFoundException("Found StatsMinMaxTypesBe: ${actualScenario.minMaxTypes.name} which is not supported in ScenMinMaxProcessor.")
        processOutput(fullResults, actualScenario)
        return FixedTextForMinMax().createFormattedText(fullResults)
    }

    private fun defineTotalsForDistance(scenario: ScenMinMaxBe, allCharts: List<ChartInputData>): MinMaxResults {
        val flags = CombinedFlags().getCombinedValue(listOf(SeFlags.SWISSEPH, SeFlags.SPEED))
        val refPoint = scenario.refPoint
        val summedTotals: MutableList<ChartPointValue> = ArrayList()
        val details: MutableList<MinMaxPositionsPerChart> = ArrayList()
        val allPoints = scenario.celObjects + scenario.mundanePoints
        if (null != refPoint) {
            var shortestDistance = 180.0
            var pointWithShortestDistance: IChartPoints = CelestialObjects.EMPTY
            for (chart in allCharts) {
                var refPointPosition = positionHandler.calcLongitude(refPoint, chart.dateTime.jd, flags.toInt(), chart.location)
                var distance = 0.0
                for (point in allPoints) {
                    distance = defineDistance(point, chart, flags.toInt(), refPointPosition)
                    if (distance < shortestDistance) {
                        shortestDistance = distance
                        pointWithShortestDistance = point
                    }
                }
                details.add(MinMaxPositionsPerChart(chart.id, pointWithShortestDistance, distance))
            }
            for (point in allPoints) {
                var count = 0
                for (posPerChart in details) {
                    if (point == posPerChart.point) count++
                }
                summedTotals.add(ChartPointValue(point, count))
            }
        }
        return MinMaxResults(scenario, summedTotals, details)
    }

    private fun defineDistance(point: IChartPoints, chart: ChartInputData, flags: Int, refPointPosition: Double): Double {
        var lon = positionHandler.calcLongitude(point, chart.dateTime.jd, flags, chart.location)
        return Range.checkValue(abs(refPointPosition - lon), 0.0, 180.0)
    }

//    private fun defineTotalsForDecl(scenario: ScenMinMaxBe, allCharts: List<ChartInputData>): MinMaxResults {
//
//
//        return MinMaxResult(scenario, summedTotals, details)
//    }

    private fun processOutput(fullResults: MinMaxResults, scenario: ScenMinMaxBe) {
        var pathToFilename = pathConstructor.pathForJsonResult(scenario.name, scenario.projectName)
        JsonWriter().write2File(pathToFilename, fullResults, true)
        val csvText = CsvTextForMinMax().createTextLines(fullResults)
        pathToFilename = pathConstructor.pathForCsvResult(scenario.name, scenario.projectName)
        CsvWriter().write2File(pathToFilename, csvText)
    }


    fun processOld(scenario: ScenarioBe, dataType: DataTypes): String {
        val actualScenario = scenario as ScenMinMaxBe
        val minMaxType = scenario.minMaxTypes
        val refPoint = scenario.refPoint
        val inputDataSet = dataHandler.readChartData(actualScenario.projectName)
        val allData = inputDataSet.inputData
        var refPointPosition: Double
        val flags = CombinedFlags().getCombinedValue(listOf(SeFlags.SWISSEPH, SeFlags.SPEED))
        val totalsForAllChartPoints = Array(actualScenario.celObjects.size) { Array(2) { 0 } }
        val indexForAllChartPoints: MutableMap<IChartPoints, Int> = HashMap()
        for ((index, point) in actualScenario.celObjects.withIndex()) {
            indexForAllChartPoints[point] = index
            totalsForAllChartPoints[index][0] = index
            totalsForAllChartPoints[index][1] = point.id
        }
        var cumIndex = 0
        for ((index, point) in actualScenario.mundanePoints.withIndex()) {
            cumIndex = index + actualScenario.celObjects.size
            indexForAllChartPoints[point] = cumIndex
            totalsForAllChartPoints[cumIndex][0] = cumIndex
            totalsForAllChartPoints[cumIndex][1] = point.id
        }

        val allResults: MutableList<MinMaxPositionsPerChart> = ArrayList()
        for (chart in allData) {
            var resultingPoint: IChartPoints = CelestialObjects.SUN    // fake instantiation
            var calcValue: Double = 0.0
            when (minMaxType) {
                StatsMinMaxTypesBe.ECLIPTIC_DISTANCE -> {
                    var shortestDistance: Double = 180.0
                    refPointPosition = positionHandler.calcLongitude(refPoint!!, chart.dateTime.jd, flags.toInt(), chart.location)
                    for (celObject in actualScenario.celObjects) {
                        var lon = positionHandler.calcLongitude(celObject, chart.dateTime.jd, flags.toInt(), chart.location)
                        var distance = Range.checkValue(abs(refPointPosition - lon), 0.0, 180.0)
                        if (distance <= shortestDistance) {
                            shortestDistance = distance
                            resultingPoint = celObject
                            calcValue = distance
                        }
                    }
                    for (mundPoint in actualScenario.mundanePoints) {
                        var lon = positionHandler.calcLongitude(mundPoint, chart.dateTime.jd, flags.toInt(), chart.location)
                        var distance = Range.checkValue(abs(refPointPosition - lon), 0.0, 180.0)
                        if (distance <= shortestDistance) {
                            shortestDistance = distance
                            resultingPoint = mundPoint
                            calcValue = distance
                        }
                    }
                }
                StatsMinMaxTypesBe.DECLINATION -> {
                    var maxDecl = 0.0
                    for (celObject in actualScenario.celObjects) {
                        var decl = positionHandler.calcDeclination(celObject, chart.dateTime.jd, flags.toInt(), chart.location)
                        if (decl >= maxDecl) {
                            maxDecl = decl
                            resultingPoint = celObject
                            calcValue = decl
                        }
                    }
                    for (mundPoint in actualScenario.mundanePoints) {
                        var decl = positionHandler.calcDeclination(mundPoint, chart.dateTime.jd, flags.toInt(), chart.location)
                        if (decl >= maxDecl) {
                            maxDecl = decl
                            resultingPoint = mundPoint
                            calcValue = decl
                        }
                    }
                }
            }
            allResults.add(MinMaxPositionsPerChart(chart.id, resultingPoint, calcValue))
        }
        val summedResults: MutableList<ChartPointValue> = ArrayList()
        var tempVal: Int
        for (i in 0 until allResults.size) {
            var tempPoint = allResults[i].point
            val index = indexForAllChartPoints[tempPoint]
            totalsForAllChartPoints[index!!][1]++              // todo check for null
        }

        var point: IChartPoints = CelestialObjects.EMPTY
        var sum: Int = 0
        for (i in totalsForAllChartPoints.indices) {
            for (j in indexForAllChartPoints) {
                if (j.key.id == i) {
                    point = j.key
                    sum = totalsForAllChartPoints[i][1]
                }
            }
            summedResults += ChartPointValue(point, sum)
        }

        val fullResults = MinMaxResults(actualScenario, summedResults, allResults.toList())
        var pathToFilename = pathConstructor.pathForJsonResult(scenario.name, scenario.projectName)
        JsonWriter().write2File(pathToFilename, fullResults, true)
        val csvText = CsvTextForMinMax().createTextLines(fullResults)
        pathToFilename = pathConstructor.pathForCsvResult(scenario.name, scenario.projectName)
        CsvWriter().write2File(pathToFilename, csvText)
        return FixedTextForMinMax().createFormattedText(fullResults)
    }
}

