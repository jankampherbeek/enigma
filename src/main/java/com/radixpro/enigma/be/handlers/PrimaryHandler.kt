/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.handlers

import com.radixpro.enigma.be.calc.CoordinateConversions
import com.radixpro.enigma.be.calc.EnigmaAstronMath
import com.radixpro.enigma.be.calc.EnigmaMath
import com.radixpro.enigma.be.calc.SpaeculumPropSaCalculator
import com.radixpro.enigma.be.util.Range
import com.radixpro.enigma.domain.astronpos.IPosition
import com.radixpro.enigma.domain.astronpos.LonDeclPosition
import com.radixpro.enigma.domain.reqresp.PrimaryCalcRequest
import com.radixpro.enigma.domain.reqresp.SimpleProgResponse
import com.radixpro.enigma.shared.exceptions.UnknownTimeKeyException
import java.util.*

class PrimaryHandler(private val primaryPositionsHandler: PrimaryPositionsHandler,
                     private val timeKeyHandler: TimeKeyHandler,
                     private val obliquityHandler: ObliquityHandler,
                     private val spsCalculator: SpaeculumPropSaCalculator) {


    fun performCalculations(request: PrimaryCalcRequest): SimpleProgResponse {
        val responsePositions: MutableList<IPosition> = ArrayList()
        val calculatedChart = request.calculatedChart
        val geoLat = request.location.geoLat
        try {
            val solarArc = timeKeyHandler.retrieveTimeSpan(request.dateTimeRadix, request.dateTime, request.timeKey, request.location, request.settings)
            val eps = obliquityHandler.calcTrueObliquity(request.dateTimeRadix.jd)
            val prMc = Range.checkValue(calculatedChart.mundPoints.mc.longitude + solarArc)
            val prRaMc = CoordinateConversions.eclipticToEquatorial(doubleArrayOf(prMc, 0.0), eps)[0]
            val prAsc = EnigmaAstronMath.ascFromRamc(prRaMc, geoLat, eps)
            val (raMcRx, items) = spsCalculator.performCalculation(calculatedChart, request.dateTimeRadix.jd, request.location.geoLat, request.settings)
            for ((chartPoint, _, ra, _, _, propSa, quadrant) in items) {
                val offset = ra - raMcRx
                val raProg = placideanIterator(geoLat, eps, prRaMc, offset, propSa, quadrant)
                val lonProg = CoordinateConversions.equatorialToEcliptic(raProg, eps)
                val declProg = CoordinateConversions.longitudeToDeclination(lonProg, eps)
                responsePositions.add(LonDeclPosition(chartPoint, lonProg, declProg))
            }
        } catch (utke: UnknownTimeKeyException) {
            // TODO LOG and create error msg
        }
        return SimpleProgResponse(responsePositions, request)
    }

    private fun placideanIterator(geoLat: Double, eps: Double, rightAscMC: Double, offsetForPosition: Double, factor: Double, quadrant: Int): Double {
        val workOffsetForPosition: Double = offsetForPosition
        val workFactor: Double = factor
        val margin = 0.00001
        var diff = 1.0
        var currentRightAscension = rightAscMC + offsetForPosition
        var tempRightAscension: Double
        val tanEpsilon = EnigmaMath.tan(eps)
        val tanGeoLatitude = EnigmaMath.tan(geoLat)
        while (diff > margin) {
            tempRightAscension = if (workOffsetForPosition < 90) {
                rightAscMC + EnigmaMath.acos(-EnigmaMath.sin(currentRightAscension) * tanEpsilon * tanGeoLatitude) * workFactor
            } else {
                180 + rightAscMC - EnigmaMath.acos(EnigmaMath.sin(currentRightAscension) * tanEpsilon * tanGeoLatitude) * workFactor
            }
            diff = Math.abs(tempRightAscension - currentRightAscension)
            currentRightAscension = tempRightAscension
        }
        return Range.checkValue(currentRightAscension)
    }
}