/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.ui

import com.radixpro.enigma.statistics.api.ScenGeneralApi
import com.radixpro.enigma.statistics.api.StatsProjApi
import com.radixpro.enigma.statistics.api.xchg.ApiResult
import com.radixpro.enigma.statistics.testhelpers.ProjectCreator
import com.radixpro.enigma.statistics.testhelpers.ScenarioCreators
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class StatsFacadeTest {

    private val projectCreator = ProjectCreator()
    private val projApiMock: StatsProjApi = mockk(relaxed = true)
    private val scenApiMock: ScenGeneralApi = mockk()
    private val facade = StatsFacade(projApiMock, scenApiMock)
    private val scenarioFe = ScenarioCreators().createScenRangeFe()
    private val scenarioBe = ScenarioCreators().createScenRangeBe()

    @BeforeEach
    fun init() {
        every { projApiMock.save(any()) } returns ApiResult(true, "")
        every { scenApiMock.readAllNames(any()) } returns scenarioNames()
        every { scenApiMock.save(any()) } returns ApiResult(true, "")
        every { scenApiMock.read(any(), any(), any()) } returns scenarioFe
    }

    @Test
    fun `Request to save a project is correctly handled`() {
        facade.saveProject(projectCreator.createStatsProjectFe("projname", "projdescr", "datafile")) shouldBe ApiResult(true, "")
    }

    @Test
    fun `Request to read scenarionames for a project gives the expected results`() {
        scenarioNames() shouldBe facade.readScenarioNames("project name")
    }

    @Test
    fun `Request to write Scenario should result in  a valid ApiResult`() {
        facade.writeScenario(scenarioFe) shouldBe ApiResult(true, "")
    }

    @Test
    fun `Request to read Scenario should result in a valid ScenarioFe`() {
        facade.readScenario("scenName", "scenType", "project") shouldBe scenarioFe

    }

    private fun scenarioNames(): List<String> {
        return listOf("Scen 1", "Scen 2", "Scen 3")
    }

}