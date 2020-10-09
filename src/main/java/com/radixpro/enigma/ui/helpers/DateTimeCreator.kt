/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.ui.helpers

import com.radixpro.enigma.domain.input.DateTimeJulian
import com.radixpro.enigma.domain.input.DateTimeParts
import com.radixpro.enigma.references.TimeZones
import swisseph.SweDate

object DateTimeCreator {

    fun createDateTimeJulian(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int,
                             zone: TimeZones, dst: Boolean, offsetLmt: Double, cal: String): DateTimeJulian {
        val jdFor0h = createJdFor0h(intArrayOf(year, month, day), cal)
        var time = hour + minute / 60.0 + second / 3600.0
        if (dst) time--
        time -= if (zone.name == "LMT") offsetLmt else zone.offset
        val jdEt = jdFor0h + time / 24.0
        return DateTimeJulian(jdEt, cal)
    }

    fun createDateTimeJulian(dateText: String, calendar: String, timeText: String,
                             zone: TimeZones, dst: Boolean, offsetLmt: Double): DateTimeJulian {
        val dateParts = createDateParts(dateText)
        val jdFor0h = createJdFor0h(dateParts, calendar)
        val time = createTime(timeText, zone, dst, offsetLmt)
        val jdEt = jdFor0h + time / 24.0
        return DateTimeJulian(jdEt, calendar)
    }

    fun createDateTimeParts(dateTxt: String, timeTxt: String, zone: TimeZones, dst: Boolean): DateTimeParts {
        val dateParts = createDateParts(dateTxt)
        val timeParts = createTimeParts(timeTxt)
        var dstValue = 0.0
        if (dst) dstValue = 1.0
        val offset = zone.offset + dstValue
        return DateTimeParts(dateParts[0], dateParts[1], dateParts[2], timeParts[0], timeParts[1], timeParts[2], offset)
    }

    private fun createDateParts(dateText: String): IntArray {
        val parts = dateText.split("/").toTypedArray()
        val year = parts[0].toInt()
        val month = parts[1].toInt()
        val day = parts[2].toInt()
        return intArrayOf(year, month, day)
    }

    private fun createJdFor0h(dateParts: IntArray, calendar: String): Double {
        val sweDate = SweDate(dateParts[0], dateParts[1], dateParts[2], 0.0, calendar.equals("G", ignoreCase = true))
        return sweDate.julDay
    }

    private fun createTime(timeText: String, zone: TimeZones, dst: Boolean, offsetLmt: Double): Double {
        val parts = createTimeParts(timeText)
        var time = parts[0] + parts[1] / 60.0 + parts[2] / 3600.0
        if (dst) time--
        time -= if (zone.name == "LMT") offsetLmt else zone.offset
        return time
    }

    private fun createTimeParts(timeText: String): IntArray {
        val parts = timeText.split(":").toTypedArray()
        val hour = parts[0].toInt()
        val minute = parts[1].toInt()
        val second = if (parts.size == 3) parts[2].toInt() else 0
        return intArrayOf(hour, minute, second)
    }
}