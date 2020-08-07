/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.analysis;

import com.radixpro.enigma.xchg.domain.IChartPoints;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Value object for point that can be analyzed.
 */
public class AnalyzablePoint {

   private final IChartPoints chartPoint;
   private final double position;

   public AnalyzablePoint(final IChartPoints chartPoint, final double position) {
      this.chartPoint = checkNotNull(chartPoint);
      this.position = position;
   }

   public double getPosition() {
      return position;
   }

   public IChartPoints getChartPoint() {
      return chartPoint;
   }
}
