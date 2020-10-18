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
import java.io.File
import java.io.IOException

interface Writer {
    fun write2File(pathFilename: String, object2Write: Any, useIndent: Boolean)
}

/**
 * Writes an object to file using Json format.
 * Based on an example at: http://www.studytrails.com/java/json/jackson-create-json.jsp
 */
class JsonWriter : Writer {
    override fun write2File(pathFilename: String,
                            object2Write: Any,
                            useIndent: Boolean) {
        val mapper = ObjectMapper()
        mapper.configure(SerializationFeature.INDENT_OUTPUT, useIndent)
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
        try {
            val jsonFile = File(pathFilename)
            mapper.writeValue(jsonFile, object2Write)
        } catch (e: IOException) {
            throw RuntimeException("Could not write to file, using path and filename :$pathFilename . Original message: ${e.message}")
        }
    }
}