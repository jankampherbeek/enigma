/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.references.CelestialObjects
import com.radixpro.enigma.references.MundanePointsAstron
import com.radixpro.enigma.statistics.core.RangeSegmentResults
import com.radixpro.enigma.statistics.core.ScenRangeBe
import com.radixpro.enigma.statistics.ui.domain.StatsRangeTypes
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CsvTextForRangeTest() {

    private val divider = 12
    private val projName = "My Project"
    private val scenName = "This scenario"
    private val statsResultsMock: RangeSegmentResults = mockk()
    private val scenarioMock: ScenRangeBe = mockk()
    private val csvTextForRange = CsvTextForRange()


    @BeforeEach
    fun init() {
        every { scenarioMock.celObjects } returns createCelestialObjects()
        every { scenarioMock.mundanePoints } returns createMundanePoints()
        every { scenarioMock.projectName } returns projName
        every { scenarioMock.name } returns scenName
        every { scenarioMock.rangeType } returns StatsRangeTypes.SIGNS
        every { statsResultsMock.summedResults } returns createSummedResults()
        every { statsResultsMock.scenario } returns scenarioMock
        every { statsResultsMock.details } returns listOf()
    }

    @Test
    fun `createTextLines should return the correct textlines for a csv`() {
        csvTextForRange.createTextLines(statsResultsMock, divider) shouldBe createExpectedLines()
    }


    private fun createSummedResults(): Array<Array<Int>> {
        val firstArray = arrayOf(1, 2, 3, 4, 5, 6, 0, 3, 3, 2, 1, 0, 30)
        val secondArray = arrayOf(0, 0, 2, 1, 4, 22, 100, 3, 3, 3, 5, 9, 152)
        val thirdArray = arrayOf(3, 33, 333, 0, 0, 0, 0, 0, 2, 22, 222, 0, 615)
        val fourthArray = arrayOf(1, 1, 1, 0, 0, 0, 3, 4, 5, 6, 2, 0, 23)
        return arrayOf(firstArray, secondArray, thirdArray, fourthArray)
    }

    private fun createCelestialObjects(): List<CelestialObjects> {
        val celestialObjects = mutableListOf<CelestialObjects>()
        celestialObjects.add(CelestialObjects.SUN)
        celestialObjects.add(CelestialObjects.MOON)
        return celestialObjects.toList()
    }

    private fun createMundanePoints(): List<MundanePointsAstron> {
        val mundanePoints = mutableListOf<MundanePointsAstron>()
        mundanePoints.add(MundanePointsAstron.MC)
        mundanePoints.add(MundanePointsAstron.ASC)
        return mundanePoints.toList()
    }

    private fun createExpectedLines(): List<String> {
        val csvLines = mutableListOf<String>()
        csvLines.add("Scenario: This scenario. Project: My Project")
        csvLines.add("")
        csvLines.add(",1,2,3,4,5,6,7,8,9,10,11,12,Total")
        csvLines.add("SUN,1,2,3,4,5,6,0,3,3,2,1,0,30")
        csvLines.add("MOON,0,0,2,1,4,22,100,3,3,3,5,9,152")
        csvLines.add("MC,3,33,333,0,0,0,0,0,2,22,222,0,615")
        csvLines.add("ASC,1,1,1,0,0,0,3,4,5,6,2,0,23")
        return csvLines.toList()
    }


}