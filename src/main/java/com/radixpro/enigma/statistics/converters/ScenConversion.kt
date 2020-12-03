/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.converters

import com.radixpro.enigma.references.CelestialObjects
import com.radixpro.enigma.references.HouseSystems
import com.radixpro.enigma.references.MundanePointsAstron
import com.radixpro.enigma.share.exceptions.ItemNotFoundException
import com.radixpro.enigma.statistics.core.ScenMinMaxBe
import com.radixpro.enigma.statistics.core.ScenRangeBe
import com.radixpro.enigma.statistics.core.ScenarioBe
import com.radixpro.enigma.statistics.core.StatsMinMaxTypesBe
import com.radixpro.enigma.statistics.di.StatsInjector.injectScenMinMaxConverter
import com.radixpro.enigma.statistics.di.StatsInjector.injectScenRangeConverter
import com.radixpro.enigma.statistics.ui.domain.*
import com.radixpro.enigma.xchg.domain.IChartPoints

class ScenConverterFactory() {

    fun getConverter(type: ScenarioTypes): ScenarioConverter {
        return when (type) {
            ScenarioTypes.RANGE -> injectScenRangeConverter()
            ScenarioTypes.MINMAX -> injectScenMinMaxConverter()
            else -> {
                throw ItemNotFoundException(type.name + " not found in enum ScenarioTypes.")
            }
        }
    }
}


interface ScenarioConverter {

    fun feRequestToBe(scenFe: ScenarioFe): ScenarioBe
    fun beRequestToFe(scenBe: ScenarioBe): ScenarioFe

    fun createCelestialObjects(celObjectNames: List<String>): List<CelestialObjects> {
        val objects: MutableList<CelestialObjects> = ArrayList()
        for (name: String in celObjectNames) {
            objects.add(CelestialObjects.valueOf(name))
        }
        return objects.toList()
    }

    fun createCelObjectNames(celestialObjects: List<CelestialObjects>): List<String> {
        val names: MutableList<String> = ArrayList()
        for (celestialObject in celestialObjects) {
            names.add(celestialObject.name)
        }
        return names.toList()
    }

    fun createMundanePoints(mundanePointNames: List<String>): List<MundanePointsAstron> {
        val mPoints: MutableList<MundanePointsAstron> = ArrayList()
        for (name: String in mundanePointNames) {
            mPoints.add(MundanePointsAstron.valueOf(name))
        }
        return mPoints.toList()
    }

    fun createMundanePointNames(mundPoints: List<MundanePointsAstron>): List<String> {
        val names: MutableList<String> = ArrayList()
        for (point: MundanePointsAstron in mundPoints) {
            names.add(point.name)
        }
        return names.toList()
    }

}


class ScenRangeConverter : ScenarioConverter {

    override fun feRequestToBe(scenFe: ScenarioFe): ScenarioBe {
        val scenario = scenFe as ScenRangeFe
        return ScenRangeBe(
                scenario.name,
                scenario.descr,
                scenario.projName,
                ScenarioTypes.valueOf(scenario.typeName),
                StatsRangeTypes.valueOf(scenario.rangeTypeName),
                HouseSystems.valueOf(scenario.houseSystemName),
                createCelestialObjects(scenario.celObjectNames),
                createMundanePoints(scenario.mundanePointNames)
        )
    }

    override fun beRequestToFe(scenBe: ScenarioBe): ScenarioFe {
        val scenario = scenBe as ScenRangeBe
        return ScenRangeFe(
                scenario.name,
                scenario.description,
                scenario.projectName,
                scenario.scenarioType.name,
                scenario.rangeType.name,
                scenario.houseSystem.name,
                createCelObjectNames(scenario.celObjects),
                createMundanePointNames(scenario.mundanePoints)
        )
    }

}

class ScenMinMaxConverter : ScenarioConverter {
    override fun feRequestToBe(scenFe: ScenarioFe): ScenarioBe {
        val scenario = scenFe as ScenMinMaxFe
        var refPoint: IChartPoints
        val refPointTxt = scenario.referencepoint
        refPoint = when {
            CelestialObjects.values().joinToString().contains(refPointTxt) -> CelestialObjects.valueOf(refPointTxt)
            MundanePointsAstron.values().joinToString().contains(refPointTxt) -> MundanePointsAstron.valueOf(refPointTxt)
            else -> throw ItemNotFoundException("Could not find $refPointTxt in CelestialObjects or in MundanePointsAstron in ScenMinMaxConverter.")
        }
        return ScenMinMaxBe(
            scenario.name,
            scenario.descr,
            scenario.projName,
            ScenarioTypes.valueOf(scenario.typeName),
            StatsMinMaxTypesBe.valueOf(scenario.minMaxTypeName),
            refPoint,
            createCelestialObjects(scenario.celObjectNames),
            createMundanePoints(scenario.mundanePointNames)
        )
    }

    override fun beRequestToFe(scenBe: ScenarioBe): ScenarioFe {
        val scenario = scenBe as ScenMinMaxBe
        var refPointText: String = ""
        if (scenario.refPoint != null) {
            if (scenario.refPoint is CelestialObjects) refPointText = scenario.refPoint.name
            else if (scenario.refPoint is MundanePointsAstron) scenario.refPoint.name.also { refPointText = it }
        }
        return ScenMinMaxFe(
            scenario.name,
            scenario.description,
            scenario.projectName,
            scenario.scenarioType.name,
            scenario.minMaxTypes.name,
            refPointText,
            createCelObjectNames(scenario.celObjects),
            createMundanePointNames(scenario.mundanePoints)
        )
    }

}
