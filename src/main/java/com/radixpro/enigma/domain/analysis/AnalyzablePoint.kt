/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.analysis

import com.radixpro.enigma.xchg.domain.IChartPoints

/**
 * Value object for point that can be analyzed.
 */
data class AnalyzablePoint(val chartPoint: IChartPoints,
                           val position: Double)