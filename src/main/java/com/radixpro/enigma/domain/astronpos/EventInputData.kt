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
 * Inputdata for an event. Reflects the internal Json structure for inputdata for events.
 */
class EventInputData(val id: Int, val chartId: Int, val description: String, val dateTime: DateTimeJulian, val location: Location)