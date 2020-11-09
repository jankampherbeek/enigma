/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.persistency

import com.radixpro.enigma.references.ErrorMsgs
import com.radixpro.enigma.share.persistency.JsonWriter
import com.radixpro.enigma.share.persistency.Reader
import com.radixpro.enigma.shared.exceptions.DatabaseException
import com.radixpro.enigma.statistics.core.IStatsProject
import com.radixpro.enigma.statistics.core.StatsFailedProject
import com.radixpro.enigma.statistics.core.StatsProject
import org.apache.log4j.Logger
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

interface StatsProjDao {
    fun save(project: StatsProject, pathRoot: String)
    fun read(projectName: String, pathRoot: String): IStatsProject
    fun readAllNames(pathRoot: String): MutableList<String>
}

class StatsProjDaoJson(private val jsonReader: Reader, private val mapper: StatsProjMapper) : StatsProjDao {

    private val log = Logger.getLogger(StatsProjDaoJson::class.java)

    override fun save(project: StatsProject, pathRoot: String) {
        val projectName = project.name
        val fullPath = pathRoot + File.separator + "proj" + File.separator + projectName + File.separator
        if (folderExists(fullPath)) throw DatabaseException("Projectfolder already exists")
        val fullPathAsFile = File(fullPath)
        fullPathAsFile.mkdirs()
        val fullProjFileName = fullPath + "proj_" + projectName + ".json"
        JsonWriter().write2File(fullProjFileName, project, true)

        val dataFileName = project.dataFileName       // TODO handle set that contains event file
        val inputDataFolder = pathRoot + File.separator + "data" + File.separator
        val fullPathDataFile = "$inputDataFolder$dataFileName.json"
        val newPathDataFile = fullPath + File.separator + "in_" + dataFileName + ".json"
        File(fullPathDataFile).copyTo(File(newPathDataFile))
    }

    override fun read(projectName: String, pathRoot: String): IStatsProject {
        val fullPath = pathRoot + File.separator + "proj" + File.separator + projectName
        if (folderExists(fullPath)) {
            val fullProjFileName = fullPath + File.separator + "proj_" + projectName + ".json"
            val jsonObject = jsonReader.readObjectFromFile(File(fullProjFileName))
            return mapper.jsonToStatsProject(jsonObject)
        }
        log.error("Could not find project $projectName in path $pathRoot")
        return StatsFailedProject(ErrorMsgs.PROJECT_NOT_SAVED)
    }

    override fun readAllNames(pathRoot: String): MutableList<String> {
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