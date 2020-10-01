/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.shared.converters

import com.radixpro.enigma.Rosetta
import com.radixpro.enigma.references.TimeZones
import com.radixpro.enigma.shared.common.EnigmaDictionary.DEGREESIGN
import com.radixpro.enigma.shared.common.EnigmaDictionary.MINUTESIGN
import com.radixpro.enigma.shared.common.EnigmaDictionary.SECONDSIGN

/**
 * Converts inputted elements and creates instance of InputData.
 */
class InputDataConversionBuilder {
    private var locationName: String? = null
    private var latitude: String? = null
    private var longitude: String? = null
    private var date: String? = null
    private var time: String? = null
    private var offsetLmt: String? = null
    private var source: String? = null

    fun setLocationName(locationName: String): InputDataConversionBuilder {
        this.locationName = locationName
        return this
    }

    fun setLatitude(deg: Int, min: Int, sec: Int, direction: String): InputDataConversionBuilder {
        latitude = createDMS(deg, min, sec) + direction
        return this
    }

    fun setLongitude(deg: Int, min: Int, sec: Int, direction: String): InputDataConversionBuilder {
        longitude = createDMS(deg, min, sec) + direction
        return this
    }

    fun setDate(year: Int, month: Int, day: Int, cal: String): InputDataConversionBuilder {
        date = "$year/$month/$day $cal"
        return this
    }

    fun setTime(hour: Int, min: Int, sec: Int, zone: TimeZones, dst: Boolean): InputDataConversionBuilder {
        val hourTxt = if (hour <= 9) "0" else "" + hour
        val minTxt = if (min <= 9) "0" else "" + min
        val secTxt = if (sec <= 9) "0" else "" + sec
        val zoneTxt = Rosetta.getText(zone.nameForRB)
        val dstText = if (dst) Rosetta.getText("ui.shared.dst") else Rosetta.getText("ui.shared.nodst")
        time = "$hourTxt:$minTxt:$secTxt $zoneTxt. $dstText"
        return this
    }

    fun setOffsetLmt(deg: Int, min: Int, sec: Int, direction: String): InputDataConversionBuilder {
        offsetLmt = "Offset " + createDMS(deg, min, sec) + direction
        return this
    }

    fun setSource(source: String): InputDataConversionBuilder {
        this.source = source
        return this
    }

    private fun createDMS(deg: Int, min: Int, sec: Int): String {
        val degTxt = if (deg <= 9) "0" else "" + deg
        val minTxt = if (min <= 9) "0" else "" + min
        val secTxt = if (sec <= 9) "0" else "" + sec
        return degTxt + DEGREESIGN + minTxt + MINUTESIGN + secTxt + SECONDSIGN
    }

    fun build(): String {
        var inputData = ""
        if (null != locationName && !locationName!!.isBlank()) inputData += "$locationName,"
        if (null != latitude) inputData += "$latitude,"
        if (null != longitude) inputData += """
     $longitude
     
     """.trimIndent()
        if (null != date) inputData += "$date "
        if (null != time) inputData += """
     $time
     
     """.trimIndent()
        if (null != offsetLmt) inputData += """
     $offsetLmt
     
     """.trimIndent()
        if (null != source && !source!!.isBlank()) inputData += source
        return inputData
    }
}