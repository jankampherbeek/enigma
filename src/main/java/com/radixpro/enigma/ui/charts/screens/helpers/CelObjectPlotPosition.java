/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Calculates the position for a celelstial object text.
 */
public class CelObjectPlotPosition {

   private final ChartIDrawMetrics metrics;

   public CelObjectPlotPosition(final ChartIDrawMetrics metrics) {
      this.metrics = checkNotNull(metrics);
   }

   public double[] defineCoordinates(final double angle) {
      double hypothenusa = 0.0;
      if (0.0 <= angle && angle < 45.0) hypothenusa = metrics.getDiameterPosTextsLeft();
      else if (45.0 <= angle && angle < 135.0) hypothenusa = metrics.getDiameterPosTextsTop();
      else if (135.0 <= angle && angle < 225.0) hypothenusa = metrics.getDiameterPosTextsRight();
      else if (225.0 <= angle && angle < 315.0) hypothenusa = metrics.getDiameterPosTextsBottom();
      else if (315.0 <= angle && angle < 360.0) hypothenusa = metrics.getDiameterPosTextsLeft();
      return new RectTriangleAbsolute(angle + 180.0, metrics.getCorrForXY()).getCoordinates(hypothenusa);
   }

}

