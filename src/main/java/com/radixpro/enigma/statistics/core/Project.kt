/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.core

import com.radixpro.enigma.domain.config.BaseAstronConfig
import com.radixpro.enigma.references.ErrorMsgs

/**
 * Project for statistical research.
 */
interface IStatsProject {
    val success: Boolean
}

data class StatsProject(override val success: Boolean,
                        val name: String,
                        val description: String,
                        val baseAstronConfig: BaseAstronConfig,
                        val dataFile: DataFileDescription) : IStatsProject

class StatsFailedProject(override val success: Boolean, val errorMsg: ErrorMsgs) : IStatsProject
