/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.shared.converters

import com.radixpro.enigma.domain.input.Location
import com.radixpro.enigma.shared.exceptions.InputDataException


class Csv2LocationConverter {

    @Throws(InputDataException::class)
    fun convert(lonTxt: String, latTxt: String): Location {
        return Location(createLatitude(latTxt), createLongitude(lonTxt))
    }

    @Throws(InputDataException::class)
    private fun createLongitude(lonTxt: String): Double {
        val dir: String
        var parts = lonTxt.toUpperCase().split("E").toTypedArray()
        if (2 == parts.size) dir = "E" else {
            parts = lonTxt.toUpperCase().split("W").toTypedArray()
            dir = if (2 == parts.size) {
                "W"
            } else throw InputDataException("Error when defining longitude for : $lonTxt")
        }
        return createCoordinate(parts, dir)
    }

    @Throws(InputDataException::class)
    private fun createLatitude(latTxt: String): Double {
        val dir: String
        var parts = latTxt.toUpperCase().split("N").toTypedArray()
        if (2 == parts.size) dir = "N" else {
            parts = latTxt.toUpperCase().split("S").toTypedArray()
            dir = if (2 == parts.size) {
                "S"
            } else throw InputDataException("Error when defining latitude for : $latTxt")
        }
        return createCoordinate(parts, dir)
    }

    @Throws(InputDataException::class)
    private fun createCoordinate(parts: Array<String>, direction: String): Double {
        val degrees: Int
        val minutes: Int
        val dirCorrection = if ("NOEnoe".contains(direction)) 1 else -1
        try {
            degrees = parts[0].toInt()
            minutes = parts[1].toInt()
        } catch (nfe: NumberFormatException) {
            throw InputDataException("NumberFormatException when parsing GeographicCoordinate for : " + parts.contentToString())
        }
        return (degrees + minutes / 60.0) * dirCorrection
    }
}