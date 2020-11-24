/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.api

import com.radixpro.enigma.statistics.api.xchg.ApiResult
import com.radixpro.enigma.statistics.converters.ScenConverterFactory
import com.radixpro.enigma.statistics.converters.ScenRangeConverter
import com.radixpro.enigma.statistics.process.ScenHandler
import com.radixpro.enigma.statistics.process.ScenarioGeneralHandler
import com.radixpro.enigma.statistics.testhelpers.ScenarioCreators
import com.radixpro.enigma.statistics.ui.domain.ScenarioTypes
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ScenGeneralApiTest {

    private val converterFactoryMock: ScenConverterFactory = mockk()
    private val genHandlerMock: ScenarioGeneralHandler = mockk(relaxed = true)
    private val scenHandlerMock: ScenHandler = mockk(relaxed = true)
    private val rangeConverterMock: ScenRangeConverter = mockk()
    private val scenarioFe = ScenarioCreators().createScenRangeFe()
    private val scenarioBe = ScenarioCreators().createScenRangeBe()
    private val api: ScenGeneralApi = ScenGeneralApi(genHandlerMock, scenHandlerMock, converterFactoryMock)


    @BeforeEach
    fun init() {
        every { genHandlerMock.retrieveScenarioNames(any()) } returns scenarioNames()
        every { scenHandlerMock.saveScenario(any()) } returns ApiResult(true, "")
        every { scenHandlerMock.readScenario(any(), any()) } returns scenarioBe
        every { converterFactoryMock.getConverter(ScenarioTypes.RANGE) } returns rangeConverterMock
        every { rangeConverterMock.feRequestToBe(any()) } returns scenarioBe
        every { rangeConverterMock.beRequestToFe(any()) } returns scenarioFe
    }

    @Test
    fun `Api returns correct list of scenario names`() {
        api.readAllNames("project name") shouldBe scenarioNames()
    }

    @Test
    fun `Api returns a valid ApiInsert after saving new Scenario`() {
        api.save(scenarioFe) shouldBe ApiResult(true, "")
    }

    @Test
    fun `Api returns a valid ScenarioFe after reading new Scenario`() {
        api.read("scenarioName", "RANGE", "projectName") shouldBe ScenarioCreators().createScenRangeFe()
    }

    private fun scenarioNames(): List<String> {
        return listOf("Scen1", "Scen2", "Scen3")
    }


}