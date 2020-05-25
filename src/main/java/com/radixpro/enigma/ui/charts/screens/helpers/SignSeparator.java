/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

import org.apache.commons.lang3.ArrayUtils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Calculates the position for a sign separator line.
 */
public class SignSeparator {

   final ChartIDrawMetrics metrics;

   public SignSeparator(ChartIDrawMetrics metrics) {
      this.metrics = checkNotNull(metrics);
   }

   public double[] defineCoordinates(final double angle) {
      final RectTriangleAbsolute rectTriangle = new RectTriangleAbsolute(angle, metrics.getCorrForXY());
      double[] coords1 = rectTriangle.getCoordinates(metrics.getDiameterOuterCircle());
      double[] coords2 = rectTriangle.getCoordinates(metrics.getDiameterSignsCircle());
      return ArrayUtils.addAll(coords1, coords2);
   }

}

