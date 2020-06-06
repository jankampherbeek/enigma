/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.factories;

import com.radixpro.enigma.ui.charts.screens.helpers.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Factory for implementations of PlotCoordinates.
 */
public class PlotCoordinatesFactory {

   private PlotCoordinatesFactory() {
      // prevent instantiation.
   }

   /**
    * Create instance of CuspLinePlotCoordinates.
    *
    * @param angle       The angle to be used for calculating the coordinates.
    * @param drawMetrics . An acutal instance of ChartDrawMetrics.
    * @return instance of CuspLinePlotCoordinates.
    */
   public static CuspLinePlotCoordinates createCuspLinePlotCoordinates(final double angle, final IDrawMetrics drawMetrics) {
      checkNotNull(drawMetrics);
      ChartDrawMetrics metrics = (ChartDrawMetrics) drawMetrics;
      RectTriangleAbsolute rectTriangle = new RectTriangleAbsolute(angle, metrics.getCorrForXY());
      return new CuspLinePlotCoordinates(rectTriangle);
   }

   /**
    * Create instance of CuspTextCoordinates.
    *
    * @param angle        The angle to be used for calculating the coordinates.
    * @param drawMetrics An actual instance of ChartDrawMetrics.
    * @return instance of CuspTextPlotCoordinates.
    */
   public static CuspTextPlotCoordinates createCuspTextPlotCoordinates(final double angle, final IDrawMetrics drawMetrics) {
      checkNotNull(drawMetrics);
      ChartDrawMetrics metrics = (ChartDrawMetrics) drawMetrics;
      RectTriangleAbsolute rectTriangle = new RectTriangleAbsolute(angle, metrics.getCorrForXY());
      return new CuspTextPlotCoordinates(rectTriangle);
   }

   /**
    * Create instance of DegreeLinePlotCoordinates.
    *
    * @param angle        The angle to be used for calculating the coordinates.
    * @param drawMetrics An actual instance of ChartDrawMetrics.
    * @return instance of DegreeLinePlotCoordinates.
    */
   public static DegreeLinePlotCoordinates createDegreeLinePlotCoordinates(final double angle, final IDrawMetrics drawMetrics) {
      checkNotNull(drawMetrics);
      ChartDrawMetrics metrics = (ChartDrawMetrics) drawMetrics;
      RectTriangleAbsolute rectTriangle = new RectTriangleAbsolute(angle, metrics.getCorrForXY());
      return new DegreeLinePlotCoordinates(rectTriangle);
   }

}
