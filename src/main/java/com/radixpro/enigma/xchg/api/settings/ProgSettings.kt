/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.settings;

import com.radixpro.enigma.references.Ayanamshas;
import com.radixpro.enigma.xchg.domain.IChartPoints;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public class ProgSettings implements ICalcSettings {

   private final List<IChartPoints> points;
   private final Ayanamshas ayanamsha;
   private final boolean sidereal;
   private final boolean topocentric;

   /**
    * Constructor defines all properties.
    *
    * @param points      the points to use. PRE: not null and not empty.
    * @param ayanamsha   the ayanamsha to use. PRE: not null if sidereal is true.
    * @param sidereal    use sidereal ecliptic ?
    * @param topocentric use topocentric position ?
    */
   public ProgSettings(final List<IChartPoints> points, final Ayanamshas ayanamsha, final boolean sidereal, final boolean topocentric) {
      checkArgument(null != points && !points.isEmpty());
      checkArgument(null != ayanamsha || !sidereal);
      this.points = points;
      this.ayanamsha = ayanamsha;
      this.sidereal = sidereal;
      this.topocentric = topocentric;
   }

   @Override
   public List<IChartPoints> getPoints() {
      return points;
   }

   @Override
   public Ayanamshas getAyamsha() {
      return ayanamsha;
   }

   @Override
   public boolean isSidereal() {
      return sidereal;
   }

   public boolean isTopocentric() {
      return topocentric;
   }

}
