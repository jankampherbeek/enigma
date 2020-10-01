/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.persistency

import com.radixpro.enigma.be.persistency.mappers.BePersMappersInjector.injectInputDataSetMapper
import com.radixpro.enigma.be.persistency.mappers.BePersMappersInjector.injectStatsProjMapper
import com.radixpro.enigma.shared.converters.ShConvertersInjector.injectCsv2LocationConverter
import com.radixpro.enigma.ui.helpers.UiHelpersInjector

object BePersistencyInjector {
    fun injectChartDataDao(): ChartDataDao {
        return ChartDataDao()
    }

    fun injectConfigurationDao(): ConfigurationDao {
        return ConfigurationDao()
    }

    fun injectDataFileDao(): DataFileDao {
        return DataFileDao(injectJsonReader(), injectInputDataSetMapper())
    }

    fun injectDataReaderCsv(): DataReaderCsv {
        return DataReaderCsv(injectCsv2LocationConverter(), UiHelpersInjector.injectDateTimeJulianCreator())
    }

    fun injectJsonReader(): JsonReader {
        return JsonReader()
    }

    fun injectJsonWriter(): JsonWriter {
        return JsonWriter()
    }

    fun injectPropertyDao(): PropertyDao {
        return PropertyDao()
    }

    fun injectStatsProjDao(): StatsProjDao {
        return StatsProjDao(injectJsonWriter(), injectJsonReader(), injectStatsProjMapper())
    }

    fun injectVersionDao(): VersionDao {
        return VersionDao()
    }
}