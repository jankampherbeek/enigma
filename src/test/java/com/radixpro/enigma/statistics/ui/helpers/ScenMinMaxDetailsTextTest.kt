/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.ui.helpers

import com.radixpro.enigma.statistics.ui.domain.ScenMinMaxFe
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class ScenMinMaxDetailsTextTest {

    private val scenDetailsText = ScenMinMaxDetailsText()
    private val expectedText = "Scenario Another Scenario from project Name of project.\n" +
            "Scenario type: MINMAX\n" +
            "Type for min/max: ECLIPTIC_DISTANCE\n" +
            "Description: Some explanatory text\n" +
            "Reference point: MC\n" +
            "Celestial objects: SUN VENUS SATURN \n" +
            "Mundane points: ASC EASTPOINT \n"

    @Test
    fun `The details text for a MinMaxRange is correct`() {
        val scen = constructScenario()
        scenDetailsText.createText(scen) shouldBe expectedText
    }

    private fun constructScenario(): ScenMinMaxFe {
        val celObjects = listOf("SUN", "VENUS", "SATURN")
        val mundPoints = listOf("ASC", "EASTPOINT")
        return ScenMinMaxFe("Another Scenario", "Some explanatory text", "Name of project", "MINMAX",
                "ECLIPTIC_DISTANCE", "MC", celObjects, mundPoints)

    }

}

