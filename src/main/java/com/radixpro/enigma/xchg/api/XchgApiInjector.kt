/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.xchg.api

import com.radixpro.enigma.be.handlers.BeHandlersInjector
import com.radixpro.enigma.be.handlers.BeHandlersInjector.injectAspectsHandler
import com.radixpro.enigma.be.handlers.BeHandlersInjector.injectCalculatedChartHandler
import com.radixpro.enigma.be.handlers.BeHandlersInjector.injectDataFileHandler
import com.radixpro.enigma.be.handlers.BeHandlersInjector.injectEphProgCalcHandler
import com.radixpro.enigma.be.handlers.BeHandlersInjector.injectInputDataFileHandler
import com.radixpro.enigma.be.handlers.BeHandlersInjector.injectMidpointsHandler
import com.radixpro.enigma.be.handlers.BeHandlersInjector.injectPrimaryHandler
import com.radixpro.enigma.be.handlers.BeHandlersInjector.injectProgAspectHandler
import com.radixpro.enigma.be.handlers.BeHandlersInjector.injectSecundaryDateHandler
import com.radixpro.enigma.be.handlers.BeHandlersInjector.injectSolarReturnHandler
import com.radixpro.enigma.be.handlers.BeHandlersInjector.injectTetenburgHandler
import com.radixpro.enigma.be.persistency.BePersistencyInjector

object XchgApiInjector {
    @JvmStatic
    fun injectAspectsApi(): AspectsApi {
        return AspectsApi(injectAspectsHandler())
    }

    @JvmStatic
    fun injectCalculatedChartApi(): CalculatedChartApi {
        return CalculatedChartApi(injectCalculatedChartHandler())
    }

    @JvmStatic
    fun injectDateTimeApi(): DateTimeApi {
        return DateTimeApi()
    }

    @JvmStatic
    fun injectMidpointsApi(): MidpointsApi {
        return MidpointsApi(injectMidpointsHandler())
    }

    @JvmStatic
    fun injectInputDataFileApi(): InputDataFileApi {
        return InputDataFileApi(injectInputDataFileHandler())
    }

    @JvmStatic
    fun injectPersistedChartDataApi(): PersistedChartDataApi {
        return PersistedChartDataApi(BePersistencyInjector.injectChartDataDao())
    }

    @JvmStatic
    fun injectPersistedConfigurationApi(): PersistedConfigurationApi {
        return PersistedConfigurationApi(BePersistencyInjector.injectConfigurationDao())
    }

    @JvmStatic
    fun injectPersistedDataFileApi(): PersistedDataFileApi {
        return PersistedDataFileApi(injectDataFileHandler())
    }

    @JvmStatic
    fun injectPersistedPropertyApi(): PersistedPropertyApi {
        return PersistedPropertyApi(BePersistencyInjector.injectPropertyDao())
    }

    @JvmStatic
    fun injectPrimaryApi(): PrimaryApi {
        return PrimaryApi(injectPrimaryHandler())
    }

    @JvmStatic
    fun injectSecundaryApi(): SecundaryApi {
        return SecundaryApi(injectEphProgCalcHandler(), injectSecundaryDateHandler(),
                injectProgAspectHandler())
    }

    fun injectSolarReturnApi(): SolarReturnApi {
        return SolarReturnApi(injectSolarReturnHandler())
    }

    @JvmStatic
    fun injectStatsProjApi(): StatsProjApi {
        return StatsProjApi(BeHandlersInjector.injectStatsProjHandler())
    }

    @JvmStatic
    fun injectTetenburgApi(): TetenburgApi {
        return TetenburgApi(injectTetenburgHandler())
    }

    @JvmStatic
    fun injectTransitsApi(): TransitsApi {
        return TransitsApi(injectEphProgCalcHandler(), injectProgAspectHandler())
    }

    fun injectVersionApi(): VersionApi {
        return VersionApi(BePersistencyInjector.injectVersionDao())
    }
}