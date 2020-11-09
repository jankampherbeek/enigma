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
import com.radixpro.enigma.be.persistency.mappers.BePersMappersInjector.injectInputDataSetMapper
import com.radixpro.enigma.share.di.ShareInjector.injectFileReader
import com.radixpro.enigma.share.di.ShareInjector.injectGlobalPropertyApi
import com.radixpro.enigma.share.di.ShareInjector.injectGlobalPropertyHandler
import com.radixpro.enigma.share.di.ShareInjector.injectJsonReader
import com.radixpro.enigma.statistics.api.InputDataFileApi
import com.radixpro.enigma.statistics.api.ScenGeneralApi
import com.radixpro.enigma.statistics.api.StatsProjApi
import com.radixpro.enigma.statistics.api.converters.ProjectConverter
import com.radixpro.enigma.statistics.api.converters.ScenConverterFactory
import com.radixpro.enigma.statistics.api.converters.ScenRangeConverter
import com.radixpro.enigma.statistics.persistency.ScenarioGeneralFileMapper
import com.radixpro.enigma.statistics.persistency.ScenarioMapper
import com.radixpro.enigma.statistics.persistency.ScenarioRangeMapper
import com.radixpro.enigma.statistics.persistency.ScenarioRangePersister
import com.radixpro.enigma.statistics.process.*
import com.radixpro.enigma.xchg.api.PersistedDataFileApi

object StatsInjector {

    fun injectControlDataCalendar(): ControlDataCalendar {
        return ControlDataCalendar()
    }

    fun injectControlDataCharts(): ControlDataCharts {
        return ControlDataCharts(injectJsonReader(), injectGlobalPropertyHandler(), injectInputDataSetMapper(), injectControlDataCalendar())
    }

    fun injectDataFileHandler(): InternalDataFileHandler {
        return InternalDataFileHandler(injectDataFileDao(), injectGlobalPropertyHandler())
    }

    fun injectInputDataFileApi(): InputDataFileApi {
        return InputDataFileApi(injectInputDataFileHandler())
    }

    fun injectInputDataFileHandler(): InputDataFileHandler {
        return InputDataFileHandler(injectDataReaderCsv())
    }

    @JvmStatic
    fun injectPersistedDataFileApi(): PersistedDataFileApi {
        return PersistedDataFileApi(injectDataFileHandler())
    }

    fun injectProjectConverter(): ProjectConverter {
        return ProjectConverter()
    }

    fun injectScenarioGeneralFileMapper(): ScenarioGeneralFileMapper {
        return ScenarioGeneralFileMapper()
    }

    fun injectScenarioGeneralHandler(): ScenarioGeneralHandler {
        return ScenarioGeneralHandler(injectFileReader(), injectScenarioGeneralFileMapper(), injectStatsPathConstructor())
    }


    fun injectScenarioHandlerFactory(): ScenarioHandlerFactory {
        return ScenarioHandlerFactory()
    }

    fun injectScenarioRangeHandler(): ScenarioRangeHandler {
        return ScenarioRangeHandler(injectScenarioRangePersister(), injectJsonReader(), injectScenarioRangeMapper(), injectStatsPathConstructor())
    }

    fun injectScenarioRangeMapper(): ScenarioMapper {
        return ScenarioRangeMapper()
    }

    fun injectScenarioRangePersister(): ScenarioRangePersister {
        return ScenarioRangePersister()
    }

    fun injectScenConverterFactory(): ScenConverterFactory {
        return ScenConverterFactory()
    }

    fun injectScenGeneralApi(): ScenGeneralApi {
        return ScenGeneralApi(injectScenarioHandlerFactory(), injectScenConverterFactory())
    }

    fun injectStatsPathConstructor(): PathConstructor {
        return StatsPathConstructor(injectGlobalPropertyApi())
    }

    fun injectScenRangeConverter(): ScenRangeConverter {
        return ScenRangeConverter()
    }

    @JvmStatic
    fun injectStatsProjApi(): StatsProjApi {
        return StatsProjApi(injectStatsProjHandler(), injectProjectConverter())
    }

    fun injectStatsProjHandler(): StatsProjHandler {
        return StatsProjHandler(BePersistencyInjector.injectStatsProjDao(), injectGlobalPropertyHandler(), injectControlDataCharts())
    }

}