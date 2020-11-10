/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.share.api.PropertyApi
import com.radixpro.enigma.shared.Property
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class StatsPathConstructorTest {

    private val propApiMock: PropertyApi = mockk(relaxed = true)
    private val propPath = "prop_path"
    private val projName = "Abc"
    private val scenName = "Xyz"
    private val sep = java.io.File.separator
    private val constructor = StatsPathConstructor(propApiMock)

    @BeforeEach
    fun setUp() {
        every { propApiMock.read(any()) } returns listOf<Property>(Property("projdir", propPath))
    }

    @Test
    fun `Constructing a path for the location of a project gives the expected result`() {
        constructor.pathForProject(projName) shouldBe "prop_path${sep}proj${sep}Abc${sep}proj_Abc.json"
    }

    @Test
    fun `Constructing a path for the location of a project folder gives the expected result`() {
        constructor.pathForProjectDir(projName) shouldBe "prop_path${sep}proj${sep}Abc${sep}"
    }

    @Test
    fun `Constructing a path for the location of a scenario gives the expected result`() {
        constructor.pathForScenario(scenName, projName) shouldBe "prop_path${sep}proj${sep}Abc${sep}scen_Xyz.json"
    }

    @Test
    fun `Constructing a path for the location of a json result file gives the expected result`() {
        constructor.pathForJsonResult(scenName, projName) shouldBe "prop_path${sep}proj${sep}Abc${sep}results_Xyz.json"
    }

    @Test
    fun `Constructing a path for the location of a csv result file gives the expected result`() {
        constructor.pathForCsvResult(scenName, projName) shouldBe "prop_path${sep}proj${sep}Abc${sep}results_Xyz.csv"
    }

    @Test
    fun `Constructing a path for the location of an imported datafile gives the expected result`() {
        constructor.pathForData(projName) shouldBe "prop_path${sep}proj${sep}Abc${sep}in_Abc.json"
    }


}