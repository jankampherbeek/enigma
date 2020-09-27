/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.util

import com.radixpro.enigma.be.calc.SeFrontend

object PositionInHouse {

    fun defineHouseNrEcliptical(armc: Double,
                                geoLat: Double,
                                eps: Double,
                                hsys: Int,
                                lon: Double): Int {
        return defineHouseNrAndSeparationEcliptical(armc, geoLat, eps, hsys, lon).toInt()
    }

    fun defineHouseNrMundane(armc: Double,
                             geoLat: Double,
                             eps: Double,
                             hsys: Int,
                             lonLat: DoubleArray): Int {
        return defineHouseNrAndSeparationMundane(armc, geoLat, eps, hsys, lonLat).toInt()
    }

    fun defineHouseNrAndSeparationEcliptical(armc: Double,
                                             geoLat: Double,
                                             eps: Double,
                                             hsys: Int,
                                             lon: Double): Double {
        val lonLat = arrayOf(lon, 0.0).toDoubleArray()
        return defineHouseNrAndSeparationMundane(armc, geoLat, eps, hsys, lonLat)
    }

    fun defineHouseNrAndSeparationMundane(armc: Double,
                                          geoLat: Double,
                                          eps: Double,
                                          hsys: Int,
                                          lonLat: DoubleArray): Double {
        var error = StringBuffer()
        return (SeFrontend.getPositionInHouse(armc, geoLat, eps, hsys, lonLat, error))
    }


}
