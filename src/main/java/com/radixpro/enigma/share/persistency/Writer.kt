/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.share.persistency

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.radixpro.enigma.share.exceptions.SaveException
import java.io.File
import java.io.IOException

/**
 * Writes an object to file using Json format.
 * Based on an example at: http://www.studytrails.com/java/json/jackson-create-json.jsp
 */
class JsonWriter {
    fun write2File(pathFilename: String,
                   object2Write: Any,
                   useIndent: Boolean) {
        val mapper = ObjectMapper()
        mapper.configure(SerializationFeature.INDENT_OUTPUT, useIndent)
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
        try {
            val jsonFile = File(pathFilename)
            mapper.writeValue(jsonFile, object2Write)
        } catch (e: IOException) {
            throw SaveException("Could not write to file, using path and filename :$pathFilename . Reason: ${e.message}")
        }
    }
}

class CsvWriter {
    fun write2File(pathFilename: String, lines: List<String>) {
        val file = File(pathFilename)
        file.writeText("")     // overwrites existing content
        for (line: String in lines) {
            file.appendText(line)
            file.appendText("\n")
        }
    }


}