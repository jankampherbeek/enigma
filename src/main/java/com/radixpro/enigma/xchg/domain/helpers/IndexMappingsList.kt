/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */
package com.radixpro.enigma.xchg.domain.helpers

/**
 * Collection of all IndexMappings for a specific enum.
 */
class IndexMappingsList(val allIndexMappings: List<IndexMapping>) {

    fun getSequenceIdForEnumId(enumId: Int): Int {
        for ((sequenceId, enumId1) in allIndexMappings) {
            if (enumId1 == enumId) return sequenceId
        }
        return -1
    }

    fun getEnumIdForSequenceId(sequenceId: Int): Int {
        for ((sequenceId1, enumId) in allIndexMappings) {
            if (sequenceId1 == sequenceId) return enumId
        }
        return -1
    }
}