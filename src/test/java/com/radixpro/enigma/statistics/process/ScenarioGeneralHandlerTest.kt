/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.share.persistency.FileSystemReader
import com.radixpro.enigma.statistics.persistency.ScenarioGeneralFileMapper
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ScenarioGeneralHandlerTest {

    private val readerMock: FileSystemReader = mockk(relaxed = true)
    private val pathConstructorMock: PathConstructor = mockk(relaxed = true)
    private val mapperMock: ScenarioGeneralFileMapper = mockk()
    private val handler = ScenarioGeneralHandler(readerMock, mapperMock, pathConstructorMock)

    @BeforeEach
    fun init() {
        every { mapperMock.mapFileNamesToScenarioNames(any()) } returns scenarioNames()
    }

    @Test
    fun `Asking for all scenarionames returns the scenarionames for a given project`() {
        handler.retrieveScenarioNames("Project Name") shouldBe scenarioNames()
    }

    private fun scenarioNames(): List<String> {
        return listOf("Scenario A", "Scenario B", "ScenarioC")
    }

}
