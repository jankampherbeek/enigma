/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.stats;

import com.radixpro.enigma.references.DistanceTypes;
import com.radixpro.enigma.references.MinMaxType;
import org.jetbrains.annotations.NotNull;

/**
 * Definition of minimum/maximum positions for statistical research.
 */
public class StatsMinMax implements IStatsMinMaxRelations {

   final MinMaxType minMaxType;
   final DistanceTypes distanceType;
   final double separation;

   public StatsMinMax(@NotNull final MinMaxType minMaxType,
                      @NotNull final DistanceTypes distanceType,
                      final double separation) {
      this.minMaxType = minMaxType;
      this.distanceType = distanceType;
      this.separation = separation;
   }

   @Override
   public MinMaxType getMinMaxType() {
      return minMaxType;
   }

   @Override
   public DistanceTypes getDistanceType() {
      return distanceType;
   }

   @Override
   public double getSeparation() {
      return separation;
   }
}
