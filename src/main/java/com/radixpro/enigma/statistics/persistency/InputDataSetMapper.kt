/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.statistics.persistency

import com.radixpro.enigma.domain.input.ChartInputData
import com.radixpro.enigma.domain.input.DateTimeJulian
import com.radixpro.enigma.domain.input.DateTimeParts
import com.radixpro.enigma.domain.input.Location
import com.radixpro.enigma.statistics.core.InputDataSet
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import java.util.*

/**
 * Converts from Json to InputDataSet
 */
class InputDataSetMapper {
    fun jsonToInputDataSet(jsonObject: JSONObject): InputDataSet {
        return constructInputDataSet(jsonObject)
    }

    private fun constructInputDataSet(jsonObject: JSONObject): InputDataSet {
        val name = jsonObject["name"] as String
        val description = jsonObject["description"] as String
        val origFileName = jsonObject["origFileName"] as String
        val dateTime = jsonObject["dateTime"] as String
        val allEntries = jsonObject["inputData"] as JSONArray
        val allInputData = constructEntries(allEntries)
        return InputDataSet(name, description, origFileName, dateTime, allInputData)
    }

    private fun constructEntries(inputData: JSONArray): List<ChartInputData> {
        val chartInputData: MutableList<ChartInputData> = ArrayList()
        for (dataObject in inputData) {
            val jsonObject = dataObject as JSONObject
            val id = jsonObject["id"].toString().toInt()
            val name = jsonObject["name"] as String
            val dateTimeJulian = createDateTime(jsonObject)
            val dateTimeParts = createDateTimeParts(jsonObject)
            val location = createLocation(jsonObject)
            chartInputData.add(ChartInputData(id, name, dateTimeParts, dateTimeJulian, location))
        }
        return chartInputData
    }

    private fun createDateTime(jsonObject: JSONObject): DateTimeJulian {
        val dateTimeObject = jsonObject["dateTime"] as JSONObject
        val jdNr = dateTimeObject["jd"] as Double
        val cal = dateTimeObject["calendar"] as String
        return DateTimeJulian(jdNr, cal)
    }

    private fun createDateTimeParts(jsonObject: JSONObject): DateTimeParts {
        val partsObject = jsonObject["dateTimeParts"] as JSONObject
        val year = partsObject["year"].toString().toInt()
        val month = partsObject["month"].toString().toInt()
        val day = partsObject["day"].toString().toInt()
        val hour = partsObject["hour"].toString().toInt()
        val minute = partsObject["minute"].toString().toInt()
        val second = partsObject["second"].toString().toInt()
        val offsetUt = partsObject["offsetUt"] as Double
        return DateTimeParts(year, month, day, hour, minute, second, offsetUt)
    }

    private fun createLocation(jsonObject: JSONObject): Location {
        val locationObject = jsonObject["location"] as JSONObject
        val latValue = locationObject["geoLat"] as Double
        val lonValue = locationObject["geoLon"] as Double
        return Location(latValue, lonValue)
    }
}