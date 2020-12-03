/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.testhelpers

import com.radixpro.enigma.references.CelestialObjects
import com.radixpro.enigma.references.HouseSystems
import com.radixpro.enigma.references.MundanePointsAstron
import com.radixpro.enigma.statistics.core.ScenMinMaxBe
import com.radixpro.enigma.statistics.core.ScenRangeBe
import com.radixpro.enigma.statistics.core.StatsMinMaxTypesBe
import com.radixpro.enigma.statistics.ui.domain.ScenMinMaxFe
import com.radixpro.enigma.statistics.ui.domain.ScenRangeFe
import com.radixpro.enigma.statistics.ui.domain.ScenarioTypes
import com.radixpro.enigma.statistics.ui.domain.StatsRangeTypes

class ScenarioCreators {

    private val scenName = "Name for scenario"
    private val descr = "Descr for scenario"
    private val projName = "Project with scenarios"
    private val scenRangeType = "RANGE"
    private val scenMinMaxType = "MINMAX"
    private val notExistingScenType = "DOES NOT EXIST"
    private val rangeTypeName = "SIGNS"
    private val minMaxTypeName = "ECLIPTIC_DISTANCE"
    private val houseSystemName = "PLACIDUS"
    private val refPointName = "MC"
    private val refPoint = MundanePointsAstron.MC

    fun createScenRangeFe(): ScenRangeFe {
        return ScenRangeFe(scenName, descr, projName, scenRangeType, rangeTypeName, houseSystemName, createCelObjectNames(), createMundanePointNames())
    }

    fun createScenRangeBe(): ScenRangeBe {
        return ScenRangeBe(scenName, descr, projName, ScenarioTypes.RANGE, StatsRangeTypes.SIGNS, HouseSystems.PLACIDUS, createCelObjects(), createMundanePoints())
    }

    fun createScenRangeFeError(): ScenRangeFe {
        return ScenRangeFe(scenName, descr, projName, notExistingScenType, rangeTypeName, houseSystemName, createCelObjectNames(), createMundanePointNames())
    }

    fun createScenMinMaxFe(): ScenMinMaxFe {
        return ScenMinMaxFe(scenName, descr, projName, scenMinMaxType, minMaxTypeName, refPointName, createCelObjectNames(), createMundanePointNames())
    }

    fun createScenMinMaxBe(): ScenMinMaxBe {
        return ScenMinMaxBe(
            scenName,
            descr,
            projName,
            ScenarioTypes.MINMAX,
            StatsMinMaxTypesBe.ECLIPTIC_DISTANCE,
            refPoint,
            createCelObjects(),
            createMundanePoints()
        )
    }

    fun createScenMinMaxFeError(): ScenMinMaxFe {
        return ScenMinMaxFe(scenName, descr, projName, notExistingScenType, minMaxTypeName, refPointName, createCelObjectNames(), createMundanePointNames())
    }


    private fun createCelObjectNames(): List<String> {
        return listOf("SUN", "MOON", "MERCURY")
    }

    private fun createCelObjects(): List<CelestialObjects> {
        return listOf(CelestialObjects.SUN, CelestialObjects.MOON, CelestialObjects.MERCURY)
    }

    private fun createMundanePointNames(): List<String> {
        return listOf("MC", "ASC")
    }

    private fun createMundanePoints(): List<MundanePointsAstron> {
        return listOf(MundanePointsAstron.MC, MundanePointsAstron.ASC)
    }

}