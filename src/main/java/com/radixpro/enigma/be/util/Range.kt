/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */
package com.radixpro.enigma.be.util

/**
 * Makes sure a number is in a specific range.
 */
object Range {

    /**
     * Makes sure inputValue is in the range minValue .. maxValue (dfault 0.0 .. 360.0). If minValue > maxValue, the values are interchanged.
     */
    fun checkValue(inputValue: Double, minValue: Double = 0.0, maxValue: Double = 360.0): Double {
        val min: Double
        val max: Double
        if (minValue < maxValue) {
            min = minValue
            max = maxValue
        } else {
            min = maxValue
            max = minValue
        }
        val actualRange = max - min
        var workValue = inputValue
        while (workValue >= max) workValue -= actualRange
        while (workValue < min) workValue += actualRange
        return workValue
    }

}