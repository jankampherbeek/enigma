/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.reqresp

import com.radixpro.enigma.domain.analysis.IAnalyzedPair
import com.radixpro.enigma.domain.astronpos.CalculatedChart
import com.radixpro.enigma.domain.astronpos.IPosition

data class CalculatedChartResponse(val calculatedChart: CalculatedChart, val resultMsg: String)

/**
 * Response for analyzed progressive aspects.
 */
data class EphProgAspectResponse(val chartId: Long, val analyzedAspects: List<IAnalyzedPair>)

/**
 * Response after reading a datafile and converting it to Json.
 */
data class InputDataFileResponse(val resultMsg: String,
                                 val errorLines: List<String>,
                                 val isSuccess: Boolean)

data class SimpleProgResponse(val positions: List<IPosition>,
                              val request: IProgCalcRequest)

data class SolarReturnResponse(val solarReturnChart: CalculatedChart,
                               val resultMsg: String)

/**
 * Response for the calculation of a critical point according to the theory by Ton Tetenburg.
 */
data class TetenburgResponse(val longAsc: Double,
                             val resultMsg: String)