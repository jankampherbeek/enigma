/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.statistics.core.RangeSegmentResults
import com.radixpro.enigma.statistics.core.ScenRangeBe
import com.radixpro.enigma.statistics.core.StatsResults
import com.radixpro.enigma.xchg.domain.IChartPoints


class CsvTextForRange {

    fun createTextLines(statsResults: StatsResults, divider: Int): List<String> {
        val results = statsResults as RangeSegmentResults
        val scenario = results.scenario as ScenRangeBe
        val nrOfMundanePoints = scenario.mundanePoints.size
        val nrOfCelObjects = scenario.celObjects.size
        val textLines = mutableListOf<String>()
        textLines.add("Scenario: ${scenario.name}. Project: ${scenario.projectName}")
        textLines.add("")
        val header = StringBuilder(",")
        for (i in 1..divider) header.append("$i,")
        header.append("Total")
        textLines.add(header.toString())
        for (i in 0 until nrOfCelObjects) {
            textLines.add(createDataLine(i, divider, scenario.celObjects, results.summedResults[i]))
        }
        for (i in 0 until nrOfMundanePoints) {
            textLines.add(createDataLine(i, divider, scenario.mundanePoints, results.summedResults[i + nrOfCelObjects]))
        }
        return textLines
    }

    private fun createDataLine(index: Int, divider: Int, points: List<IChartPoints>, lineData: Array<Int>): String {
        val lineText = StringBuilder("")
        lineText.append(points[index])
        lineText.append(",")
        for (j in 0 until divider + 1) {
            val x = lineData[j]
            lineText.append(x.toString())
            if (j < divider) lineText.append(",")
        }
        return lineText.toString()
    }


}

