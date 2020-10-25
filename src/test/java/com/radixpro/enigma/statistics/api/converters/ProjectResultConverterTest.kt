/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.api.converters

import com.radixpro.enigma.statistics.testhelpers.ProjectCreator
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ProjectResultConverterTest {

    private lateinit var resultConverter: ProjectResultConverter
    private val projectCreator = ProjectCreator()
    private val converterMock: ProjectConverter = mockk(relaxed = true)
    private val name = "name1"
    private val descr = "descr 2"
    private val data = "data 3"

    @BeforeEach
    fun init() {
        every { converterMock.beRequestToFe(any()) } returns projectCreator.createStatsProjectFe(name, descr, data)
        resultConverter = ProjectResultConverter(converterMock)
    }

    @Test
    fun `Conversion backend ProjectApiResult to frontend`() {
        val projectResult = resultConverter.beResponseToFe(projectCreator.createProjectApiResultBe(name, descr, data))
        projectResult shouldBe projectCreator.createProjectApiResultFe(name, descr, data)
    }

}