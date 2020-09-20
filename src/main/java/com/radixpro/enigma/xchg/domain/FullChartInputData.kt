/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */
package com.radixpro.enigma.xchg.domain

import com.radixpro.enigma.domain.input.ChartMetaData
import com.radixpro.enigma.domain.input.DateTimeJulian
import com.radixpro.enigma.domain.input.Location

open class FullChartInputData(val id: Int,
                              val dateTimeJulian: DateTimeJulian,
                              val location: Location,
                              val chartMetaData: ChartMetaData)