/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.core

import com.radixpro.enigma.shared.exceptions.UnknownIdException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith


internal class StatsRangeTypesTest {

    @Test
    @Throws(UnknownIdException::class)
    fun getRangeTypeForId() {
        assertEquals(StatsRangeTypes.HOUSES, StatsRangeTypes.getRangeTypeForId(2))
    }

    @Test
    @Throws(UnknownIdException::class)
    fun getRangeTypeForIdError() {
        assertFailsWith<UnknownIdException> {
            StatsRangeTypes.getRangeTypeForId(100)
        }
    }
}