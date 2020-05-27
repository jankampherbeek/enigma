/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

import com.radixpro.enigma.xchg.domain.analysis.IChartPoints;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Info about the position of a body on the circle. Used for drawing aspectlines.
 */
public class PointInfoForAspect {

   private final IChartPoints point;
   private final double angleFromAsc;

   /**
    * Construcotr defines all properties.
    *
    * @param point        the point for which the information is given. PRE: not null.
    * @param angleFromAsc the angle from the ascendant. PRE: 0 <= angleFromAsc < 360
    */
   public PointInfoForAspect(final IChartPoints point, final double angleFromAsc) {
      checkArgument(0.0 <= angleFromAsc && angleFromAsc < 360.0);
      this.point = checkNotNull(point);
      this.angleFromAsc = angleFromAsc;
   }

   public IChartPoints getPoint() {
      return point;
   }

   public double getAngleFromAsc() {
      return angleFromAsc;
   }
}
