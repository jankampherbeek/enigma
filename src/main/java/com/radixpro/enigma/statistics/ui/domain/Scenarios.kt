/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.statistics.ui.domain

interface ScenarioFe {
    val name: String
    val descr: String
    val projName: String
    val typeName: String
}

data class ScenRangeFe(override val name: String,
                       override val descr: String,
                       override val projName: String,
                       override val typeName: String,
                       val rangeTypeName: String,
                       val houseSystemName: String,
                       val celObjectNames: List<String>,
                       val mundanePointNames: List<String>) : ScenarioFe

data class ScenMinMaxFe(override val name: String,
                        override val descr: String,
                        override val projName: String,
                        override val typeName: String,
                        val minMaxTypeName: String,
                        val celObjectNames: List<String>,
                        val mundanePointNames: List<String>) : ScenarioFe

