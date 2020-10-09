package com.radixpro.enigma.be.persistency.mappers

import com.radixpro.enigma.Rosetta.getText
import com.radixpro.enigma.domain.input.ChartMetaData
import com.radixpro.enigma.domain.input.DateTimeJulian
import com.radixpro.enigma.domain.input.Location
import com.radixpro.enigma.references.ChartTypes
import com.radixpro.enigma.references.Ratings.Companion.getRatingForId
import com.radixpro.enigma.references.TimeZones
import com.radixpro.enigma.references.TimeZones.Companion.timeZoneForId
import com.radixpro.enigma.ui.helpers.DateTimeCreator
import com.radixpro.enigma.xchg.domain.FullChartInputData

/**
 * Should remain available up to release 2020.2 to support reading existing csv data.
 */
class ChartDataCsvMapper() {
    fun chartDataFromCsv(csvLine: Array<String>): FullChartInputData {
        return handleCsvLine(csvLine)
    }

    private fun handleCsvLine(csvLine: Array<String>): FullChartInputData {
        val id = csvLine[0].toInt()
        val chartMetaData = createMetaData(csvLine)
        val dateTime = createDateTime(csvLine)
        val location = createLocation(csvLine)
        return FullChartInputData(id, dateTime, location, chartMetaData)
    }

    private fun createMetaData(csvLine: Array<String>): ChartMetaData {
        val name = csvLine[1]
        val description = csvLine[2]
        val source = csvLine[3]
        val chartType = ChartTypes.chartTypeForId(csvLine[4].toInt())
        val rating = getRatingForId(csvLine[5].toInt())
        val SPACE = " "
        val NEWLINE = "\n"
        val dateText = createDateText(csvLine)
        val calText = if (csvLine[5].equals("Y", ignoreCase = true)) "G" else "J"
        val timeText = createTimeText(csvLine)
        val locationText = createLocationText(csvLine)
        val inputData = dateText + SPACE + calText + SPACE + timeText + NEWLINE + locationText + NEWLINE + getText("ui.shared.source") + SPACE +
                source
        return ChartMetaData(name, description, chartType, rating, inputData)
    }

    private fun createLocationText(csvLine: Array<String>): String {
        val locName = csvLine[16]
        val locLon = csvLine[17] + ":" + csvLine[18] + ":" + csvLine[19] + " " + csvLine[20]
        val locLat = csvLine[21] + ":" + csvLine[22] + ":" + csvLine[23] + " " + csvLine[24]
        return "$locName $locLon $locLat"
    }

    private fun createDateText(csvLine: Array<String>): String {
        val year = csvLine[6]
        val month = csvLine[7]
        val day = csvLine[8]
        val sep = "/"
        return year + sep + month + sep + day
    }

    private fun createTimeText(csvLine: Array<String>): String {
        val hour = csvLine[10]
        val minute = csvLine[11]
        val second = csvLine[12]
        val timeZone = timeZoneForId(csvLine[13].toInt())
        val zone = getText(timeZone.nameForRB)
        val dst = "y".equals(csvLine[14], ignoreCase = true)
        val dstText = getText(if ("y".equals(csvLine[14], ignoreCase = true)) "ui.shared.dst" else "ui.shared.nodst")
        val sep = ":"
        var offsetLmtTxt = ""
        if (timeZone === TimeZones.LMT) offsetLmtTxt = csvLine[15] + " "
        return "$hour$sep$minute$sep$second $zone $dstText $offsetLmtTxt"
    }

    private fun createDateTime(csvLine: Array<String>): DateTimeJulian {
        val dateText = createDateText(csvLine)
        val timeText = createTimeText(csvLine)
        val cal = csvLine[9]
        val timeZone = timeZoneForId(csvLine[13].toInt())
        val dst = "y".equals(csvLine[14], ignoreCase = true)
        val offsetLmt = csvLine[15].toDouble()
        return DateTimeCreator.createDateTimeJulian(dateText, cal, timeText, timeZone, dst, offsetLmt)
    }

    private fun createLocation(csvLine: Array<String>): Location {
        val latVal = csvLine[26].toDouble()
        val longVal = csvLine[21].toDouble()
        return Location(latVal, longVal)
    }
}