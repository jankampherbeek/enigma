/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.ui.common

import com.radixpro.enigma.di.StatsUiInjector.injectStatsStart
import com.radixpro.enigma.ui.screens.UiScreensInjector

object UiCommonInjector {

    fun injectDashboard(): Dashboard {
        return Dashboard(UiScreensInjector.injectChartsStart(), injectStatsStart())
    }
}