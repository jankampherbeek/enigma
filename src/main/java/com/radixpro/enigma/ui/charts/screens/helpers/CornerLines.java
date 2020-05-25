/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Calculates the positions for alle cusplines for the corners.
 */
public class CornerLines {

   final ChartIDrawMetrics metrics;
   final double hypothenusaLarge;
   final double hypothenusaSmall;

   public CornerLines(final ChartIDrawMetrics metrics) {
      this.metrics = checkNotNull(metrics);
      this.hypothenusaLarge = metrics.getDiameterOuterCircle() + metrics.getOffsetOuterCircle();
      this.hypothenusaSmall = metrics.getDiameterHousesCircle() + (metrics.getWidthThickLines() / 2);
   }

   public double[] defineCoordinates(final double angleMc) {
      var rectTriangle = new RectTriangleAbsolute(0.0, metrics.getCorrForXY());
      double[] coordsAsc1 = rectTriangle.getCoordinates(hypothenusaSmall);
      double[] coordsAsc2 = rectTriangle.getCoordinates(hypothenusaLarge);
      rectTriangle = new RectTriangleAbsolute(180.0, metrics.getCorrForXY());
      double[] coordsDesc1 = rectTriangle.getCoordinates(hypothenusaSmall);
      double[] coordsDesc2 = rectTriangle.getCoordinates(hypothenusaLarge);
      rectTriangle = new RectTriangleAbsolute(angleMc, metrics.getCorrForXY());
      double[] coordsMc1 = rectTriangle.getCoordinates(hypothenusaSmall);
      double[] coordsMc2 = rectTriangle.getCoordinates(hypothenusaLarge);
      rectTriangle = new RectTriangleAbsolute(angleMc + 180.0, metrics.getCorrForXY());
      double[] coordsIc1 = rectTriangle.getCoordinates(hypothenusaSmall);
      double[] coordsIc2 = rectTriangle.getCoordinates(hypothenusaLarge);
      return new double[]{coordsAsc1[0], coordsAsc1[1], coordsAsc2[0], coordsAsc2[1],
            coordsDesc1[0], coordsDesc1[1], coordsDesc2[0], coordsDesc2[1],
            coordsMc1[0], coordsMc1[1], coordsMc2[0], coordsMc2[1],
            coordsIc1[0], coordsIc1[1], coordsIc2[0], coordsIc2[1]};
   }

}
