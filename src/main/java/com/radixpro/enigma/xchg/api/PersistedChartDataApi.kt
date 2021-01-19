/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */
package com.radixpro.enigma.xchg.api

import com.radixpro.enigma.be.persistency.ChartDataDao
import com.radixpro.enigma.shared.FailFastHandler
import com.radixpro.enigma.shared.exceptions.DatabaseException
import com.radixpro.enigma.xchg.domain.FullChartInputData
import java.util.*

class PersistedChartDataApi(private val dao: ChartDataDao) {
    fun insert(fullChartInputData: FullChartInputData): Int {
        var chartId = -1
        try {
            chartId = dao.insert(fullChartInputData)
        } catch (de: DatabaseException) {    // TODO handle exception
            println(de.message)
            //     new FailFastHandler().terminate(de.getMessage());
        }
        return chartId
    }

    fun delete(chartId: Int) {
        try {
            dao.delete(chartId)
        } catch (e: Exception) {
            FailFastHandler().terminate(e.message)
        }
    }

    fun read(id: Int): List<FullChartInputData> {
        return dao.read(id)
    }

    fun readAll(): List<FullChartInputData?> {
        var fullChartInputDataResult: List<FullChartInputData?> = ArrayList()
        try {
            fullChartInputDataResult = dao.readAll()
        } catch (e: Exception) {
            println(e.message)
        }
        return fullChartInputDataResult
    }

    fun search(searchName: String): List<FullChartInputData> {
        return dao.search(searchName)
    }

    fun searchExact(searchName: String): List<FullChartInputData> {
        return dao.searchExact(searchName)
    }
}