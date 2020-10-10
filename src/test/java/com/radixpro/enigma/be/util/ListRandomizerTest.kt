/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.util

import com.radixpro.enigma.statistics.process.ListRandomizer
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class ListRandomizerTest {

    @Test
    fun randomize() {
        val sortedList = listOf("One", "Two", "Three", "Four")
        val randomList = ListRandomizer.randomize(sortedList)
        assertTrue(randomList.containsAll(sortedList))
        assertTrue(4 == randomList.size)
    }

}