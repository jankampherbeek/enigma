/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.process

class ControlDataCalendar {
    private val months31 = listOf(1, 3, 5, 7, 8, 10, 12)
    private val months30 = listOf(1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)

    /**
     * Use only for Gregorian calendar and for
     */
    fun dayFitsInMonth(day: Int, month: Int, year: Int): Boolean {
        return (day < 29
                || day == 29 && 2 != month
                || day == 30 && months30.contains(month)
                || day == 31 && months31.contains(month)
                || isLeapYear(year) && day < 30)
    }

    private fun isLeapYear(year: Int): Boolean {
        return year % 400 == 0 || year % 100 != 0 && year % 4 == 0
    }
}