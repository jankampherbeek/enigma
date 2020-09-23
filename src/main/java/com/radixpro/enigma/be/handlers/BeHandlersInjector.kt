/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.handlers

import com.radixpro.enigma.be.analysis.BeAnalysisInjector
import com.radixpro.enigma.be.calc.BeCalcInjector
import com.radixpro.enigma.be.persistency.BePersistencyInjector
import com.radixpro.enigma.xchg.api.XchgApiInjector

object BeHandlersInjector {
    @JvmStatic
    fun injectAspectsHandler(): AspectsHandler {
        return AspectsHandler(BeAnalysisInjector.injectAspectsForRadix())
    }

    @JvmStatic
    fun injectCalculatedChartHandler(): CalculatedChartHandler {
        return CalculatedChartHandler(injectFullPointPositionHandler(), injectMundanePositionsHandler())
    }

    @JvmStatic
    fun injectDataFileHandler(): DataFileHandler {
        return DataFileHandler(BePersistencyInjector.injectDataFileDao(), XchgApiInjector.injectPersistedPropertyApi())
    }

    @JvmStatic
    fun injectEphProgCalcHandler(): EphProgCalcHandler {
        return EphProgCalcHandler()
    }

    fun injectFullPointPositionHandler(): FullPointPositionHandler {
        return FullPointPositionHandler()
    }

    @JvmStatic
    fun injectInputDataFileHandler(): InputDataFileHandler {
        return InputDataFileHandler(BePersistencyInjector.injectDataReaderCsv(), BePersistencyInjector.injectJsonWriter())
    }

    @JvmStatic
    fun injectMidpointsHandler(): MidpointsHandler {
        return MidpointsHandler(BeAnalysisInjector.injectMidpointsForRadix())
    }

    fun injectMundanePositionsHandler(): MundanePositionsHandler {
        return MundanePositionsHandler(injectObliquityHandler())
    }

    @JvmStatic
    fun injectObliquityHandler(): ObliquityHandler {
        return ObliquityHandler()
    }

    @JvmStatic
    fun injectPrimaryHandler(): PrimaryHandler {
        return PrimaryHandler(injectPrimaryPositionsHandler(), injectTimeKeyHandler(), injectObliquityHandler(),
                BeCalcInjector.injectSpaeculumPropSaCalculator())
    }

    fun injectPrimaryPositionsHandler(): PrimaryPositionsHandler {
        return PrimaryPositionsHandler()
    }

    @JvmStatic
    fun injectProgAspectHandler(): ProgAspectHandler {
        return ProgAspectHandler(BeAnalysisInjector.injectProgRadixAspects())
    }

    @JvmStatic
    fun injectSecundaryDateHandler(): SecundaryDateHandler {
        return SecundaryDateHandler()
    }

    @JvmStatic
    fun injectSolarReturnHandler(): SolarReturnHandler {
        return SolarReturnHandler(BeCalcInjector.injectJdFromPosCalc(), XchgApiInjector.injectCalculatedChartApi())
    }

    @JvmStatic
    fun injectTetenburgHandler(): TetenburgHandler {
        return TetenburgHandler(injectObliquityHandler())
    }

    @JvmStatic
    fun injectTimeKeyHandler(): TimeKeyHandler {
        return TimeKeyHandler(injectSecundaryDateHandler(), injectFullPointPositionHandler())
    }
}