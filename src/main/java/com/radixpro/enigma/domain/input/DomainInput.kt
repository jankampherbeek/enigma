/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.input

import com.radixpro.enigma.references.ChartTypes
import com.radixpro.enigma.references.Ratings
import java.io.Serializable

data class ChartMetaData(val name: String,
                         val description: String,
                         val chartType: ChartTypes,
                         val rating: Ratings,
                         val dataInput: String) : Serializable

data class DateTimeJulian(val jd: Double,
                          val calendar: String)

data class Location(val geoLat: Double,
                    val geoLon: Double)




