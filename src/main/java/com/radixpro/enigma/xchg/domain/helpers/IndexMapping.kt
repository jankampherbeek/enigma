/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */
package com.radixpro.enigma.xchg.domain.helpers

/**
 * Combines the sequence id to be used in an Observable list and the id in an Enum.
 */
data class IndexMapping(val sequenceId: Int, val enumId: Int)