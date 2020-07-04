/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

public class DrawableLineTest {

   private final double lineWidth = 3.3;
   private final Color lineColor = Color.GREEN;
   private Point startPoint;
   private Point endPoint;
   private DrawableLine drawableLine;

   @Before
   public void setUp() {
      startPoint = new Point(100.0, 50.0);
      endPoint = new Point(200.0, 80.5);
      drawableLine = new DrawableLine(startPoint, endPoint, lineWidth, lineColor);
   }

   @Test
   public void getStartPoint() {
      assertEquals(startPoint, drawableLine.getStartPoint());
   }

   @Test
   public void getEndPoint() {
      assertEquals(endPoint, drawableLine.getEndPoint());
   }

   @Test
   public void getLineWidth() {
      assertEquals(lineWidth, drawableLine.getLineWidth(), DELTA_8_POS);
   }

   @Test
   public void getLineColor() {
      assertEquals(lineColor, drawableLine.getLineColor());
   }
}