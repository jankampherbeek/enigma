/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.statistics.process

import com.radixpro.enigma.be.persistency.DataFileDao
import com.radixpro.enigma.share.process.PropertyHandler
import com.radixpro.enigma.statistics.core.DataFileDescription
import java.io.File

/**
 * Handler to access datafiles using the internal Json format.
 */
class DataFileHandler(private val dao: DataFileDao,
                      propHandler: PropertyHandler) {
    private val projDirKey = "projdir"
    private val dataFolder = "data"
    private val projDir: String?

    init {
        projDir = propHandler.retrieve(projDirKey)[0].value
    }

    fun readDataFileDesciptions(): List<DataFileDescription> {
        val projDirFile = File(projDir + File.separator + dataFolder + File.separator)
        return dao.readDataFileList(projDirFile)
    }

}