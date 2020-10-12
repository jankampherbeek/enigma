/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.di


import com.radixpro.enigma.be.persistency.BePersistencyInjector
import com.radixpro.enigma.be.persistency.BePersistencyInjector.injectDataFileDao
import com.radixpro.enigma.be.persistency.BePersistencyInjector.injectDataReaderCsv
import com.radixpro.enigma.be.persistency.BePersistencyInjector.injectJsonReader
import com.radixpro.enigma.be.persistency.BePersistencyInjector.injectJsonWriter
import com.radixpro.enigma.be.persistency.BePersistencyInjector.injectPropertyDao
import com.radixpro.enigma.be.persistency.mappers.BePersMappersInjector.injectInputDataSetMapper
import com.radixpro.enigma.statistics.api.InputDataFileApi
import com.radixpro.enigma.statistics.api.StatsProjApi
import com.radixpro.enigma.statistics.process.*
import com.radixpro.enigma.xchg.api.PersistedDataFileApi
import com.radixpro.enigma.xchg.api.XchgApiInjector.injectPersistedPropertyApi

object StatsInjector {

    fun injectControlDataCalendar(): ControlDataCalendar {
        return ControlDataCalendar()
    }

    fun injectControlDataCharts(): ControlDataCharts {
        return ControlDataCharts(injectJsonReader(), injectJsonWriter(), injectPropertyDao(), injectInputDataSetMapper(), injectControlDataCalendar())
    }

    fun injectDataFileHandler(): DataFileHandler {
        return DataFileHandler(injectDataFileDao(), injectPersistedPropertyApi())
    }

    fun injectInputDataFileHandler(): InputDataFileHandler {
        return InputDataFileHandler(injectDataReaderCsv(), injectJsonWriter())
    }

    @JvmStatic
    fun injectPersistedDataFileApi(): PersistedDataFileApi {
        return PersistedDataFileApi(injectDataFileHandler())
    }

    fun injectInputDataFileApi(): InputDataFileApi {
        return InputDataFileApi(injectInputDataFileHandler())
    }

    @JvmStatic
    fun injectStatsProjApi(): StatsProjApi {
        return StatsProjApi(injectStatsProjHandler())
    }

    fun injectStatsProjHandler(): StatsProjHandler {
        return StatsProjHandler(BePersistencyInjector.injectStatsProjDao(), injectPropertyDao(), injectControlDataCharts())
    }

}