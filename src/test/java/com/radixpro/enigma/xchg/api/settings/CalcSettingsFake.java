/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.settings;

import com.radixpro.enigma.xchg.domain.Ayanamshas;
import com.radixpro.enigma.xchg.domain.CelestialObjects;
import com.radixpro.enigma.xchg.domain.analysis.IChartPoints;

import java.util.ArrayList;
import java.util.List;

public class CalcSettingsFake implements ICalcSettings {

   @Override
   public List<IChartPoints> getPoints() {
      List<IChartPoints> points = new ArrayList<>();
      points.add(CelestialObjects.URANUS);
      return points;
   }

   @Override
   public Ayanamshas getAyamsha() {
      return Ayanamshas.HUBER;
   }

   @Override
   public boolean isSidereal() {
      return true;
   }
}
