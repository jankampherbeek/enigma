/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.statistics.core.ScenRangeBe
import com.radixpro.enigma.statistics.ui.domain.ScenarioTypes
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class StatsProcessHandlerTest {

    private val resultTxt = "0,1,2"
    private val scenRangeProcessorMock: ScenRangeProcessor = mockk()
    private val scenMinMaxProcessorMock: ScenMinMaxProcessor = mockk()
    private val scenRangeBeMock: ScenRangeBe = mockk()
    private lateinit var handler: StatsProcessHandler

    @BeforeEach
    fun setUp() {
        every { scenRangeProcessorMock.process(any(), any()) } returns resultTxt
        every { scenRangeBeMock.scenarioType } returns ScenarioTypes.RANGE
        handler = StatsProcessHandler(scenRangeProcessorMock, scenMinMaxProcessorMock)
    }

    @Test
    fun `Method handleProcess for a Scenario for Range should return result as given by implementation of ScenProcessor`() {
        handler.handleProcess(scenRangeBeMock, "TEST") shouldBe resultTxt
    }

}