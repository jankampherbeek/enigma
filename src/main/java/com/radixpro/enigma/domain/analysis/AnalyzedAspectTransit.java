/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.analysis;

import com.radixpro.enigma.references.AspectTypes;
import org.jetbrains.annotations.NotNull;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Actual aspect between a transit position and a radix postion.
 */
public class AnalyzedAspectTransit implements IAnalyzedPair {

   private final AnalyzablePoint transitPoint;
   private final AnalyzablePoint radixPoint;
   private final AspectTypes aspectType;
   private final double orb;
   private final double maxOrb;
   private final double percOrb;

   /**
    * Constructor defines all properties.
    *
    * @param transitPoint transiting point.
    * @param radixPoint   radix point.
    * @param aspectType   Aspect type (angle).
    * @param orb          actual orb. PRE: >= 0.0 .
    * @param maxOrb       max orb. PRE: >= 0.0 .
    */
   public AnalyzedAspectTransit(@NotNull final AnalyzablePoint transitPoint, @NotNull final AnalyzablePoint radixPoint, @NotNull final AspectTypes aspectType,
                                final double orb, final double maxOrb) {
      checkArgument(orb >= 0.0);
      checkArgument(maxOrb >= 0.0);
      this.transitPoint = transitPoint;
      this.radixPoint = radixPoint;
      this.aspectType = aspectType;
      this.orb = orb;
      this.maxOrb = maxOrb;
      percOrb = (orb / maxOrb * 100.0);
   }

   @Override
   public AnalyzablePoint getFirst() {
      return transitPoint;
   }

   @Override
   public AnalyzablePoint getSecond() {
      return radixPoint;
   }

   public AspectTypes getAspectType() {
      return aspectType;
   }

   @Override
   public double getActualOrb() {
      return orb;
   }

   @Override
   public double getMaxOrb() {
      return maxOrb;
   }

   @Override
   public double getPercOrb() {
      return percOrb;
   }
}
