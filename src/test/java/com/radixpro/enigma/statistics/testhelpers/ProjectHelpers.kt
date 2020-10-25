/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.testhelpers

import com.radixpro.enigma.domain.config.BaseAstronConfig
import com.radixpro.enigma.references.Ayanamshas
import com.radixpro.enigma.references.EclipticProjections
import com.radixpro.enigma.references.HouseSystems
import com.radixpro.enigma.references.ObserverPositions
import com.radixpro.enigma.share.ui.domain.AstronConfigFe
import com.radixpro.enigma.statistics.api.xchg.ApiResult
import com.radixpro.enigma.statistics.api.xchg.ProjectApiResult
import com.radixpro.enigma.statistics.api.xchg.ProjectApiResultFe
import com.radixpro.enigma.statistics.core.StatsProject
import com.radixpro.enigma.statistics.ui.domain.StatsProjectFe

class ProjectCreator {

    fun createProjectApiResultBe(name: String, descr: String, dataFileName: String): ProjectApiResult {
        return ProjectApiResult(ApiResult(true, "OK"), createStatsProjectBe(name, descr, dataFileName))
    }

    fun createProjectApiResultFe(name: String, descr: String, dataFileName: String): ProjectApiResultFe {
        return ProjectApiResultFe(ApiResult(true, "OK"), createStatsProjectFe(name, descr, dataFileName))
    }

    fun createStatsProjectFe(name: String, descr: String, dataFileName: String): StatsProjectFe {
        return StatsProjectFe(name, descr, dataFileName, createConfigFe())
    }

    fun createStatsProjectBe(name: String, descr: String, dataFileName: String): StatsProject {
        return StatsProject(name, descr, createConfigBe(), dataFileName)
    }

    private fun createConfigFe(): AstronConfigFe {
        return AstronConfigFe("PLACIDUS", "LAHIRI", "SIDEREAL", "GEOCENTRIC")
    }

    private fun createConfigBe(): BaseAstronConfig {
        return BaseAstronConfig(HouseSystems.PLACIDUS, Ayanamshas.LAHIRI, EclipticProjections.SIDEREAL, ObserverPositions.GEOCENTRIC)
    }

}