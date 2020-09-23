/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.xchg.domain

import com.radixpro.enigma.references.ChartPointTypes

interface IChartPoints {
    val id: Int
    val rbKey: String
    fun getItemForId(id: Int): IChartPoints
    val pointType: ChartPointTypes
}