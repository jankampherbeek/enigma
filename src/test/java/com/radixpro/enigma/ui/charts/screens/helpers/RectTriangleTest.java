/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RectTriangleTest {

   private static final double DELTA = 0.00000001;

   @Test
   public void getPointAtEndOfHypFirstQuadrant() {
      double hypothenusa = 100.0;
      double angle = 10.0;
      Point result = new RectTriangle(hypothenusa, angle).getPointAtEndOfHyp();
      assertEquals(17.3648177667, result.getYPos(), DELTA);
      assertEquals(98.4807753012, result.getXPos(), DELTA);
   }

   @Test
   public void getPointAtEndOfHypSecondQuadrant() {
      double hypothenusa = 100.0;
      double angle = 152.0;
      Point result = new RectTriangle(hypothenusa, angle).getPointAtEndOfHyp();
      assertEquals(46.947156279, result.getYPos(), DELTA);
      assertEquals(-88.294759286, result.getXPos(), DELTA);
   }


}