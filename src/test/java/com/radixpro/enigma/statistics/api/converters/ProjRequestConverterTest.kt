/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.api.converters

import com.radixpro.enigma.statistics.testhelpers.ProjectCreator
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ProjectConversionTest {

    private lateinit var converter: ProjectConverter
    private val projName = "Name for myProject"
    private val projDescr = "Description for myProject"
    private val dataFileName = "Name for myDataFile"
    private val projectCreator = ProjectCreator()

    @BeforeEach
    fun init() {
        converter = ProjectConverter()
    }

    @Test
    fun `Conversion frontend Statistics Project to backend `() {
        val project = converter.feRequestToBe(projectCreator.createStatsProjectFe(projName, projDescr, dataFileName))
        project shouldBe projectCreator.createStatsProjectBe(projName, projDescr, dataFileName)
    }

    @Test
    fun `Conversion backend Statistics Project to frontend`() {
        val projectFe = converter.beRequestToFe(projectCreator.createStatsProjectBe(projName, projDescr, dataFileName))
        projectFe shouldBe projectCreator.createStatsProjectFe(projName, projDescr, dataFileName)
    }

}