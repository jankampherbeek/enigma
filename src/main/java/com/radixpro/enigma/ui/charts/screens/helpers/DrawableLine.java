/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

import javafx.scene.paint.Color;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Data that is used to draw a line.
 */
public class DrawableLine {

   private final Point startPoint;
   private final Point endPoint;
   private final double lineWidth;
   private final Color lineColor;

   /**
    * Constructor defines all properties.
    *
    * @param startPoint the first point. PRE: not null.
    * @param endPoint   the second point. PRE: not null.
    * @param lineWidth  width of the line to draw. PRE: lineWidth > 0.0.
    * @param lineColor  color of the line. PRE: not null.
    */
   public DrawableLine(final Point startPoint, final Point endPoint, final double lineWidth, final Color lineColor) {
      checkArgument(0.0 < lineWidth);
      this.startPoint = checkNotNull(startPoint);
      this.endPoint = checkNotNull(endPoint);
      this.lineWidth = lineWidth;
      this.lineColor = checkNotNull(lineColor);
   }

   public Point getStartPoint() {
      return startPoint;
   }

   public Point getEndPoint() {
      return endPoint;
   }

   public double getLineWidth() {
      return lineWidth;
   }

   public Color getLineColor() {
      return lineColor;
   }

}
