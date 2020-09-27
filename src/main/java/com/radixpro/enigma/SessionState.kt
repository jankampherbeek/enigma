/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma

import com.radixpro.enigma.domain.astronpos.FullChart
import com.radixpro.enigma.domain.config.Configuration

/**
 * Remembers state of several components.
 * Implemented as singleton. Is mutable.
 */
object SessionState {
    var selectedChart: FullChart? = null
    var selectedConfig: Configuration? = null
    fun deSelectChart() {
        selectedChart = null
    }

    fun selectedChartIsSet(): Boolean {
        return selectedChart != null
    }

    fun selectedConfigIsSet(): Boolean {
        return selectedConfig != null
    }

}