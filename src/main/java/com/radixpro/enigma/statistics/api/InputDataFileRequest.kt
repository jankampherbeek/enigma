/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.api

import java.io.File

/**
 * Request for reading a datafile and saving it in Json format in the project folder.
 */
data class InputDataFileRequest(val dataName: String,
                                val description: String,
                                val dataFile: File,
                                val projName: String)