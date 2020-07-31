/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

import com.radixpro.enigma.ui.shared.creators.PlotCoordinatesFactory;
import org.apache.commons.lang3.ArrayUtils;

import static com.google.common.base.Preconditions.checkNotNull;

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
   public DegreeLinePlotCoordinates(RectTriangleAbsolute rectTriangle) {
      this.rectTriangle = checkNotNull(rectTriangle);
   }

   /**
    * Calcualted the coordinates, checks for multiples of 5 degrees and adapats the coordinates acoordingly.
    *
    * @param index       The index of the degree, is only used to check if the degree is a multiple of 5.
    * @param drawMetrics Instance of DrawMetrics.
    * @return The calculated coordinates.
    */
   public double[] defineCoordinates(final int index, final IDrawMetrics drawMetrics) {
      checkNotNull(drawMetrics);
      ChartDrawMetrics metrics = (ChartDrawMetrics) drawMetrics;
      double[] coords1;
      if (index % 5 == 0) coords1 = rectTriangle.getCoordinates(metrics.getDiameterDegrees5Circle());
      else coords1 = rectTriangle.getCoordinates(metrics.getDiameterDegreesCircle());
      double[] coords2 = rectTriangle.getCoordinates(metrics.getDiameterSignsCircle());
      return ArrayUtils.addAll(coords1, coords2);
   }


}
