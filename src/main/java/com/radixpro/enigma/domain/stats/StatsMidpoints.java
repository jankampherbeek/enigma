/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.stats;

import com.radixpro.enigma.references.MidpointTypes;
import org.jetbrains.annotations.NotNull;

/**
 * Definition of midpoints for statistical research.
 */
public class StatsMidpoints implements IStatsTripleRelations {

   final double orb;
   final MidpointTypes midpointType;

   public StatsMidpoints(@NotNull final MidpointTypes midpointType,
                         final double orb) {
      this.midpointType = midpointType;
      this.orb = orb;
   }

   @Override
   public double getOrb() {
      return orb;
   }

   public MidpointTypes getMidpointType() {
      return midpointType;
   }
}
