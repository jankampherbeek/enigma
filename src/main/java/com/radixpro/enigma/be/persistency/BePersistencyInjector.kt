/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.persistency

import com.radixpro.enigma.share.di.ShareInjector.injectJsonReader
import com.radixpro.enigma.statistics.di.StatsInjector.injectInputDataSetMapper
import com.radixpro.enigma.statistics.di.StatsInjector.injectStatsPathConstructor
import com.radixpro.enigma.statistics.di.StatsInjector.injectStatsProjMapper
import com.radixpro.enigma.statistics.persistency.GlobalDataDao
import com.radixpro.enigma.statistics.persistency.StatsProjDaoJson

object BePersistencyInjector {
    fun injectChartDataDao(): ChartDataDao {
        return ChartDataDao()
    }

    fun injectConfigurationDao(): ConfigurationDao {
        return ConfigurationDao()
    }

    fun injectDataFileDao(): GlobalDataDao {
        return GlobalDataDao(injectJsonReader(), injectInputDataSetMapper(), injectStatsPathConstructor())
    }

    fun injectStatsProjDao(): StatsProjDaoJson {
        return StatsProjDaoJson(injectJsonReader(), injectStatsProjMapper())
    }

    fun injectVersionDao(): VersionDao {
        return VersionDao()
    }
}