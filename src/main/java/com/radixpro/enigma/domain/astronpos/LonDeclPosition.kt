/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.astronpos

import com.radixpro.enigma.xchg.domain.IChartPoints

class LonDeclPosition(override val chartPoint: IChartPoints,
                      override val longitude: Double,
                      override val declination: Double) : IPosition