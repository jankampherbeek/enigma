/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.Rosetta
import com.radixpro.enigma.be.persistency.DataReaderCsv
import com.radixpro.enigma.share.persistency.JsonWriter
import com.radixpro.enigma.statistics.api.InputDataFileRequest
import com.radixpro.enigma.statistics.api.InputDataFileResponse
import org.apache.log4j.Logger
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

class InputDataFileHandler(private val dataReaderCsv: DataReaderCsv) {
    fun handleDataFile(request: InputDataFileRequest): InputDataFileResponse {
        var errorLines: List<String> = ArrayList()
        var resultMsg: String
        var success: Boolean
        try {
            val pathFilename = createFullPathJsonFile(request.dataName, request.fullPathProjDir)
            val inputDataSet = dataReaderCsv.readCsv(request.dataName, request.description, request.dataFile.absolutePath)
            errorLines = dataReaderCsv.errorLines
            success = dataReaderCsv.isNoErrors
            JsonWriter().write2File(pathFilename, inputDataSet, true)
            resultMsg = if (success) Rosetta.getText("inputdata.response.resultmsg") + " " + pathFilename else Rosetta.getText("inputdata.response.errormsg")
        } catch (ide: InputDataException) {
            success = false
            resultMsg = Rosetta.getText("inputdata.response.errormsg")
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
        private val LOG = Logger.getLogger(InputDataFileHandler::class.java)
    }

}