/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

/**
 * Interface for items that will be shown on a drawing at specific coordinates.
 */
public interface PlotCoordinates {

   /**
    * Define the coordinates for the given angle.
    * The implementations should know which value to use from DrawMetrics to define the size of the hypothenusa.
    *
    * @param angle        Angle in degrees.
    * @param IDrawMetrics Actual instance of DrawMetrics.
    * @return The calculated coordinates.
    */
   double[] defineCoordinates(final double angle, final IDrawMetrics IDrawMetrics);

}
