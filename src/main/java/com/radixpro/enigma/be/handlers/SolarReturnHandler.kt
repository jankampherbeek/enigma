/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.handlers

import com.google.common.base.Preconditions
import com.radixpro.enigma.be.calc.JdFromPosCalc
import com.radixpro.enigma.be.calc.assist.CombinedFlags
import com.radixpro.enigma.domain.astronpos.CalculatedChart
import com.radixpro.enigma.domain.input.DateTimeJulian
import com.radixpro.enigma.domain.input.Location
import com.radixpro.enigma.domain.reqresp.CalculatedChartRequest
import com.radixpro.enigma.references.CelestialObjects
import com.radixpro.enigma.references.EclipticProjections
import com.radixpro.enigma.references.ObserverPositions
import com.radixpro.enigma.references.SeFlags
import com.radixpro.enigma.shared.common.EnigmaDictionary.TROPICAL_YEAR
import com.radixpro.enigma.shared.exceptions.NoPositionFoundException
import com.radixpro.enigma.xchg.api.CalculatedChartApi
import com.radixpro.enigma.xchg.api.settings.ChartCalcSettings
import java.util.*

class SolarReturnHandler(private val jdFromPosCalc: JdFromPosCalc,
                         private val calculatedChartApi: CalculatedChartApi) {

    @Throws(NoPositionFoundException::class)
    fun getSolarReturnChart(longSun: Double,
                            birthDateTime: DateTimeJulian,
                            yearForReturn: Int,
                            location: Location,
                            settings: ChartCalcSettings): CalculatedChart {
        Preconditions.checkArgument(longSun >= 0.0 && longSun < 360.0)
        return createSolarReturnChart(longSun, birthDateTime, yearForReturn, location, settings)
    }

    @Throws(NoPositionFoundException::class)
    private fun createSolarReturnChart(longSun: Double,
                                       birthDateTime: DateTimeJulian,
                                       age: Int,
                                       location: Location,
                                       settings: ChartCalcSettings): CalculatedChart {
        val startJd: Double = birthDateTime.jd + age * TROPICAL_YEAR - 3.0
        val endJd: Double = birthDateTime.jd + age * TROPICAL_YEAR + 3.0
        val flags = defineFlags(settings)
        val jdActual = jdFromPosCalc.findJd(startJd, endJd, longSun, CelestialObjects.SUN, flags, location)
        val actualDateTime = DateTimeJulian(jdActual, birthDateTime.calendar)
        val request = CalculatedChartRequest(settings, actualDateTime, location)
        val (calculatedChart, resultMsg) = calculatedChartApi.calcChart(request)
        if ("OK" != resultMsg) {
            throw NoPositionFoundException(resultMsg)
        }
        return calculatedChart
    }

    private fun defineFlags(settings: ChartCalcSettings): Int {
        val allFlags: MutableList<SeFlags> = ArrayList()
        allFlags.add(SeFlags.SWISSEPH)
        if (EclipticProjections.SIDEREAL === settings.eclProj) {
            allFlags.add(SeFlags.SIDEREAL)
        }
        if (ObserverPositions.TOPOCENTRIC === settings.obsPos) {
            allFlags.add(SeFlags.TOPOCENTRIC)
        }
        return CombinedFlags().getCombinedValue(allFlags).toInt()
    }
}