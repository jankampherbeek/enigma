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
}

data class StatsProject( //override val success: Boolean,
        val name: String,
        val description: String,
        val baseAstronConfig: BaseAstronConfig,
        val dataFileName: String) : IStatsProject

//val dataFile: DataFileDescription) : IStatsProject
class StatsFailedProject(val errorMsg: ErrorMsgs) : IStatsProject