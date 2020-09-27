/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.util

/**
 * Define the index of a segment in a circle. A segment is a part like a sign (segmentSize = 30) or a decanate (segmentSize = 10).
 * The index starts with 1. The range is 0.0 .. < 360.0 and is adapted if necessary.
 */
object SegmentInCircle {

    fun indexforSegment(position: Double, segmentSize: Int): Int {
        require(segmentSize > 0.0)
        return (Range.checkValue(position) / segmentSize).toInt() + 1
    }


}