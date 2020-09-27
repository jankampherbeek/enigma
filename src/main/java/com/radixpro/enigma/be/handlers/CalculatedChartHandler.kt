/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.handlers

import com.radixpro.enigma.domain.astronpos.AstronSpecifics
import com.radixpro.enigma.domain.astronpos.CalculatedChart
import com.radixpro.enigma.domain.astronpos.IPosition
import com.radixpro.enigma.domain.input.DateTimeJulian
import com.radixpro.enigma.domain.input.Location
import com.radixpro.enigma.xchg.api.settings.ChartCalcSettings
import java.util.*

class CalculatedChartHandler(private val fullPointPositionHandler: FullPointPositionHandler,
                             private val mundanePositionsHandler: MundanePositionsHandler) {

    fun defineChart(settings: ChartCalcSettings,
                    dateTime: DateTimeJulian,
                    location: Location): CalculatedChart {
        val jdUt = dateTime.jd
        val eclProj = settings.eclProj
        val ayanamsha = settings.ayanamsha
        val celPoints = settings.points
        val fullPointPositions: MutableList<IPosition> = ArrayList()
        for (celPoint in celPoints) {
            val fullPointPosition = fullPointPositionHandler.definePosition(celPoint, jdUt, settings.obsPos, eclProj, ayanamsha, location)
            fullPointPositions.add(fullPointPosition)
        }
        val allMundanePositions = mundanePositionsHandler.definePositions(jdUt, eclProj, ayanamsha, settings.houseSystem, location)
        // FIXME: calcualte armc and eps
        val astronSpecifics = AstronSpecifics(0.0, 0.0)
        return CalculatedChart(fullPointPositions, allMundanePositions, astronSpecifics)
    }
}