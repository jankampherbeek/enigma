/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.converters

import com.radixpro.enigma.statistics.testhelpers.ScenarioCreators
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ScenMinMaxConverterTest {

    private lateinit var converter: ScenarioConverter
    private val creators = ScenarioCreators()

    @BeforeEach
    fun init() {
        converter = ScenMinMaxConverter()
    }

    @Test
    fun `Conversion frontend Scenario to backend`() {
        creators.createScenMinMaxBe() shouldBe (converter.feRequestToBe(creators.createScenMinMaxFe()))
    }

    @Test
    fun `Conversion backend Scenario to frontend`() {
        creators.createScenMinMaxFe() shouldBe (converter.beRequestToFe((creators.createScenMinMaxBe())))
    }

    @Test
    fun `Conversion frontened Scenario to backend, with invalid name for Enum value, should throw IllegalargumentException`() {
        val scenFeError = creators.createScenMinMaxFeError()
        shouldThrow<IllegalArgumentException> { converter.feRequestToBe(scenFeError) }
    }

}





