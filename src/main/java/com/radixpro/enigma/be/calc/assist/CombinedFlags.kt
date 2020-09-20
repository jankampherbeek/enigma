/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */
package com.radixpro.enigma.be.calc.assist

import com.radixpro.enigma.references.SeFlags

class CombinedFlags {
    fun getCombinedValue(flagList: List<SeFlags>): Long {
        return performCombination(flagList)
    }

    private fun performCombination(flagList: List<SeFlags>): Long {
        var result: Long = 0
        for (flag in flagList) {
            result = result or flag.seValue
        }
        return result
    }
}