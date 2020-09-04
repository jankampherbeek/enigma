/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

import com.radixpro.enigma.ui.creators.PlotCoordinatesFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Calculates the coordinates to show text for a cusp.
 */
public class CuspTextPlotCoordinates implements PlotCoordinates {

   final RectTriangleAbsolute rectTriangle;

   /**
    * Constructor should be used via factory.
    *
    * @param rectTriangle An already instantiated RectTriangleAbsolute.
    * @see PlotCoordinatesFactory
    */
   public CuspTextPlotCoordinates(@NotNull RectTriangleAbsolute rectTriangle) {
      this.rectTriangle = rectTriangle;
   }

   /**
    * Define the coordinates for the given angle.
    * The class knows the size of the hypothenusa.
    *
    * @param angle       Angle in degrees.
    * @param drawMetrics Actual instance of ChartDrawMetrics.
    * @return The calculated coordinates.
    */
   public double[] defineCoordinates(final double angle, @NotNull final IDrawMetrics drawMetrics) {
      ChartDrawMetrics metrics = (ChartDrawMetrics) drawMetrics;
      double hypothenusa = 0.0;
      if (0.0 <= angle && angle < 45.0) hypothenusa = metrics.getDiameterCuspTextsLeft();
      else if (45.0 <= angle && angle < 135.0) hypothenusa = metrics.getDiameterCuspTextsTop();
      else if (135.0 <= angle && angle < 225.0) hypothenusa = metrics.getDiameterCuspTextsRight();
      else if (225.0 <= angle && angle < 315.0) hypothenusa = metrics.getDiameterCuspTextsBottom();
      else if (315.0 <= angle && angle < 360.0) hypothenusa = metrics.getDiameterCuspTextsLeft();
      return rectTriangle.getCoordinates(hypothenusa);
   }


}

