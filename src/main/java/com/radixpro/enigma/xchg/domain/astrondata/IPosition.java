/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.astrondata;

import com.radixpro.enigma.xchg.domain.IChartPoints;

/**
 * Interface for most used elements of a position.
 */
public interface IPosition {

   double getLongitude();

   double getDeclination();

   IChartPoints getChartPoint();

}
