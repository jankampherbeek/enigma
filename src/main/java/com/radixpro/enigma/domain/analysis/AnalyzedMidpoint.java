/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.analysis;

import com.radixpro.enigma.references.MidpointTypes;
import org.jetbrains.annotations.NotNull;

import static com.google.common.base.Preconditions.checkArgument;

public class AnalyzedMidpoint implements IAnalyzedPair {


   private final AnalyzablePoint firstPoint;
   private final AnalyzablePoint secondPoint;
   private final AnalyzablePoint centerPoint;
   private final MidpointTypes midpointType;
   private final double actualOrb;
   private final double maxOrb;
   private final double percOrb;

   /**
    * PRE: 0.0 <= actualOrb <= maxOrb
    */
   public AnalyzedMidpoint(@NotNull final AnalyzablePoint firstPoint, @NotNull final AnalyzablePoint secondPoint, @NotNull final AnalyzablePoint centerPoint,
                           @NotNull final MidpointTypes midpointType, final double actualOrb, final double maxOrb) {
      checkArgument(0.0 <= actualOrb && actualOrb <= maxOrb);
      this.firstPoint = firstPoint;
      this.secondPoint = secondPoint;
      this.centerPoint = centerPoint;
      this.midpointType = midpointType;
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

   public AnalyzablePoint getCenterPoint() {
      return centerPoint;
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

   public MidpointTypes getMidpointType() {
      return midpointType;
   }

   private double calculatePercOrb() {
      return (actualOrb / maxOrb) * 100.0;
   }

}
