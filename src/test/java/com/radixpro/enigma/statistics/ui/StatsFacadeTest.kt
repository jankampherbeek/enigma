/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.ui

import com.radixpro.enigma.statistics.api.StatsProjApi
import com.radixpro.enigma.statistics.api.xchg.ApiResult
import com.radixpro.enigma.statistics.testhelpers.ProjectCreator
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class StatsFacadeTest {

    private val projectCreator = ProjectCreator()
    private val projApiMock: StatsProjApi = mockk(relaxed = true)
    private val facade = StatsFacade(projApiMock)

    @BeforeEach
    fun init() {
        every { projApiMock.save(any()) } returns ApiResult(true, "OK")
    }

    @Test
    fun `Request to save a project is correctly handled`() {
        facade.saveProject(projectCreator.createStatsProjectFe("projname", "projdescr", "datafile")) shouldBe ApiResult(true, "OK")
    }


}