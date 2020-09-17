/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.handlers

import com.radixpro.enigma.be.calc.CoordinateConversions
import com.radixpro.enigma.be.calc.SeFrontend
import com.radixpro.enigma.shared.Range
import com.radixpro.enigma.shared.common.EnigmaDictionary

/**
 * Handler for the calculation of the Critical Point as defined by Ton Tetenburg.
 */
class TetenburgHandler(private val seFrontend: SeFrontend,
                       private val oblHandler: ObliquityHandler) {
    fun criticalPoint(jdRadix: Double,
                      jdEvent: Double,
                      geoLat: Double,
                      radixMc: Double,
                      solarSpeed: Double): Double {
        val eps = oblHandler.calcTrueObliquity(jdRadix)
        val jdDiff = jdEvent - jdRadix
        val nrOfYears = jdDiff / EnigmaDictionary.TROPICAL_YEAR
        val progMc = Range(0.0, 360.0).checkValue(radixMc + nrOfYears * solarSpeed)
        val eclValues = doubleArrayOf(progMc, 0.0, 1.0)
        val eqValues = CoordinateConversions.eclipticToEquatorial(eclValues, eps)
        val progRaMc = eqValues[0]
        return seFrontend.ascFromMc(progRaMc, geoLat, eps)
    }
}