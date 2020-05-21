/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.analysis;

import com.radixpro.enigma.xchg.domain.AspectTypes;

import static com.google.common.base.Preconditions.checkNotNull;

public class AnalyzedResultAspect implements AnalyzedResultPairInterface {

   private final AnalyzablePoint firstPoint;
   private final AnalyzablePoint secondPoint;
   private final AspectTypes aspectType;
   private final double actualOrb;
   private final double maxOrb;
   private final double percOrb;


   public AnalyzedResultAspect(final AnalyzablePoint firstPoint, final AnalyzablePoint secondPoint,
                               final AspectTypes aspectType, final double actualOrb, final double maxOrb) {
      this.firstPoint = checkNotNull(firstPoint);
      this.secondPoint = checkNotNull(secondPoint);
      this.aspectType = checkNotNull(aspectType);
      this.actualOrb = actualOrb;
      this.maxOrb = maxOrb;
      this.percOrb = calculatePercOrb();
   }


   @Override
   public AnalyzablePoint getFirst() {
      return firstPoint;
   }

   @Override
   public AnalyzablePoint getSecond() {
      return secondPoint;
   }

   @Override
   public double getActualOrb() {
      return actualOrb;
   }

   @Override
   public double getMaxOrb() {
      return maxOrb;
   }

   @Override
   public double getPercOrb() {
      return percOrb;
   }

   private double calculatePercOrb() {
      return (actualOrb / maxOrb) * 100.0;
   }

}
