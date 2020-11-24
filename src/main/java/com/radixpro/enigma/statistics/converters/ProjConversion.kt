/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.converters

import com.radixpro.enigma.domain.config.BaseAstronConfig
import com.radixpro.enigma.references.Ayanamshas
import com.radixpro.enigma.references.EclipticProjections
import com.radixpro.enigma.references.HouseSystems
import com.radixpro.enigma.references.ObserverPositions
import com.radixpro.enigma.share.ui.domain.AstronConfigFe
import com.radixpro.enigma.statistics.api.xchg.ProjectApiResult
import com.radixpro.enigma.statistics.api.xchg.ProjectApiResultFe
import com.radixpro.enigma.statistics.core.StatsProject
import com.radixpro.enigma.statistics.ui.domain.StatsProjectFe

/**
 * Marker interface for converters for requests and responses.
 */
interface Converter

class ProjectConverter : Converter {

    fun feRequestToBe(feProject: StatsProjectFe): StatsProject {
        return StatsProject(
                feProject.name,
                feProject.description,
                BaseAstronConfig(HouseSystems.valueOf(feProject.config.houseSystem),
                        Ayanamshas.valueOf(feProject.config.ayanamsha),
                        EclipticProjections.valueOf(feProject.config.eclipticProj),
                        ObserverPositions.valueOf(feProject.config.observerPos)),
                feProject.dataFileName)
    }

    fun beRequestToFe(beProject: StatsProject): StatsProjectFe {
        return StatsProjectFe(
                beProject.name,
                beProject.description,
                beProject.dataFileName,
                AstronConfigFe(beProject.baseAstronConfig.houseSystem.name,
                        beProject.baseAstronConfig.ayanamsha.name,
                        beProject.baseAstronConfig.eclipticProjection.name,
                        beProject.baseAstronConfig.observerPosition.name))
    }
}

class ProjectResultConverter(private val projConverter: ProjectConverter) : Converter {

    fun beResponseToFe(beResult: ProjectApiResult): ProjectApiResultFe {
        return ProjectApiResultFe(beResult.apiResult, projConverter.beRequestToFe(beResult.project))
    }

}
