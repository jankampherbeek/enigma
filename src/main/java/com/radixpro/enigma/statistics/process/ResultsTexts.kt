/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.Rosetta
import com.radixpro.enigma.shared.common.EnigmaDictionary.NEWLINE
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
        for (i in 0 until nrOfCelObjects - 1) {
            textLines.add(createDataLine(i, divider, scenario.celObjects, results.summedResults[i]))
        }
        for (i in 0 until nrOfMundanePoints - 1) {
            textLines.add(createDataLine(i, divider, scenario.mundanePoints, results.summedResults[i]))
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


class FixedTextForRange {


    fun createFormattedText(statsResults: StatsResults, divider: Int): String {
        val results = statsResults as RangeSegmentResults
        val scenario = results.scenario as ScenRangeBe
        val nrOfMundanePoints = scenario.mundanePoints.size
        val nrOfCelObjects = scenario.celObjects.size
        val formattedText = StringBuilder("")
        formattedText.append("Scenario: ${scenario.name}. Project: ${scenario.projectName}").append(NEWLINE).append(NEWLINE)
        formattedText.append("            ")   // 12 spaces
        for (i in 1..divider) formattedText.append(i.toString().padStart(8, ' '))
        formattedText.append("   Total").append(NEWLINE).append(NEWLINE)
        for (i in 0 until nrOfCelObjects) {
            formattedText.append(createDataLine(i, divider, scenario.celObjects, results.summedResults[i]))
        }
        for (i in 0 until nrOfMundanePoints) {
            formattedText.append(createDataLine(i, divider, scenario.mundanePoints, results.summedResults[i]))
        }
        return formattedText.toString()

    }

    private fun createDataLine(index: Int, divider: Int, points: List<IChartPoints>, lineData: Array<Int>): String {
        val lineText = StringBuilder("")
        lineText.append(Rosetta.getText(points[index].rbKey).padEnd(12))
        for (j in 0 until divider + 1) {
            val x = lineData[j]
            lineText.append(x.toString().padStart(8, ' '))
        }
        lineText.append(NEWLINE)
        return lineText.toString()
    }

}
