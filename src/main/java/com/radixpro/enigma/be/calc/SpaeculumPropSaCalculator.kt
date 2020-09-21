/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.calc

import com.radixpro.enigma.be.handlers.ObliquityHandler
import com.radixpro.enigma.domain.astronpos.CalculatedChart
import com.radixpro.enigma.domain.astronpos.SpaeculumPropSaData
import com.radixpro.enigma.domain.astronpos.SpaeculumPropSaItem
import com.radixpro.enigma.references.CelestialObjects
import com.radixpro.enigma.references.ChartPointTypes
import com.radixpro.enigma.shared.Range
import com.radixpro.enigma.shared.exceptions.EnigmaMathException
import com.radixpro.enigma.xchg.api.settings.ICalcSettings
import com.radixpro.enigma.xchg.domain.IChartPoints
import java.util.*

/**
 * Spaeculum for primary directions that use proportions of the Placidian semi arcs.
 */
class SpaeculumPropSaCalculator(private val obliquityHandler: ObliquityHandler) {
    private val items: MutableList<SpaeculumPropSaItem> = ArrayList()
    private var mc = 0.0
    private var raMcRx = 0.0
    private var asc = 0.0
    private var eps = 0.0

    fun performCalculation(calculatedChart: CalculatedChart,
                           jdUt: Double,
                           geoLat: Double,
                           settings: ICalcSettings): SpaeculumPropSaData {
        eps = obliquityHandler.calcTrueObliquity(jdUt)
        mc = calculatedChart.mundPoints.mc.longitude
        raMcRx = CoordinateConversions.eclipticToEquatorial(doubleArrayOf(mc, 0.0), eps)[0]
        asc = calculatedChart.mundPoints.asc.longitude
        for (point in settings.points) {
            if (ChartPointTypes.CEL_BODIES == point.pointType) items.add(itemForCelestialBody(point.id, geoLat, calculatedChart))
        }
        return SpaeculumPropSaData(raMcRx, items)
    }

    private fun itemForCelestialBody(id: Int,
                                     geoLat: Double,
                                     calculatedChart: CalculatedChart): SpaeculumPropSaItem {
        var lon = 0.0
        var decl = 0.0
        var ra = 0.0
        var sa = 0.0
        var propSa = 0.0
        var quadrant = 0
        var chartPoint: IChartPoints = CelestialObjects.EMPTY
        for (point in calculatedChart.celPoints) {
            if (id == point.chartPoint.id) {
                chartPoint = point.chartPoint
                lon = point.longitude
                decl = point.declination
                ra = CoordinateConversions.eclipticToEquatorial(doubleArrayOf(lon, 0.0), eps)[0]
                try {
                    sa = EnigmaAstronMath.semiArc(ra, geoLat, eps)
                } catch (eme: EnigmaMathException) {
                    // TODO log error, make no item for spaeculum
                }
                quadrant = defineQuadrant(lon, mc, asc)
                propSa = getPlacideanPropOfSa(raMcRx, ra, sa, quadrant)
            }
        }
        return SpaeculumPropSaItem(chartPoint, lon, ra, decl, sa, propSa, quadrant)
    }

    /**
     * Proportion of semi-arc based on the Placidean method.
     * The arc is calculated as part from a quadrant.
     * Cusp XI is 1/3, cusp XII is 2/3, ascendant is 0.
     *
     * @param raMc   Right ascension of MC.
     * @param raPos  Right ascension of position where the proportion is calculated for.
     * @return The proportion of the semi-arc.
     */
    private fun getPlacideanPropOfSa(raMc: Double,
                                     raPos: Double,
                                     sa: Double,
                                     quadrant: Int): Double {
        var meridianDistance = raPos - raMc
        if (quadrant == 4 || quadrant == 2) {
            meridianDistance = Range(0.0, 180.0).checkValue(meridianDistance)
        } else if (quadrant == 1 || quadrant == 3) {
            meridianDistance = Range(-180.0, 0.0).checkValue(meridianDistance)
        }
        var proportion = meridianDistance / sa
        if (quadrant == 1 || quadrant == 3) proportion = -proportion
        return proportion
    }

    private fun defineQuadrant(lon: Double,
                               mc: Double,
                               asc: Double): Int {
        val distanceFromMC = Range(0.0, 360.0).checkValue(lon - mc)
        val distanceFromAsc = Range(0.0, 360.0).checkValue(lon - asc)
        var eastHemisphere = false
        var southHemisphere = false
        if (distanceFromMC < 180.0) eastHemisphere = true
        if (distanceFromAsc < 180.0) southHemisphere = true
        return if (eastHemisphere && southHemisphere) 1 else if (eastHemisphere && !southHemisphere) 4 else if (!eastHemisphere && southHemisphere) 2 else 3
    }
}