/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.di

import com.radixpro.enigma.share.di.ShareInjector.injectGlobalPropertyApi
import com.radixpro.enigma.statistics.di.StatsInjector.injectInputDataFileApi
import com.radixpro.enigma.statistics.di.StatsInjector.injectPersistedDataFileApi
import com.radixpro.enigma.statistics.di.StatsInjector.injectStatsProjApi
import com.radixpro.enigma.statistics.ui.*
import com.radixpro.enigma.ui.screens.blocks.ScreensBlocksInjector.injectBaseConfigInputBlock

import javafx.stage.DirectoryChooser

object StatsUiInjector {


    fun injectScenarioNew(): ScenarioNew {
        return ScenarioNew(injectScenarioRangeNew())
    }

    fun injectScenarioRangeNew(): ScenarioRangeNew {
        return ScenarioRangeNew()
    }

    fun injectProjectManager(): ProjectManager {
        return ProjectManager(injectScenarioNew())
    }

    fun injectStatsDataDetail(): StatsDataDetail {
        return StatsDataDetail()
    }

    fun injectStatsDataNew(): StatsDataNew {
        return StatsDataNew(injectInputDataFileApi(), injectGlobalPropertyApi())
    }

    fun injectStatsDataSearch(): StatsDataSearch {
        return StatsDataSearch(injectPersistedDataFileApi())
    }

    fun injectStatsFacade(): StatsFacade {
        return StatsFacade(injectStatsProjApi())
    }

    fun injectStatsProjNew(): StatsProjNew {
        return StatsProjNew(injectBaseConfigInputBlock(), injectStatsDataSearch(), injectStatsFacade())
    }

    fun injectStatsProjSearch(): StatsProjSearch {
        return StatsProjSearch(injectStatsProjApi())
    }

    fun injectStatsStart(): StatsStart {
        return StatsStart(injectStatsDataNew(), injectStatsDataDetail(), injectStatsDataSearch(), injectStatsProjSearch(), injectStatsProjNew(),
                injectProjectManager(), injectGlobalPropertyApi(), DirectoryChooser())
    }
}