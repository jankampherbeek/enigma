/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.api.converters

import com.radixpro.enigma.astronomy.ui.domain.CelObjects
import com.radixpro.enigma.astronomy.ui.domain.MundanePoints
import com.radixpro.enigma.statistics.core.ScenRangeBe
import com.radixpro.enigma.statistics.core.ScenarioBe
import com.radixpro.enigma.statistics.ui.domain.ScenRangeFe
import com.radixpro.enigma.statistics.ui.domain.ScenarioFe
import com.radixpro.enigma.statistics.ui.domain.ScenarioTypes
import com.radixpro.enigma.statistics.ui.domain.StatsRangeTypes

interface ScenarioConverter {
    fun feRequestToBe(scenFe: ScenarioFe): ScenarioBe
    fun beRequestToFe(scenBe: ScenarioBe): ScenarioFe
}

class ScenRangeConverter : ScenarioConverter {

    override fun feRequestToBe(scenario: ScenarioFe): ScenarioBe {
        val scenFe = scenario as ScenRangeFe
        return ScenRangeBe(
                scenFe.name,
                scenFe.descr,
                scenFe.projName,
                ScenarioTypes.valueOf(scenFe.typeName),
                StatsRangeTypes.valueOf(scenFe.rangeTypeName),
                createCelObjects(scenFe.celObjectNames),
                createMundanePoints(scenFe.mundanePointNames)
        )
    }

    override fun beRequestToFe(scenario: ScenarioBe): ScenarioFe {
        val scenBe = scenario as ScenRangeBe
        return ScenRangeFe(
                scenBe.name,
                scenBe.description,
                scenBe.projectName,
                scenBe.scenarioType.name,
                scenBe.rangeType.name,
                createCelObjectNames(scenBe.celObjects),
                createMundanePointNames(scenBe.mundanePoints)
        )
    }

    private fun createCelObjects(celObjectNames: List<String>): List<CelObjects> {
        val objects: MutableList<CelObjects> = ArrayList()
        for (name: String in celObjectNames) {
            objects.add(CelObjects.valueOf(name))
        }
        return objects.toList()
    }

    private fun createCelObjectNames(celObjects: List<CelObjects>): List<String> {
        val names: MutableList<String> = ArrayList()
        for (celObject in celObjects) {
            names.add(celObject.name)
        }
        return names.toList()
    }

    private fun createMundanePoints(mundanePointNames: List<String>): List<MundanePoints> {
        val mPoints: MutableList<MundanePoints> = ArrayList()
        for (name: String in mundanePointNames) {
            mPoints.add(MundanePoints.valueOf(name))
        }
        return mPoints.toList()
    }

    private fun createMundanePointNames(mundPoints: List<MundanePoints>): List<String> {
        val names: MutableList<String> = ArrayList()
        for (point: MundanePoints in mundPoints) {
            names.add(point.name)
        }
        return names.toList()
    }

}