/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.statistics.core.ScenarioBe
import com.radixpro.enigma.statistics.ui.domain.ScenarioTypes

interface Processor {
    val calculator: StatsCalculator
    fun calculate();
    fun analyze();
}

class RangeProcessor(override val calculator: StatsCalculator) : Processor {

    override fun calculate() {
        TODO("Not yet implemented")
    }

    override fun analyze() {
        TODO("Not yet implemented")
    }

}


object ProcessorFactory {

    fun createProcessor(scenarioBe: ScenarioBe): Processor {
        val calculator = AllPointsCalculator()
        if (ScenarioTypes.RANGE == scenarioBe.scenarioType) return RangeProcessor(calculator)
        // todo implement other scenario's
        return RangeProcessor(calculator)
    }

}