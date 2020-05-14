/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

/**
 * Defines a rectangular triangle and some basic math to calculate the not yet defined angle.
 */
public class RectTriangle {
   private final Point pointAtEndOfHyp;
   private final double hypothenusa;
   private final double angle;

   /**
    * Constructor reads data and performs calculation. The resulting point is relative to the center of the circle.
    *
    * @param hypothenusa The hypothenusa of the rectangular triangle.
    * @param angle       The angle  as seen frok the center.
    */
   public RectTriangle(final double hypothenusa, final double angle) {
      this.hypothenusa = hypothenusa;
      this.angle = angle;
      pointAtEndOfHyp = definePoint();
   }

   private Point definePoint() {
      double ySize = Math.sin(Math.toRadians(angle)) * hypothenusa;
      double xSize = Math.cos(Math.toRadians(angle)) * hypothenusa;
      return new Point(xSize, ySize);
   }

   public Point getPointAtEndOfHyp() {
      return this.pointAtEndOfHyp;
   }
}

