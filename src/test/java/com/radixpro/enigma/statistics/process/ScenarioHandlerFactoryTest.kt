/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.statistics.ui.domain.ScenarioTypes
import io.kotest.matchers.types.shouldBeTypeOf
import org.junit.jupiter.api.Test

internal class ScenarioHandlerFactoryTest {

    private val factory = ScenarioHandlerFactory()

    @Test
    fun `Using getHandler with ScenarioTypes Range returns instance of ScenarioRangeHandler`() {
        factory.getHandler(ScenarioTypes.RANGE).shouldBeTypeOf<ScenarioRangeHandler>()
    }

    @Test
    fun `Using getGeneralHandler returns instance of ScenarioGeneralHandler`() {
        factory.getGeneralHandler().shouldBeTypeOf<ScenarioGeneralHandler>()

    }

}