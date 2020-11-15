/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.persistency

import com.radixpro.enigma.share.persistency.Reader
import com.radixpro.enigma.statistics.core.InputDataSet
import com.radixpro.enigma.statistics.core.StatsProject
import com.radixpro.enigma.statistics.process.StatsPathConstructor
import org.json.simple.JSONObject
import java.io.File

class ProjectDataDao(private val jsonReader: Reader,
                     private val mapper: InputDataSetMapper,
                     private val pathConstructor: StatsPathConstructor) {

    fun readChartDataData(project: StatsProject): InputDataSet {
        val dataFile = File(pathConstructor.pathForProjectData(project))
        val inputDataJson: JSONObject = jsonReader.readObjectFromFile(dataFile)
        return mapper.jsonToInputDataSet(inputDataJson)
    }

    // TODO create method readEventData()


}