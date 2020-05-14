/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

/**
 * Metrics for drawing a figure. Supports resizing.
 * The implementations will be mutable as the value for the imension of the canvas can be set.
 * This is required to support resizing the figure.
 */
public interface DrawMetrics {

   /**
    * Change base-dimension. This method should be called after resizing by the user.
    *
    * @param newDimension New size of the canvas, it should be the minimum of the height and width of the canvas.
    */
   void setCanvasDimension(final double newDimension);

}
