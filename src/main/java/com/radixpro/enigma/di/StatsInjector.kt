/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.di


import com.radixpro.enigma.be.persistency.BePersistencyInjector
import com.radixpro.enigma.be.persistency.BePersistencyInjector.injectJsonReader
import com.radixpro.enigma.be.persistency.BePersistencyInjector.injectJsonWriter
import com.radixpro.enigma.be.persistency.BePersistencyInjector.injectPropertyDao
import com.radixpro.enigma.be.persistency.mappers.BePersMappersInjector.injectInputDataSetMapper
import com.radixpro.enigma.statistics.api.StatsProjApi
import com.radixpro.enigma.statistics.process.ControlDataCalendar
import com.radixpro.enigma.statistics.process.ControlDataCharts
import com.radixpro.enigma.statistics.process.StatsProjHandler

object StatsInjector {

    @JvmStatic
    fun injectStatsProjApi(): StatsProjApi {
        return StatsProjApi(injectStatsProjHandler())
    }

    fun injectStatsProjHandler(): StatsProjHandler {
        return StatsProjHandler(BePersistencyInjector.injectStatsProjDao(), injectPropertyDao(), injectControlDataCharts())
    }

    fun injectControlDataCalendar(): ControlDataCalendar {
        return ControlDataCalendar()
    }

    fun injectControlDataCharts(): ControlDataCharts {
        return ControlDataCharts(injectJsonReader(), injectJsonWriter(), injectPropertyDao(), injectInputDataSetMapper(), injectControlDataCalendar())
    }

}