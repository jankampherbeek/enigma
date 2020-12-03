/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.share.exceptions.ItemNotFoundException
import com.radixpro.enigma.statistics.core.DataTypes
import com.radixpro.enigma.statistics.core.ScenarioBe
import com.radixpro.enigma.statistics.ui.domain.ScenarioTypes

class StatsProcessHandler(val rangeProcessor: ScenRangeProcessor, val minMaxProcessor: ScenMinMaxProcessor) {

    fun handleProcess(scenario: ScenarioBe, dataTypeText: String): String {
        val dataType = DataTypes.valueOf(dataTypeText)
        return when (scenario.scenarioType) {
            ScenarioTypes.RANGE -> rangeProcessor.process(scenario, dataType)
            ScenarioTypes.MINMAX -> minMaxProcessor.process(scenario, dataType)
            else -> throw ItemNotFoundException("Could not find scenario type in StatsProcessHandler.")
        }

    }

}