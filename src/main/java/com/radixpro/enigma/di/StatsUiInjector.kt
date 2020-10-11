/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.di

import com.radixpro.enigma.di.StatsInjector.injectInputDataFileApi
import com.radixpro.enigma.di.StatsInjector.injectPersistedDataFileApi
import com.radixpro.enigma.di.StatsInjector.injectStatsProjApi
import com.radixpro.enigma.statistics.ui.*
import com.radixpro.enigma.ui.screens.blocks.ScreensBlocksInjector.injectBaseConfigInputBlock

import com.radixpro.enigma.xchg.api.XchgApiInjector.injectPersistedPropertyApi
import javafx.stage.DirectoryChooser

object StatsUiInjector {

    fun injectProjectManager(): ProjectManager {
        return ProjectManager()
    }

    fun injectStatsDataDetail(): StatsDataDetail {
        return StatsDataDetail()
    }

    fun injectStatsDataNew(): StatsDataNew {
        return StatsDataNew(injectInputDataFileApi(), injectPersistedPropertyApi())
    }

    fun injectStatsDataSearch(): StatsDataSearch {
        return StatsDataSearch(injectPersistedDataFileApi())
    }

    fun injectStatsProjNew(): StatsProjNew {
        return StatsProjNew(injectBaseConfigInputBlock(), injectStatsDataSearch(), injectStatsProjApi())
    }

    fun injectStatsProjSearch(): StatsProjSearch {
        return StatsProjSearch(injectStatsProjApi())
    }

    fun injectStatsStart(): StatsStart {
        return StatsStart(injectStatsDataNew(), injectStatsDataDetail(), injectStatsDataSearch(), injectStatsProjSearch(), injectStatsProjNew(),
                injectProjectManager(), injectPersistedPropertyApi(), DirectoryChooser())
    }
}