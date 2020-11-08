/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.be.calc.SeFrontend
import com.radixpro.enigma.be.calc.assist.SePositionResultCelObjects
import com.radixpro.enigma.be.calc.assist.SePositionResultHouses
import com.radixpro.enigma.domain.input.Location
import com.radixpro.enigma.references.CelestialObjects
import com.radixpro.enigma.references.MundanePointsAstron
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PointsCalculatorTest {

    private val coPosition = 123.456
    private val mpPosition = 321.654
    private val seMock: SeFrontend = mockk(relaxed = true)
    private val seCoResultMock: SePositionResultCelObjects = mockk(relaxed = true)
    private val seMpResultMock: SePositionResultHouses = mockk(relaxed = true)
    private val calculator = PointsCalculator(seMock)

    @BeforeEach
    fun init() {
        every { seCoResultMock.allPositions } returns arrayOf(coPosition, 0.0, 0.0, 0.0, 0.0, 0.0).toDoubleArray()
        every { seMpResultMock.ascMc } returns arrayOf(0.0, mpPosition, 0.0).toDoubleArray()
        every { seMock.getPositionsForCelBody(any(), any(), any(), any()) } returns seCoResultMock
        every { seMock.getPositionsForHouses(any(), any(), any(), any(), any()) } returns seMpResultMock
    }

    @Test
    fun `Calculation of a celestial body returns the expected result`() {
        calculator.celObject(CelestialObjects.SUN, 888.999, 1, Location(1.1, 2.2)) shouldBe coPosition
    }

    @Test
    fun `Calculation of a mundane point returns the expected result`() {
        calculator.mundanePoint(MundanePointsAstron.ASC, 999.888, 1, Location(3.3, 4.4), 2, 12) shouldBe mpPosition
    }

}