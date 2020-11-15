/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.api

import com.radixpro.enigma.statistics.api.converters.ScenConverterFactory
import com.radixpro.enigma.statistics.api.converters.ScenarioConverter
import com.radixpro.enigma.statistics.core.ScenarioBe
import com.radixpro.enigma.statistics.process.StatsProcessHandler
import com.radixpro.enigma.statistics.ui.domain.ScenarioFe
import com.radixpro.enigma.statistics.ui.domain.ScenarioTypes
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class StatsProcessApiTest {

    private val resultText = "0,0,1,1,5"
    private val handlerMock: StatsProcessHandler = mockk()

    private val converterFactoryMock: ScenConverterFactory = mockk()
    private val converterMock: ScenarioConverter = mockk()
    private val scenBeMock: ScenarioBe = mockk()
    private val scenFeMock: ScenarioFe = mockk()
    private lateinit var api: StatsProcessApi


    @BeforeEach
    fun init() {
        every { handlerMock.handleProcess(any(), any()) } returns resultText
        every { converterMock.feRequestToBe(any()) } returns scenBeMock
        every { converterFactoryMock.getConverter(any()) } returns converterMock
        every { scenFeMock.typeName } returns ScenarioTypes.RANGE.name
        api = StatsProcessApi(handlerMock, converterFactoryMock)
    }

    @Test
    fun `Method processScanRange should return value as received from StatsProcessHandler`() {
        api.processScenario(scenFeMock, "TEST") shouldBe resultText
    }


}