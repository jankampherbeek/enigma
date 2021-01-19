/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

import com.radixpro.enigma.ui.creators.PlotCoordinatesFactory;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Calculates the position for a degree line.
 */
public class DegreeLinePlotCoordinates {

   final RectTriangleAbsolute rectTriangle;

   /**
    * Constructor should be used via factory.
    *
    * @param rectTriangle An already instantiated RectTriangleAbsolute.
    * @see PlotCoordinatesFactory
    */
   public DegreeLinePlotCoordinates(@NotNull RectTriangleAbsolute rectTriangle) {
      this.rectTriangle = rectTriangle;
   }

   /**
    * Calculate the coordinates, checks for multiples of 5 degrees and adapts the coordinates accordingly.
    *
    * @param index       The index of the degree, is only used to check if the degree is a multiple of 5.
    * @param drawMetrics Instance of DrawMetrics.
    * @return The calculated coordinates.
    */
   public double[] defineCoordinates(final int index, @NotNull final IDrawMetrics drawMetrics) {
      ChartDrawMetrics metrics = (ChartDrawMetrics) drawMetrics;
      double[] coords1;
      if (index % 5 == 0) coords1 = rectTriangle.getCoordinates(metrics.getDiameterDegrees5Circle());
      else coords1 = rectTriangle.getCoordinates(metrics.getDiameterDegreesCircle());
      double[] coords2 = rectTriangle.getCoordinates(metrics.getDiameterSignsCircle());
      return ArrayUtils.addAll(coords1, coords2);
   }


}
