/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.persistency

import com.radixpro.enigma.be.persistency.mappers.StatsProjMapper
import com.radixpro.enigma.domain.stats.IStatsProject
import com.radixpro.enigma.domain.stats.StatsFailedProject
import com.radixpro.enigma.domain.stats.StatsProject
import com.radixpro.enigma.references.ErrorMsgs
import com.radixpro.enigma.shared.exceptions.DatabaseException
import org.apache.log4j.Logger
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

class StatsProjDao(private val jsonWriter: JsonWriter, private val jsonReader: JsonReader, private val mapper: StatsProjMapper) {

    private val log = Logger.getLogger(StatsProjDao::class.java)

    fun save(project: StatsProject, pathRoot: String) {
        val projectName = project.name
        val fullPath = pathRoot + File.separator + "proj" + File.separator + projectName + File.separator
        if (folderExists(fullPath)) throw DatabaseException("Projectfolder already exists")
        val fullPathAsFile = File(fullPath)
        fullPathAsFile.mkdirs()
        val fullProjFileName = fullPath + "proj_" + projectName + ".json"
        jsonWriter.write2File(fullProjFileName, project, true)

        val dataFileDescr = project.dataFile       // TODO handle set that contains event file
        val inputDataFolder = pathRoot + File.separator + "data" + File.separator
//        for (datafileDescr: DataFileDescription in dataFilenames) {
        val fullPathDataFile = inputDataFolder + dataFileDescr.name + ".json"
        val newPathDataFile = fullPath + File.separator + "in_" + dataFileDescr.name + ".json"
        File(fullPathDataFile).copyTo(File(newPathDataFile))
//        }
    }

    fun read(projectName: String, pathRoot: String): IStatsProject {
        val fullPath = pathRoot + File.separator + "proj" + File.separator + projectName
        if (folderExists(fullPath)) {
            val fullProjFileName = fullPath + File.separator + "proj_" + projectName + ".json"
            val jsonObject = jsonReader.readObjectFromFile(File(fullProjFileName))
            return mapper.jsonToStatsProject(jsonObject)
        }
        log.error("Could not find project $projectName in path $pathRoot")
        return StatsFailedProject(false, ErrorMsgs.PROJECT_NOT_SAVED)
    }

    fun readAllNames(pathRoot: String): MutableList<String> {
        val fullPath = pathRoot + File.separator + "proj" + File.separator
        val projNames: MutableList<String> = ArrayList()
        File(fullPath).walk().forEach {
            if (it.isDirectory) {
                projNames.add(it.name)
            }
        }
        return projNames
    }

    private fun folderExists(fullPathFolder: String): Boolean {
        return Files.exists(Path.of(fullPathFolder))
    }


}