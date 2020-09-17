/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.astronpos

import com.radixpro.enigma.xchg.domain.IChartPoints

/**
 * Item for a single body/point in a spaeculum based on Placidian semi-arcs.
 *
 * @param lon    longitude
 * @param ra     right ascension
 * @param decl   declination
 * @param propSa proportion of semi-arc.
 */
class SpaeculumPropSaItem(val chartPoint: IChartPoints, val lon: Double, val ra: Double, val decl: Double, val sa: Double,
                          val propSa: Double, val quadrant: Int)