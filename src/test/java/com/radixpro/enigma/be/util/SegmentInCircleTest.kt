/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SegmentInCircleTest {

    @Test
    fun indexforSegmentSign() {
        val segmentSize = 30
        val position = 33.3
        assertEquals(2, SegmentInCircle.indexforSegment(position, segmentSize))
    }

    @Test
    fun indexForSegmentDecanate() {
        val segmentSize = 10
        val position = 185.34
        assertEquals(19, SegmentInCircle.indexforSegment(position, segmentSize))
    }

    @Test
    fun indexforSegmentPositionOutOfRange() {
        val segmentSize = 30
        val position = 393.3
        assertEquals(2, SegmentInCircle.indexforSegment(position, segmentSize))
    }

}