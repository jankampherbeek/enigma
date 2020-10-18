/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.be.persistency.mappers.InputDataSetMapper
import com.radixpro.enigma.domain.input.ChartInputData
import com.radixpro.enigma.domain.input.DateTimeParts
import com.radixpro.enigma.domain.input.Location
import com.radixpro.enigma.references.TimeZones
import com.radixpro.enigma.share.persistency.JsonReader
import com.radixpro.enigma.share.persistency.JsonWriter
import com.radixpro.enigma.share.process.PropertyHandler
import com.radixpro.enigma.statistics.core.InputDataSet
import com.radixpro.enigma.statistics.core.StatsProject
import com.radixpro.enigma.ui.helpers.DateTimeCreator
import java.io.File
import java.time.LocalDateTime
import java.util.*

class ControlDataCharts(private val jsonReader: JsonReader,
                        private val jsonWriter: JsonWriter,
                        private val propHandler: PropertyHandler,
                        private val inputDataSetMapper: InputDataSetMapper,
                        private val controlDataCalendar: ControlDataCalendar) {

    private var controlInputData: MutableList<ChartInputData> = ArrayList()
    private var months: MutableList<Int> = ArrayList()
    private var years: MutableList<Int> = ArrayList()
    private var days: MutableList<Int> = ArrayList()
    private var utHours: MutableList<Int> = ArrayList()
    private var utMinutes: MutableList<Int> = ArrayList()
    private var utSeconds: MutableList<Int> = ArrayList()
    private var latitudes: MutableList<Double> = ArrayList()
    private var longitudes: MutableList<Double> = ArrayList()

    fun createFile(project: StatsProject) {
        val (origFilename) = project.dataFile
        val pathRoot = propHandler.retrieve("projdir")[0].value
        val fullPath = pathRoot + File.separator + "proj" + File.separator + project.name + File.separator
        val fullOrigFilename = fullPath + "in_" + origFilename + ".json"
        val fullCtrlFilename = fullPath + "ctrl_" + origFilename + ".json"
        handleControlData(fullOrigFilename)
        persistData(fullCtrlFilename, fullOrigFilename, project)
    }

    private fun handleControlData(origFilename: String) {
        val origFile = File(origFilename)
        val inputDataSetJson = jsonReader.readObjectFromFile(origFile)
        val inputDataSet = inputDataSetMapper.jsonToInputDataSet(inputDataSetJson)
        processInputData(inputDataSet)
        sortDaysAndShuffleOtherItems()
        processData()
    }

    private fun processData() {   // use only UT
        var counter = 0
        while (years.size > 0) {
            val year = getIntFromList(years)
            val day = getIntFromList(days)
            val month = findMonth(day, year)
            val utHour = getIntFromList(utHours)
            val utMinute = getIntFromList(utMinutes)
            val utSecond = getIntFromList(utSeconds)
            val latitude = getDoubleFromList(latitudes)
            val longitude = getDoubleFromList(longitudes)
            val location = Location(longitude, latitude)
            val dateTimeParts = DateTimeParts(year, month, day, utHour, utMinute, utSecond, 0.0)
            val dateTimeJulian = DateTimeCreator.createDateTimeJulian(year, month, day, utHour, utMinute, utSecond, TimeZones.UT, false, 0.0, "G")
            val id = counter++
            val chartInputData = ChartInputData(id, "Controldata $id", dateTimeParts, dateTimeJulian, location)
            controlInputData.add(chartInputData)
        }
    }

    private fun processInputData(inputDataSet: InputDataSet) {
        for (chartInputData in inputDataSet.inputData) {
            val dateTimeParts = chartInputData.dateTimeParts
            val location = chartInputData.location
            utHours.add(dateTimeParts.hour)
            utMinutes.add(dateTimeParts.minute)
            utSeconds.add(dateTimeParts.second)
            years.add(dateTimeParts.year)
            months.add(dateTimeParts.month)
            days.add(dateTimeParts.day)
            latitudes.add(location.geoLat)
            longitudes.add(location.geoLon)
        }
    }

    private fun sortDaysAndShuffleOtherItems() {
        days.sort()
        days.reverse()
        ListRandomizer.randomize(years)
        ListRandomizer.randomize(months)
        ListRandomizer.randomize(utHours)
        ListRandomizer.randomize(utMinutes)
        ListRandomizer.randomize(utSeconds)
        ListRandomizer.randomize(latitudes)
        ListRandomizer.randomize(longitudes)
    }

    private fun getIntFromList(theList: MutableList<Int>): Int {
        val result = theList[0]
        theList.removeAt(0)
        return result
    }

    private fun getDoubleFromList(theList: MutableList<Double>): Double {
        val result = theList[0]
        theList.removeAt(0)
        return result
    }

    private fun findMonth(day: Int, year: Int): Int {
        var found = false
        var month = 0
        var counter = 0
        while (!found && counter < months.size) {
            month = months[counter]
            if (controlDataCalendar.dayFitsInMonth(day, month, year)) {
                found = true
                months.removeAt(counter)
            }
            counter++
        }
        return month
    }

    private fun persistData(pathFilename: String, origFilename: String, project: StatsProject) {
        val dateTimeStamp = LocalDateTime.now()
        val inputDataSet = InputDataSet("Control data", "Project: " + project.name, origFilename, dateTimeStamp.toString(), controlInputData)
        jsonWriter.write2File(pathFilename, inputDataSet, true)
    }
}