/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.util

import java.time.LocalDateTime
import java.util.*

/**
 * Pseudo-randomizes the sequence of items in a collection.
 * Uses the nanoseconds of the current time as seed for the randomizer.
 */
object ListRandomizer {
    /**
     * Perform the randomization.
     * @param shuffleThis The list to randomize.
     * @return The randomized list.
     */
    fun randomize(shuffleThis: List<*>): List<*> {
        return shuffle(shuffleThis)
    }

    private fun shuffle(shuffleThis: List<*>): List<*> {
        val seed = generateSeedFromTime()
        val random = createRandomizer(seed)
        Collections.shuffle(shuffleThis, random)
        return shuffleThis
    }

    private fun generateSeedFromTime(): Long {
        return LocalDateTime.now().nano.toLong()
    }

    private fun createRandomizer(seed: Long): Random {
        val random = Random()
        random.setSeed(seed)
        return random
    }
}