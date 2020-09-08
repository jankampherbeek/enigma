/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.stats;

import com.radixpro.enigma.references.HouseSystems;
import com.radixpro.enigma.references.StatsRangeTypes;
import org.jetbrains.annotations.NotNull;

/**
 * Definition of houses for statistical research.
 */
public class StatsHouses implements IStatsRangeRelations {

   private final int index;
   private final StatsRangeTypes rangeType;
   private final HouseSystems houseSystem;

   public StatsHouses(@NotNull final HouseSystems houseSystem,
                      final int index) {
      this.index = index;
      this.houseSystem = houseSystem;
      rangeType = StatsRangeTypes.HOUSES;
   }

   @Override
   public int getIndex() {
      return index;
   }

   @Override
   public StatsRangeTypes getRangeType() {
      return rangeType;
   }

   public HouseSystems getHouseSystem() {
      return houseSystem;
   }

}
