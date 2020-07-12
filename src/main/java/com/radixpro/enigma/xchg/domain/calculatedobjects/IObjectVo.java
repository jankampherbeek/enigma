/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.calculatedobjects;

import com.radixpro.enigma.xchg.domain.IChartPoints;

/**
 * Interface for full positions, containing multiple coordinate systems.
 * TODO remove obsolete interface
 */
public interface IObjectVo {

   ICoordinateVo getEclipticCoords();

   ICoordinateVo getEquatorialCoords();

   IChartPoints getChartPoint();

}
