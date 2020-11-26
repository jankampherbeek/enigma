/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.di


import com.radixpro.enigma.be.calc.SeFrontend
import com.radixpro.enigma.be.persistency.BePersistencyInjector
import com.radixpro.enigma.share.di.ShareInjector.injectFileReader
import com.radixpro.enigma.share.di.ShareInjector.injectGlobalPropertyApi
import com.radixpro.enigma.share.di.ShareInjector.injectGlobalPropertyHandler
import com.radixpro.enigma.share.di.ShareInjector.injectJsonReader
import com.radixpro.enigma.share.di.ShareInjector.injectJsonWriter
import com.radixpro.enigma.shared.converters.Csv2LocationConverter
import com.radixpro.enigma.statistics.api.GlobalDataApi
import com.radixpro.enigma.statistics.api.ScenGeneralApi
import com.radixpro.enigma.statistics.api.StatsProcessApi
import com.radixpro.enigma.statistics.api.StatsProjApi
import com.radixpro.enigma.statistics.converters.ProjectConverter
import com.radixpro.enigma.statistics.converters.ScenConverterFactory
import com.radixpro.enigma.statistics.converters.ScenMinMaxConverter
import com.radixpro.enigma.statistics.converters.ScenRangeConverter
import com.radixpro.enigma.statistics.persistency.*
import com.radixpro.enigma.statistics.process.*
import com.radixpro.enigma.xchg.api.PersistedDataFileApi

object StatsInjector {

    fun injectChartDataCsvMapper(): ChartDataCsvMapper {
        return ChartDataCsvMapper()
    }

    fun injectControlDataCalendar(): ControlDataCalendar {
        return ControlDataCalendar()
    }

    fun injectControlDataCharts(): ControlDataCharts {
        return ControlDataCharts(injectJsonReader(), injectGlobalPropertyHandler(), injectInputDataSetMapper(), injectControlDataCalendar())
    }

    fun injectCsv2LocationConverter(): Csv2LocationConverter {
        return Csv2LocationConverter()
    }

//    fun injectDataFileHandler(): DataFGlobalDataHandler {
//        return GlobalDataHandler(injectDataFileDao(), injectInputDataReader(), injectJsonWriter(), injectStatsPathConstructor())
//    }

    fun injectGlobalDataApi(): GlobalDataApi {
        return GlobalDataApi(injectGlobalDataHandler())
    }

    fun injectGlobalDataDao(): GlobalDataDao {
        return GlobalDataDao(injectJsonReader(), injectInputDataSetMapper(), injectStatsPathConstructor())
    }

    fun injectGlobalDataHandler(): GlobalDataHandler {
        return GlobalDataHandler(injectGlobalDataDao(), injectInputDataReader(), injectStatsProjHandler(), injectJsonWriter(), injectStatsPathConstructor())
    }

    fun injectProjectDataDao(): ProjectDataDao {
        return ProjectDataDao(injectJsonReader(), injectInputDataSetMapper(), injectStatsPathConstructor())
    }

    fun injectProjectDataHandler(): ProjectDataHandler {
        return ProjectDataHandler(injectProjectDataDao(), injectStatsProjHandler())
    }

    @JvmStatic
    fun injectPersistedDataFileApi(): PersistedDataFileApi {
        return PersistedDataFileApi(injectGlobalDataHandler())
    }

    fun injectInputDataReader(): InputDataReader {
        return InputDataReader(injectCsv2LocationConverter())
    }

    @JvmStatic
    fun injectInputDataSetMapper(): InputDataSetMapper {
        return InputDataSetMapper()
    }

    fun injectPointsCalculator(): PointsCalculator {
        return PointsCalculator(SeFrontend)
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

    fun injectScenHandler(): ScenHandler {
        return ScenHandler(injectScenarioRangePersister(), injectJsonReader(), injectScenSpecificMapper(), injectStatsPathConstructor())
    }

    fun injectScenSpecificMapper(): ScenarioMapper {
        return ScenarioSpecificMapper()
    }

    fun injectScenarioRangePersister(): ScenPersister {
        return ScenPersister()
    }

    fun injectScenConverterFactory(): ScenConverterFactory {
        return ScenConverterFactory()
    }

    fun injectScenGeneralApi(): ScenGeneralApi {
        return ScenGeneralApi(injectScenGeneralHandler(), injectScenHandler(), injectScenConverterFactory())
    }

    fun injectScenGeneralHandler(): ScenarioGeneralHandler {
        return ScenarioGeneralHandler(injectFileReader(), injectScenarioGeneralFileMapper(), injectStatsPathConstructor())
    }

    fun injectScenRangeProcessor(): ScenRangeProcessor {
        return ScenRangeProcessor(injectPointsCalculator(), injectStatsProjHandler(), injectProjectDataHandler(), injectStatsPathConstructor(),
                injectJsonReader(), SeFrontend)
    }

    fun injectScenMinMaxConverter(): ScenMinMaxConverter {
        return ScenMinMaxConverter()
    }


    fun injectStatsPathConstructor(): StatsPathConstructor {
        return StatsPathConstructor(injectGlobalPropertyApi())
    }

    fun injectScenRangeConverter(): ScenRangeConverter {
        return ScenRangeConverter()
    }

    fun injectStatsProcessApi(): StatsProcessApi {
        return StatsProcessApi(injectStatsProcessHandler(), injectScenConverterFactory())
    }

    @JvmStatic
    fun injectStatsProjApi(): StatsProjApi {
        return StatsProjApi(injectStatsProjHandler(), injectProjectConverter())
    }

    fun injectStatsProcessHandler(): StatsProcessHandler {
        return StatsProcessHandler(injectScenRangeProcessor())
    }

    fun injectStatsProjHandler(): StatsProjHandler {
        return StatsProjHandler(BePersistencyInjector.injectStatsProjDao(), injectGlobalPropertyHandler(), injectControlDataCharts())
    }

    fun injectStatsProjMapper(): StatsProjMapper {
        return StatsProjMapper()
    }
}