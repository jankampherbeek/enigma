/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.be.persistency.InternalDataFileDao
import com.radixpro.enigma.share.process.PropertyHandler
import com.radixpro.enigma.statistics.core.DataFileDescription
import com.radixpro.enigma.statistics.core.InputDataSet
import com.radixpro.enigma.statistics.core.StatsProject
import java.io.File

/**
 * Handler to access datafiles using the internal Json format.
 */
class InternalDataFileHandler(private val daoInternal: InternalDataFileDao,
                              private val propHandler: PropertyHandler) {
    private val projDirKey = "projdir"
    private val dataFolder = "data"


    fun readDataFileDesciptions(): List<DataFileDescription> {
        return daoInternal.readDataFileList(constructProjDataFolderTxt())
    }

    fun readData(project: StatsProject): InputDataSet {
        return daoInternal.readData(constructProjDataFolderTxt(), project.dataFileName)
    }

    private fun constructProjDataFolderTxt(): File {
        val props = propHandler.retrieve(projDirKey)
        return File(propHandler.retrieve(props[0].value).toString() + File.separator + dataFolder + File.separator)
    }

}