/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.process

import com.radixpro.enigma.statistics.process.ControlDataCalendar
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class ControlDataCalendarTest {


    @Test
    fun dayFitsInMonthHappyFlow() {
        assertTrue(ControlDataCalendar().dayFitsInMonth(28, 5, 2003))
    }

    @Test
    fun dayFitsInMonthDayTooLarge() {
        assertFalse(ControlDataCalendar().dayFitsInMonth(32, 1, 1953))
    }

    @Test
    fun dayFitsInMonthLeapDayInLeapYear() {
        assertTrue(ControlDataCalendar().dayFitsInMonth(29, 2, 2004))
    }

    @Test
    fun dayFitsInMonthLepaDayNotInLeapYear() {
        assertFalse(ControlDataCalendar().dayFitsInMonth(29, 2, 2003))
    }

    @Test
    fun dayFitsInMonth29NoLeapYear() {
        assertTrue(ControlDataCalendar().dayFitsInMonth(29, 1, 2003))
    }
}