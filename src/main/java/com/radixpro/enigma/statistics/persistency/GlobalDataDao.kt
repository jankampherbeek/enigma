/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.statistics.persistency

import com.radixpro.enigma.share.persistency.Reader
import com.radixpro.enigma.statistics.core.DataFileDescription
import com.radixpro.enigma.statistics.core.InputDataSet
import com.radixpro.enigma.statistics.process.StatsPathConstructor
import java.io.File
import java.util.*

/**
 * Dao for global datafiles.
 */
class GlobalDataDao(private val jsonReader: Reader,
                    private val mapper: InputDataSetMapper,
                    private val pathConstructor: StatsPathConstructor) {

    fun readDataFileList(): List<DataFileDescription> {
        val descriptions: MutableList<DataFileDescription> = ArrayList()
        val fileNames = getFilenames(pathConstructor.pathForGlobalData())
        var inputDataSet: InputDataSet
        for (fileName in fileNames) {
            inputDataSet = readData(fileName)
            descriptions.add(DataFileDescription(inputDataSet.name, inputDataSet.description, inputDataSet.inputData.size))
        }
        return descriptions
    }

    private fun getFilenames(projDataFolderPath: String): List<String> {
        val projDataFolder = File(projDataFolderPath)
        val filenames: MutableList<String> = ArrayList()
        for (fileEntry in projDataFolder.listFiles()) {
            if (!fileEntry.isDirectory) filenames += fileEntry.name
        }
        return filenames
    }

    fun readData(filename: String): InputDataSet {
        val path = pathConstructor.pathForGlobalData() + filename
        val dataFile = File(path)
        val inputDataJson = jsonReader.readObjectFromFile(dataFile)
        return mapper.jsonToInputDataSet(inputDataJson)
    }
}