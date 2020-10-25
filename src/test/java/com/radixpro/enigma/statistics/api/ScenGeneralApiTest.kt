/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.api

import com.radixpro.enigma.statistics.process.ScenarioGeneralHandler
import com.radixpro.enigma.statistics.process.ScenarioHandlerFactory
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ScenGeneralApiTest {

    private val factoryMock: ScenarioHandlerFactory = mockk()
    private val handlerMock: ScenarioGeneralHandler = mockk(relaxed = true)
    private val api: ScenGeneralApi = ScenGeneralApi(factoryMock)

    @BeforeEach
    fun init() {
        every { handlerMock.retrieveScenarioNames(any()) } returns scenarioNames()
        every { factoryMock.getGeneralHandler() } returns handlerMock
    }

    @Test
    fun `Api returns correct list of scenario names`() {
        api.readAllNames("project name") shouldBe scenarioNames()
    }

    private fun scenarioNames(): List<String> {
        return listOf("Scen1", "Scen2", "Scen3")
    }

}