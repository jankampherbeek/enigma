/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PointTest {

   private static final double DELTA = 0.00000001;
   private final double xValue = 330.0;
   private final double yValue = 220.0;
   private Point point;

   @Before
   public void setUp() {
      point = new Point(xValue, yValue);
   }

   @Test
   public void getXPos() {
      assertEquals(xValue, point.getXPos(), DELTA);
   }

   @Test
   public void getYPos() {
      assertEquals(yValue, point.getYPos(), DELTA);
   }
}