/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.util

import com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class RangeTest {

    @Test
    fun checkValueNoChance() {
        assertEquals(213.0, Range.checkValue(213.0), DELTA_8_POS)
    }

    @Test
    fun checkValueTooSmall() {
        assertEquals(350.0, Range.checkValue(-10.0), DELTA_8_POS)
    }

    @Test
    fun checkValueTooLarge() {
        assertEquals(50.0, Range.checkValue(410.0), DELTA_8_POS)
    }

    @Test
    fun checkValueWithExplicitRange() {
        assertEquals(33.3, Range.checkValue(33.3, 0.0, 100.0), DELTA_8_POS)
    }

    @Test
    fun checkValueWithWrongSequence() {
        assertEquals(33.3, Range.checkValue(33.3, 1000.0, 0.0), DELTA_8_POS)
    }

}