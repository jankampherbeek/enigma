/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.ui.helpers

import com.radixpro.enigma.Rosetta
import com.radixpro.enigma.share.exceptions.ItemNotFoundException
import com.radixpro.enigma.statistics.ui.domain.ScenMinMaxFe
import com.radixpro.enigma.statistics.ui.domain.ScenRangeFe
import com.radixpro.enigma.statistics.ui.domain.ScenarioFe

const val BREAK = "\n"
const val COLON = ": "
const val SPACE = " "

interface ScenDetailsText {
    fun createText(scenario: ScenarioFe): String

    fun headerText(scen: ScenarioFe): String {
        val names = Rosetta.getText("ui.stats.scendetailstext.names")
        val namesText = names.replace("[name]", scen.name).replace("[projname]", scen.projName)
        val scenarioTypeText = Rosetta.getText("ui.stats.scendetailstext.typename") + COLON + scen.typeName
        return namesText + BREAK + scenarioTypeText + BREAK
    }

    fun descrText(descr: String): String {
        return Rosetta.getText("ui.stats.scendetailstext.descr") + COLON + descr + BREAK
    }

    fun celObjectsText(coNames: List<String>): String {
        val names = StringBuilder()
        for (coName in coNames) {
            names.append(coName).append(SPACE)
        }
        return Rosetta.getText("ui.stats.scendetailstext.celobjects") + COLON + names + BREAK
    }

    fun mundPointsText(mpNames: List<String>): String {
        val names = StringBuilder()
        for (mpName in mpNames) {
            names.append(mpName).append(SPACE)
        }
        return Rosetta.getText("ui.stats.scendetailstext.mundpoints") + COLON + names + BREAK
    }
}

class ScenSpecificDetailsText : ScenDetailsText {

    override fun createText(scenario: ScenarioFe): String {
        return when (scenario.typeName) {
            "RANGE" -> ScenRangeDetailsText().createText(scenario)
            "MINMAX" -> ScenMinMaxDetailsText().createText(scenario)
            else -> throw ItemNotFoundException("Could not find Scenario Type Name for ${scenario.typeName} in ScenSpecificDetailsText")
        }
    }

}


class ScenRangeDetailsText : ScenDetailsText {

    override fun createText(scenario: ScenarioFe): String {
        val actualScen = scenario as ScenRangeFe
        return (headerText(actualScen) + rangeTypeText(actualScen.rangeTypeName) + descrText(actualScen.descr) + celObjectsText(actualScen.celObjectNames)
                + mundPointsText(actualScen.mundanePointNames))
    }

    private fun rangeTypeText(rangeType: String): String {
        return Rosetta.getText("ui.stats.scendetailstext.rangetype") + COLON + rangeType + BREAK
    }

}


class ScenMinMaxDetailsText : ScenDetailsText {

    override fun createText(scenario: ScenarioFe): String {
        val actualScen = scenario as ScenMinMaxFe
        return (headerText(actualScen) + minMaxTypeText(actualScen.minMaxTypeName) + descrText(actualScen.descr) + celObjectsText(actualScen.celObjectNames)
                + mundPointsText(actualScen.mundanePointNames))
    }

    private fun minMaxTypeText(rangeType: String): String {
        return Rosetta.getText("ui.stats.scendetailstext.minmaxtype") + COLON + rangeType + BREAK
    }

}


