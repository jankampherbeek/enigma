/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain;

import com.radixpro.enigma.xchg.domain.analysis.ChartPointTypes;

public interface IChartPoints {

   int getId();

   String getRbKey();

   IChartPoints getItemForId(final int id);

   ChartPointTypes getPointType();

}
