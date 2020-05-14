/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

/**
 * Defines a rectangular triangle and some basic math to calculate the not yet defined angle.
 * Calculates a Point with absolute coordinates, referring to the top-left position of the canvas.
 */
public class RectTriangleAbsolute {

   private final double angle;
   private final double corrForXY;

   /**
    * Constructor reads data and performs calculation. The resulting point is relative to the center of the circle.
    *
    * @param angle     The angle  as seen frok the center.
    * @param corrForXY Correctionfactor, compensates the offset.
    */
   public RectTriangleAbsolute(final double angle, final double corrForXY) {
      this.angle = angle;
      this.corrForXY = corrForXY;
   }

   /**
    * Calculate the point at the end of the hypothenusa.
    *
    * @param hypothenusa length of the oblique side of the triangle.
    * @return the calculated point.
    */
   public double[] getCoordinates(final double hypothenusa) {
      return defineCoordinates(hypothenusa);
   }

   private double[] defineCoordinates(final double hypothenusa) {
      double ySize = corrForXY + Math.sin(Math.toRadians(angle)) * hypothenusa;
      double xSize = corrForXY + Math.cos(Math.toRadians(angle)) * hypothenusa;
      return new double[]{xSize, ySize};
   }

}
