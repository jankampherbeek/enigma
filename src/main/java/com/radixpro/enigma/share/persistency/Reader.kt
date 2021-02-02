/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.share.persistency

import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import java.io.File
import java.io.FileReader
import java.io.IOException

interface Reader {
    fun readObjectFromFile(inputData: File): JSONObject
    fun readArrayFromFile(inputData: File): JSONArray
}

interface FileSystemReader {
    fun readFileItems(path: String, prefix: String = "", postFix: String = ""): List<String>
}


/**
 * Reader for Json files.
 */
class JsonReader : Reader {
    override fun readObjectFromFile(inputData: File): JSONObject {
        val parser = JSONParser()
        lateinit var fileReader: FileReader
        return try {
            fileReader = FileReader(inputData)
            val jsonObject = parser.parse(fileReader)
            jsonObject as JSONObject
        } catch (pe: ParseException) {
            throw RuntimeException("Could not parse results of : " + inputData + " . Original message " + pe.message)
        } catch (ioe: IOException) {
            throw RuntimeException("Could not read file : " + inputData + " . Original message " + ioe.message)
        } finally {
            fileReader.close()
        }
    }

    override fun readArrayFromFile(inputData: File): JSONArray {
        val parser = JSONParser()
        lateinit var fileReader: FileReader
        return try {
            fileReader = FileReader(inputData)
            val jsonObject = parser.parse(fileReader)
            jsonObject as JSONArray
        } catch (pe: ParseException) {
            throw RuntimeException("Could not parse results of : " + inputData + " . Original message " + pe.message)
        } catch (ioe: IOException) {
            throw RuntimeException("Could not read file : " + inputData + " . Original message " + ioe.message)
        } finally {
            fileReader.close()
        }
    }
}


class FileReader : FileSystemReader {
    override fun readFileItems(pathToFolder: String, preFix: String, postFix: String): List<String> {
        val items: MutableList<String> = ArrayList()
        File(pathToFolder).walk().forEach {
            if (it.isFile && it.name.startsWith(preFix) && it.name.endsWith(postFix)) items.add(it.name)
        }
        return items.toList()
    }

}