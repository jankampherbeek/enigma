/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */
package com.radixpro.enigma.shared

/**
 * Makes sure a number is in a specific range.
 * Constructor defines min and max values. The input parameters should be in the correct sequence,
 * otherwise a Runtime Exception will be thrown.
 */
class Range(minValue: Double, maxValue: Double) {
    private val min: Double
    private val max: Double
    private val actualRange: Double

    /**
     * Checks if a value in the defined range. If not, the value will be adapted so that it does fit.
     *
     * @param inputValue The value to check.
     * @return The checked, and possibly adapted, value.
     */
    fun checkValue(inputValue: Double): Double {
        var workValue = inputValue
        while (workValue >= max) workValue -= actualRange
        while (workValue < min) workValue += actualRange
        return workValue
    }

    init {
        require(minValue < maxValue) { "Input for Range has wrong sequence" }
        min = minValue
        max = maxValue
        actualRange = max - min
    }
}