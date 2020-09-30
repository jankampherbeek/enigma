/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.persistency

import com.radixpro.enigma.domain.stats.DataFileDescription
import com.radixpro.enigma.domain.stats.StatsProject
import com.radixpro.enigma.shared.exceptions.DatabaseException
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

class StatsProjDao(private val jsonWriter: JsonWriter) {

    fun save(project: StatsProject, pathRoot: String) {
        val projectName = project.name
        val fullPath = pathRoot + File.separator + "proj" + File.separator + projectName + File.separator
        if (folderExists(fullPath)) throw DatabaseException("Projectfolder already exists")
        val fullPathAsFile = File(fullPath)
        fullPathAsFile.mkdirs()
        val fullProjFileName = fullPath + "proj_" + projectName + ".json"
        jsonWriter.write2File(fullProjFileName, project, true)

        val dataFilenames = project.dataFiles
        val inputDataFolder = pathRoot + File.separator + "data" + File.separator
        for (datafileDescr: DataFileDescription in dataFilenames) {
            var fullPathDataFile = inputDataFolder + datafileDescr.name + ".json"
            var newPathDataFile = fullPath + File.separator + "in_" + datafileDescr.name + ".json"
            File(fullPathDataFile).copyTo(File(newPathDataFile))
        }
    }

    private fun folderExists(fullPathFolder: String): Boolean {
        return Files.exists(Path.of(fullPathFolder))
    }


}