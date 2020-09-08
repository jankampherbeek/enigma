/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.stats;

import com.radixpro.enigma.references.AspectTypes;
import org.jetbrains.annotations.NotNull;

/**
 * Definition of aspects for statistical research.
 */
public class StatsAspects implements IStatsDualRelations {

   final double orb;
   final AspectTypes aspectType;

   public StatsAspects(@NotNull final AspectTypes aspectType,
                       final double orb) {
      this.orb = orb;
      this.aspectType = aspectType;
   }

   public AspectTypes getAspectType() {
      return aspectType;
   }

   @Override
   public double getOrb() {
      return 0;
   }
}
