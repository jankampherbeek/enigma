/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.handlers

import com.radixpro.enigma.be.analysis.BeAnalysisInjector
import com.radixpro.enigma.be.calc.BeCalcInjector
import com.radixpro.enigma.xchg.api.XchgApiInjector

object BeHandlersInjector {

    fun injectAspectsHandler(): AspectsHandler {
        return AspectsHandler(BeAnalysisInjector.injectAspectsForRadix())
    }

    fun injectCalculatedChartHandler(): CalculatedChartHandler {
        return CalculatedChartHandler(injectFullPointPositionHandler(), injectMundanePositionsHandler())
    }


    fun injectEphProgCalcHandler(): EphProgCalcHandler {
        return EphProgCalcHandler()
    }

    fun injectFullPointPositionHandler(): FullPointPositionHandler {
        return FullPointPositionHandler()
    }

    fun injectMidpointsHandler(): MidpointsHandler {
        return MidpointsHandler(BeAnalysisInjector.injectMidpointsForRadix())
    }

    fun injectMundanePositionsHandler(): MundanePositionsHandler {
        return MundanePositionsHandler(injectObliquityHandler())
    }

    fun injectObliquityHandler(): ObliquityHandler {
        return ObliquityHandler()
    }

    fun injectPrimaryHandler(): PrimaryHandler {
        return PrimaryHandler(injectPrimaryPositionsHandler(), injectTimeKeyHandler(), injectObliquityHandler(),
                BeCalcInjector.injectSpaeculumPropSaCalculator())
    }

    fun injectPrimaryPositionsHandler(): PrimaryPositionsHandler {
        return PrimaryPositionsHandler()
    }

    fun injectProgAspectHandler(): ProgAspectHandler {
        return ProgAspectHandler(BeAnalysisInjector.injectProgRadixAspects())
    }

    fun injectSecundaryDateHandler(): SecundaryDateHandler {
        return SecundaryDateHandler()
    }

    fun injectSolarReturnHandler(): SolarReturnHandler {
        return SolarReturnHandler(BeCalcInjector.injectJdFromPosCalc(), XchgApiInjector.injectCalculatedChartApi())
    }

    fun injectTetenburgHandler(): TetenburgHandler {
        return TetenburgHandler(injectObliquityHandler())
    }

    @JvmStatic
    fun injectTimeKeyHandler(): TimeKeyHandler {
        return TimeKeyHandler(injectSecundaryDateHandler(), injectFullPointPositionHandler())
    }
}