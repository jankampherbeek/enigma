/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.stats;

import com.radixpro.enigma.references.Ayanamshas;
import com.radixpro.enigma.references.StatsRangeTypes;
import org.jetbrains.annotations.NotNull;

/**
 * Definition of signs for statistical research.
 */
public class StatsSigns implements IStatsRangeRelations {

   private final int index;
   private final StatsRangeTypes rangeType;
   private final Ayanamshas ayanamsha;

   public StatsSigns(@NotNull final Ayanamshas ayanamsha,
                     final int index) {
      this.index = index;
      this.ayanamsha = ayanamsha;
      rangeType = StatsRangeTypes.SIGNS;
   }

   @Override
   public int getIndex() {
      return index;
   }

   @Override
   public StatsRangeTypes getRangeType() {
      return rangeType;
   }

   public Ayanamshas getAyanamsha() {
      return ayanamsha;
   }
}
