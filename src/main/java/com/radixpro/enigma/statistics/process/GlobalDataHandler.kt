/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.share.persistency.JsonWriter
import com.radixpro.enigma.statistics.api.InputDataFileRequest
import com.radixpro.enigma.statistics.api.InputDataFileResponse
import com.radixpro.enigma.statistics.core.DataFileDescription
import com.radixpro.enigma.statistics.core.InputDataSet
import com.radixpro.enigma.statistics.persistency.GlobalDataDao
import com.radixpro.enigma.statistics.persistency.InputDataReader
import org.apache.log4j.Logger
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

/**
 * Handler to access global datafiles using the internal Json format.
 */
class GlobalDataHandler(private val daoGlobal: GlobalDataDao,
                        private val inputDataReader: InputDataReader,
                        private val projHandler: StatsProjHandler,
                        private val jsonWriter: JsonWriter,
                        private val pathConstructor: StatsPathConstructor) {
    private val projDirKey = "projdir"
    private val dataFolder = "data"


    fun addDataFile(request: InputDataFileRequest): InputDataFileResponse {
        var errorLines: List<String> = ArrayList()
        var resultMsg: String
        var success: Boolean
        var pathFilename = ""
        try {
//            val project = projHandler.read(request.projectsFolder) as StatsProject
//            pathFilename = pathConstructor.pathForProjectData(project)
            pathFilename = pathConstructor.pathForGlobalData() + request.dataName + ".json"
            val inputDataSet = inputDataReader.readCsv(request.dataName, request.description, request.dataFile.absolutePath)
            errorLines = inputDataReader.errorLines
            success = inputDataReader.isNoErrors
            JsonWriter().write2File(pathFilename, inputDataSet, true)
            resultMsg = if (success) "Saved: $pathFilename" else "Error saving: $pathFilename"
        } catch (ide: InputDataException) {
            success = false
            resultMsg = "Error saving: $pathFilename : ${ide.message}"
            LOG.error("Error while writing Json file converted from csv. Result message: " + resultMsg + ". Exception: " + ide.message)
        }
        return InputDataFileResponse(resultMsg, errorLines, success)
    }


    @Throws(InputDataException::class)
    private fun createFullPathJsonFile(fileName: String,
                                       fullPathProjDir: String): String {
        var path = fullPathProjDir
        if (!path.endsWith(File.separator)) path += File.separator
        path += "data" + File.separator
        if (!checkOrCreateFolder(path)) throw InputDataException("Could not create folder :$path")
        val pos = fileName.lastIndexOf(File.separator)
        val fileNameNoPath = fileName.substring(pos + 1) + ".json"
        return path + fileNameNoPath
    }

    private fun checkOrCreateFolder(fullPathFolder: String): Boolean {
        return if (Files.notExists(Path.of(fullPathFolder))) {
            val newFolder = File(fullPathFolder)
            newFolder.mkdir()
        } else true
    }

    companion object {
        private val LOG = Logger.getLogger(GlobalDataHandler::class.java)
    }


    fun readDataFileDesciptions(): List<DataFileDescription> {
        return daoGlobal.readDataFileList()
    }

    fun readDataCharts(filename: String): InputDataSet {
        return daoGlobal.readData(filename)
    }

    // TODO create method readDataEvents()

}