/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Data that is used to draw a line.
 */
public class DrawableLine {

   private final Point startPoint;
   private final Point endPoint;
   private final double lineWidth;
   private final Color lineColor;

   public DrawableLine(@NotNull final Point startPoint, @NotNull final Point endPoint, final double lineWidth, @NotNull final Color lineColor) {
      checkArgument(0.0 < lineWidth);
      this.startPoint = startPoint;
      this.endPoint = endPoint;
      this.lineWidth = lineWidth;
      this.lineColor = lineColor;
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
