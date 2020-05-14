/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

/**
 * Location of a point on the canvas.
 */
public class Point {

   private final double xPos;
   private final double yPos;

   /**
    * Constructor defines both points.
    *
    * @param xPos the coordinate on the x-axis
    * @param yPos the coordinate on the y-axis
    */
   public Point(final double xPos, final double yPos) {
      this.xPos = xPos;
      this.yPos = yPos;
   }

   public double getXPos() {
      return this.xPos;
   }

   public double getYPos() {
      return this.yPos;
   }
}
