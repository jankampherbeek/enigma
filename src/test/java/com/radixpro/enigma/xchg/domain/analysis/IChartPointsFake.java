/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.analysis;

import com.radixpro.enigma.xchg.domain.CelestialObjects;

public class IChartPointsFake implements IChartPoints {

   @Override
   public int getId() {
      return 1;
   }

   @Override
   public String getRbKey() {
      return "dummy.value";
   }

   @Override
   public IChartPoints getItemForId(int id) {
      return CelestialObjects.EMPTY;
   }

   @Override
   public ChartPointTypes getPointType() {
      return ChartPointTypes.CEL_BODIES;
   }

}
