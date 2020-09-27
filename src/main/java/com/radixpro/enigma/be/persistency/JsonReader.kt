/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.persistency

import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import java.io.File
import java.io.FileReader
import java.io.IOException

/**
 * Reader for Json files.
 */
class JsonReader {
    fun readObjectFromFile(inputData: File): JSONObject {
        val parser = JSONParser()
        return try {
            val `object` = parser.parse(FileReader(inputData))
            `object` as JSONObject
        } catch (pe: ParseException) {
            throw RuntimeException("Could not parse results of : " + inputData + " . Original message " + pe.message)
        } catch (ioe: IOException) {
            throw RuntimeException("Could not read file : " + inputData + " . Original message " + ioe.message)
        }
    }

    fun readArrayFromFile(inputData: File): JSONArray {
        val parser = JSONParser()
        return try {
            val `object` = parser.parse(FileReader(inputData))
            `object` as JSONArray
        } catch (pe: ParseException) {
            throw RuntimeException("Could not parse results of : " + inputData + " . Original message " + pe.message)
        } catch (ioe: IOException) {
            throw RuntimeException("Could not read file : " + inputData + " . Original message " + ioe.message)
        }
    }
}