/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.share.api.PropertyApi
import com.radixpro.enigma.shared.Property
import com.radixpro.enigma.statistics.core.StatsProject
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class StatsPathConstructorTest {

    private val propApiMock: PropertyApi = mockk()
    private val projectMock: StatsProject = mockk()
    private val propPath = "prop_path"
    private val projName = "MyProject"
    private val scenName = "MyScenario"
    private val dataName = "data"
    private val sep = java.io.File.separator
    private val constructor = StatsPathConstructor(propApiMock)

    @BeforeEach
    fun setUp() {
        every { propApiMock.read(any()) } returns listOf<Property>(Property("projdir", propPath))
        every { projectMock.name } returns projName
        every { projectMock.dataFileName } returns dataName
    }

    @Test
    fun `Constructing a path for the location of a project gives the expected result`() {
        constructor.pathForProject(projName) shouldBe "prop_path${sep}proj${sep}MyProject${sep}proj_MyProject.json"
    }

    @Test
    fun `Constructing a path for the location of a project folder gives the expected result`() {
        constructor.pathForProjectDir(projName) shouldBe "prop_path${sep}proj${sep}MyProject${sep}"
    }

    @Test
    fun `Constructing a path for the location of a scenario gives the expected result`() {
        constructor.pathForScenario(scenName, projName) shouldBe "prop_path${sep}proj${sep}MyProject${sep}scen_MyScenario.json"
    }

    @Test
    fun `Constructing a path for the location of a json result file gives the expected result`() {
        constructor.pathForJsonResult(scenName, projName) shouldBe "prop_path${sep}proj${sep}MyProject${sep}results_MyScenario.json"
    }

    @Test
    fun `Constructing a path for the location of a csv result file gives the expected result`() {
        constructor.pathForCsvResult(scenName, projName) shouldBe "prop_path${sep}proj${sep}MyProject${sep}results_MyScenario.csv"
    }

    @Test
    fun `Constructing a path for the location of an imported datafile gives the expected result`() {
        constructor.pathForProjectData(projectMock) shouldBe "prop_path${sep}proj${sep}MyProject${sep}in_data.json"
    }

    @Test
    fun `Constructing a path for the location of a control datafile gives the expected result`() {
        constructor.pathForControlData(projectMock) shouldBe "prop_path${sep}proj${sep}MyProject${sep}ctrl_data.json"
    }

    @Test
    fun `Constructiong a path for the folder of global data gives the expected result`() {
        constructor.pathForGlobalData() shouldBe "prop_path${sep}data${sep}"
    }


}