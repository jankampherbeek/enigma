/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Calculates the position for a glyph that represents a celestial object.
 */
public class CelObject {

   private final ChartIDrawMetrics metrics;

   public CelObject(final ChartIDrawMetrics metrics) {
      this.metrics = checkNotNull(metrics);
   }

   public double[] defineCoordinates(final double angle) {
      final RectTriangleAbsolute rectTriangle = new RectTriangleAbsolute(angle + 180.0, metrics.getCorrForXY());
      return rectTriangle.getCoordinates(metrics.getDiameterCelBodiesMedium());
   }

}
