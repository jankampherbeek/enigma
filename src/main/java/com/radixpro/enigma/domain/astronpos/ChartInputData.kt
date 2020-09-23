/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.astronpos

import com.radixpro.enigma.domain.input.DateTimeJulian
import com.radixpro.enigma.domain.input.Location

/**
 * Inputdata for a chart. Reflects the internal Json structure for inputdata for charts.
 */
data class ChartInputData(val id: Int, val name: String, val dateTime: DateTimeJulian, val location: Location)