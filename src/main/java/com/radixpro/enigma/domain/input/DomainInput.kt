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

data class DateTimeParts(val year: Int,
                         val month: Int,
                         val day: Int,
                         val hour: Int,
                         val minute: Int,
                         val second: Int,
                         val offsetUt: Double)


data class DateTimeJulian(val jd: Double,
                          val calendar: String)

data class Location(val geoLat: Double,
                    val geoLon: Double)

data class ChartInputData(val id: Int,
                          val name: String,
                          val dateTimeParts: DateTimeParts,
                          val dateTime: DateTimeJulian,
                          val location: Location)