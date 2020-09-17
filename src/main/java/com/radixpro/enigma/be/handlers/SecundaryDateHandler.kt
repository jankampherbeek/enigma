/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.handlers

import com.radixpro.enigma.domain.input.DateTimeJulian
import com.radixpro.enigma.shared.common.EnigmaDictionary

class SecundaryDateHandler {
    fun calcSecundaryDate(birthDateTime: DateTimeJulian,
                          eventDateTime: DateTimeJulian): DateTimeJulian {
        val differenceInRealDays = getDifferenceInRealDays(birthDateTime, eventDateTime)
        val differenceInSecDays = getDifferenceInSecDays(differenceInRealDays)
        return getSecundaryDate(birthDateTime, differenceInSecDays)
    }

    private fun getDifferenceInRealDays(birthDateTime: DateTimeJulian,
                                        eventDateTime: DateTimeJulian): Double {
        return eventDateTime.jd - birthDateTime.jd
    }

    private fun getDifferenceInSecDays(differenceInRealDays: Double): Double {
        return differenceInRealDays / EnigmaDictionary.TROPICAL_YEAR
    }

    private fun getSecundaryDate(birthDateTime: DateTimeJulian,
                                 differenceInSecDays: Double): DateTimeJulian {
        val gregorian = birthDateTime.calendar
        val secundaryJdUt = birthDateTime.jd + differenceInSecDays
        return DateTimeJulian(secundaryJdUt, gregorian)
    }
}