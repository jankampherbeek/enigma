/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.calc

/**
 * Utility class for goniometric calculations.
 * All calculations are performed in degrees, no conversion from and to radians is required.
 * All methods are static.
 */
object EnigmaMath {
    @JvmStatic
    fun sin(value: Double): Double {
        return kotlin.math.sin(Math.toRadians(value))
    }

    @JvmStatic
    fun cos(value: Double): Double {
        return kotlin.math.cos(Math.toRadians(value))
    }

    @JvmStatic
    fun tan(value: Double): Double {
        return kotlin.math.tan(Math.toRadians(value))
    }

    @JvmStatic
    fun asin(value: Double): Double {
        return Math.toDegrees(kotlin.math.asin(value))
    }

    @JvmStatic
    fun acos(value: Double): Double {
        return Math.toDegrees(kotlin.math.acos(value))
    }

    @JvmStatic
    fun atan(value: Double): Double {
        return Math.toDegrees(kotlin.math.atan(value))
    }

    @JvmStatic
    fun atan2(value1: Double, value2: Double): Double {
        return Math.toDegrees(kotlin.math.atan2(value1, value2))
    }
}