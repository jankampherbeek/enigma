/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.analysis;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

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
    * @param transitPoint transiting point. PRE: not null.
    * @param radixPoint   radix point. PRE: not null.
    * @param aspectType   Aspect type (angle). PRE: not null.
    * @param orb          actual orb. PRE: >= 0.0 .
    * @param maxOrb       max orb. PRE: >= 0.0 .
    */
   public AnalyzedAspectTransit(final AnalyzablePoint transitPoint, final AnalyzablePoint radixPoint, final AspectTypes aspectType,
                                final double orb, final double maxOrb) {
      checkArgument(orb >= 0.0);
      checkArgument(maxOrb >= 0.0);
      this.transitPoint = checkNotNull(transitPoint);
      this.radixPoint = checkNotNull(radixPoint);
      this.aspectType = checkNotNull(aspectType);
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
