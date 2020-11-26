/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.di

import com.radixpro.enigma.share.di.ShareInjector.injectGlobalPropertyApi
import com.radixpro.enigma.statistics.di.StatsInjector.injectGlobalDataApi
import com.radixpro.enigma.statistics.di.StatsInjector.injectPersistedDataFileApi
import com.radixpro.enigma.statistics.di.StatsInjector.injectScenGeneralApi
import com.radixpro.enigma.statistics.di.StatsInjector.injectStatsProcessApi
import com.radixpro.enigma.statistics.di.StatsInjector.injectStatsProjApi
import com.radixpro.enigma.statistics.ui.*
import com.radixpro.enigma.statistics.ui.helpers.ScenSpecificDetailsText
import com.radixpro.enigma.ui.screens.blocks.ScreensBlocksInjector.injectBaseConfigInputBlock
import javafx.stage.DirectoryChooser

object StatsUiInjector {

    fun injectProcessingResult(): ProcessingResult {
        return ProcessingResult(injectStatsFacade())
    }

    fun injectScenarioDetails(): ScenarioDetails {
        return ScenarioDetails(injectStatsFacade(), injectScenSpecificDetailsText())
    }

    fun injectScenarioNew(): ScenarioNew {
        return ScenarioNew(injectScenarioRangeNew(), injectScenMinMaxNew())
    }

    fun injectScenMinMaxNew(): ScenMinMaxNew {
        return ScenMinMaxNew(injectStatsFacade())
    }

    fun injectScenarioRangeNew(): ScenarioRangeNew {
        return ScenarioRangeNew(injectStatsFacade())
    }

    fun injectScenSpecificDetailsText(): ScenSpecificDetailsText {
        return ScenSpecificDetailsText()
    }

    fun injectProjectManager(): ProjectManager {
        return ProjectManager(injectStatsFacade(), injectScenarioNew(), injectScenarioDetails(), injectProcessingResult())
    }

    fun injectStatsDataDetail(): StatsDataDetail {
        return StatsDataDetail()
    }

    fun injectStatsDataNew(): StatsDataNew {
        return StatsDataNew(injectGlobalDataApi(), injectGlobalPropertyApi())
    }

    fun injectStatsDataSearch(): StatsDataSearch {
        return StatsDataSearch(injectPersistedDataFileApi())
    }

    fun injectStatsFacade(): StatsFacade {
        return StatsFacade(injectStatsProjApi(), injectStatsProcessApi(), injectScenGeneralApi())
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