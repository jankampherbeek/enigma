/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.handlers

import com.radixpro.enigma.be.calc.SeFrontend
import swisseph.SweConst

/**
 * Obliquity of the earth-axis.
 */
class ObliquityHandler() {

    private val seFrontend: SeFrontend = SeFrontend

    fun calcTrueObliquity(jdUt: Double): Double {
        return performCalculation(jdUt)
    }

    private fun performCalculation(jdUt: Double): Double {
        val flags = 0
        val calculatedPos = seFrontend.getPositionsForEpsilon(jdUt, SweConst.SE_ECL_NUT, flags)
        return calculatedPos.allPositions[0]
    }
}