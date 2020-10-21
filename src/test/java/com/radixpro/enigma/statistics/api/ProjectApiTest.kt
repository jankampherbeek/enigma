/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.api

import com.radixpro.enigma.statistics.api.converters.ProjectConverter
import com.radixpro.enigma.statistics.api.xchg.ApiResult
import com.radixpro.enigma.statistics.process.StatsProjHandler
import com.radixpro.enigma.statistics.testhelpers.ProjectCreator
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ProjectApiTest {

    private val handlerMock: StatsProjHandler = mockk()
    private val converterMock: ProjectConverter = mockk()
    lateinit var api: ProjectApi
    private val projName = "Name for myProject"
    private val projDescr = "Description for myProject"
    private val dataFileName = "Name for myDataFile"
    private val projectCreator = ProjectCreator()

    @BeforeEach
    fun init() {
        api = StatsProjApi(handlerMock, converterMock)
        every { handlerMock.saveProject(any()) } returns ApiResult(true, "OK")
        every { converterMock.feRequestToBe(any()) } returns projectCreator.createStatsProjectBe(projName, projDescr, dataFileName)
    }

    @Test
    fun `ProjectApi handles request for saving correctly`() {
        val projectFe = projectCreator.createStatsProjectFe(projName, projDescr, dataFileName)
        api.save(projectFe) shouldBe expectedResultForSave()
    }

    private fun expectedResultForSave(): ApiResult {
        return ApiResult(true, "OK")
    }

}