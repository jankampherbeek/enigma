/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

import org.jetbrains.annotations.NotNull;

/**
 * Calculates the positions for the corner texts.
 */
public class CornerPositions {

   final ChartDrawMetrics metrics;

   public CornerPositions(@NotNull final ChartDrawMetrics metrics) {
      this.metrics = metrics;
   }

   public double[] defineCoordinates(final double angleMc) {
      double hypothenusa = metrics.getDiameterOuterCircle() + metrics.getOffsetOuterCircle() / 2.0;
      var rectTriangle = new RectTriangleAbsolute(0.0, metrics.getCorrForXY());
      double[] coordsAsc = rectTriangle.getCoordinates(hypothenusa);
      rectTriangle = new RectTriangleAbsolute(180.0, metrics.getCorrForXY());
      double[] coordsDesc = rectTriangle.getCoordinates(hypothenusa);
      rectTriangle = new RectTriangleAbsolute(angleMc, metrics.getCorrForXY());
      double[] coordsMc = rectTriangle.getCoordinates(hypothenusa);
      rectTriangle = new RectTriangleAbsolute(angleMc + 180.0, metrics.getCorrForXY());
      double[] coordsIc = rectTriangle.getCoordinates(hypothenusa);
      return new double[]{coordsAsc[0], coordsAsc[1], coordsDesc[0], coordsDesc[1],
            coordsMc[0], coordsMc[1], coordsIc[0], coordsIc[1]};
   }

}
