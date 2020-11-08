/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.api.converters

import com.radixpro.enigma.references.CelestialObjects
import com.radixpro.enigma.references.MundanePointsAstron
import com.radixpro.enigma.statistics.core.ScenRangeBe
import com.radixpro.enigma.statistics.core.ScenarioBe
import com.radixpro.enigma.statistics.di.StatsInjector.injectScenRangeConverter
import com.radixpro.enigma.statistics.ui.domain.ScenRangeFe
import com.radixpro.enigma.statistics.ui.domain.ScenarioFe
import com.radixpro.enigma.statistics.ui.domain.ScenarioTypes
import com.radixpro.enigma.statistics.ui.domain.StatsRangeTypes

interface ScenarioConverter {
    fun feRequestToBe(scenFe: ScenarioFe): ScenarioBe
    fun beRequestToFe(scenBe: ScenarioBe): ScenarioFe
}

class ScenConverterFactory() {

    fun getConverter(type: ScenarioTypes): ScenarioConverter {
        if (type == ScenarioTypes.RANGE) return injectScenRangeConverter()
        return injectScenRangeConverter()     // FIXME handle all types of converters
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
                createCelObjectNames(scenario.celObjects),
                createMundanePointNames(scenario.mundanePoints)
        )
    }

    private fun createCelestialObjects(celObjectNames: List<String>): List<CelestialObjects> {
        val objects: MutableList<CelestialObjects> = ArrayList()
        for (name: String in celObjectNames) {
            objects.add(CelestialObjects.valueOf(name))
        }
        return objects.toList()
    }

    private fun createCelObjectNames(celestialObjects: List<CelestialObjects>): List<String> {
        val names: MutableList<String> = ArrayList()
        for (celestialObject in celestialObjects) {
            names.add(celestialObject.name)
        }
        return names.toList()
    }

    private fun createMundanePoints(mundanePointNames: List<String>): List<MundanePointsAstron> {
        val mPoints: MutableList<MundanePointsAstron> = ArrayList()
        for (name: String in mundanePointNames) {
            mPoints.add(MundanePointsAstron.valueOf(name))
        }
        return mPoints.toList()
    }

    private fun createMundanePointNames(mundPoints: List<MundanePointsAstron>): List<String> {
        val names: MutableList<String> = ArrayList()
        for (point: MundanePointsAstron in mundPoints) {
            names.add(point.name)
        }
        return names.toList()
    }

}