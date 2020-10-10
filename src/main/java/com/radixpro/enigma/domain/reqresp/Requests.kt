/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.reqresp

import com.radixpro.enigma.domain.astronpos.CalculatedChart
import com.radixpro.enigma.domain.astronpos.IPosition
import com.radixpro.enigma.domain.input.DateTimeJulian
import com.radixpro.enigma.domain.input.Location
import com.radixpro.enigma.references.AspectTypes
import com.radixpro.enigma.references.ProgAnalysisType
import com.radixpro.enigma.references.TimeKeys
import com.radixpro.enigma.xchg.api.settings.ChartCalcSettings
import com.radixpro.enigma.xchg.api.settings.ICalcSettings

/**
 * Interface for progressive calculation requests.
 */
interface IProgCalcRequest {
    val dateTime: DateTimeJulian
    val settings: ICalcSettings
    val location: Location?
}

data class CalculatedChartRequest(val settings: ChartCalcSettings,
                                  val dateTimeJulian: DateTimeJulian,
                                  val location: Location)

/**
 * Request for the calculation of progressive positions based on ephejmeris calculations.
 */
data class EphProgCalcRequest(override val dateTime: DateTimeJulian,
                              override val location: Location,
                              override val settings: ICalcSettings) : IProgCalcRequest

data class PrimaryCalcRequest(override val dateTime: DateTimeJulian,
                              val dateTimeRadix: DateTimeJulian,
                              override val settings: ICalcSettings,
                              val timeKey: TimeKeys,
                              override val location: Location,
                              val calculatedChart: CalculatedChart) : IProgCalcRequest

/**
 * Request for analyzing aspects between progressive positions and radix.
 */
data class ProgAnalyzeRequest(val type: ProgAnalysisType,
                              val progPositions: List<IPosition>,
                              val calculatedChart: CalculatedChart,
                              val aspects: List<AspectTypes>,
                              val orb: Double)

data class SecundaryCalcRequest(override val dateTime: DateTimeJulian,
                                val birthDateTime: DateTimeJulian,
                                override val location: Location,
                                override val settings: ICalcSettings) : IProgCalcRequest

data class SolarReturnRequest(val birthDateTime: DateTimeJulian,
                              val settings: ChartCalcSettings,
                              val location: Location,
                              val longSun: Double,
                              val ageForReturn: Int)

/**
 * Request for the calculation of a critical point according to the theory of Ton Tetenburg.
 */
data class TetenburgRequest(val longMcRadix: Double,
                            val solarSpeed: Double,
                            val location: Location,
                            val birthDateTime: DateTimeJulian,
                            val progDateTime: DateTimeJulian)