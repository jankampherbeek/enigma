/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.analysis;

import com.radixpro.enigma.references.MidpointTypes;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class AnalyzedMidpoint implements IAnalyzedPair {


   private final AnalyzablePoint firstPoint;
   private final AnalyzablePoint secondPoint;
   private final AnalyzablePoint centerPoint;
   private final MidpointTypes midpointType;
   private final double actualOrb;
   private final double maxOrb;
   private final double percOrb;

   /**
    * Constructor defines all parameters.
    * PRE: 0.0 <= actualOrb <= maxOrb
    *
    * @param firstPoint   First point that was compared. PRE: not null.
    * @param secondPoint  Second point that was compared. PRE: not null.
    * @param centerPoint  The point at the center of the midpoint.
    * @param midpointType The type of midpoint.
    * @param actualOrb    The actual orb fir this aspect.
    * @param maxOrb       The maximum orb for this aspect.
    */
   public AnalyzedMidpoint(final AnalyzablePoint firstPoint, final AnalyzablePoint secondPoint, final AnalyzablePoint centerPoint,
                           final MidpointTypes midpointType, final double actualOrb, final double maxOrb) {
      checkArgument(0.0 <= actualOrb && actualOrb <= maxOrb);
      this.firstPoint = checkNotNull(firstPoint);
      this.secondPoint = checkNotNull(secondPoint);
      this.centerPoint = checkNotNull(centerPoint);
      this.midpointType = checkNotNull(midpointType);
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
