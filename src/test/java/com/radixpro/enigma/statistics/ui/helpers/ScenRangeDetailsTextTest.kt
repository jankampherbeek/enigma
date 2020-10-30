/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.ui.helpers

import com.radixpro.enigma.statistics.ui.domain.ScenRangeFe
import com.radixpro.enigma.statistics.ui.domain.ScenarioFe
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ScenRangeDetailsTextTest {

    private val scenDetailsText = ScenRangeDetailsText()
    private lateinit var scen: ScenarioFe
    private val expectedText = "Scenario My Scenario from project Just a project.\n" +
            "Scenario type: RANGE\n" +
            "Range type: SIGNS\n" +
            "Description: A possibly longer textual description.\n" +
            "Celestial objects: SUN MARS NEPTUNE \n" +
            "Mundane points: ASC VERTEX \n"

    @BeforeEach
    fun init() {
        scen = constructScenario()
    }

    @Test
    fun `The details text for a ScenRange is correct`() {
        scenDetailsText.createText(scen) shouldBe expectedText
    }

    private fun constructScenario(): ScenRangeFe {
        val celObjects = listOf("SUN", "MARS", "NEPTUNE")
        val mundPoints = listOf("ASC", "VERTEX")
        return ScenRangeFe("My Scenario", "A possibly longer textual description.", "Just a project", "RANGE",
                "SIGNS", celObjects, mundPoints)

    }

}