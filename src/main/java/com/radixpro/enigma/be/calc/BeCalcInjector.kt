/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.calc

import com.radixpro.enigma.be.handlers.BeHandlersInjector.injectObliquityHandler

object BeCalcInjector {
    fun injectCoordSetForDateTimeCalc(): CoordSetForDateTimeCalc {
        return CoordSetForDateTimeCalc()
    }

    fun injectJdFromPosCalc(): JdFromPosCalc {
        return JdFromPosCalc(injectCoordSetForDateTimeCalc())
    }

    fun injectSpaeculumPropSaCalculator(): SpaeculumPropSaCalculator {
        return SpaeculumPropSaCalculator(injectObliquityHandler())
    }
}