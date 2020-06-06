/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GeographicCoordinateTest {

   private static final double DELTA = 0.00000001;
   private final int degrees = 52;
   private final int minutes = 30;
   private final int seconds = 0;
   private final String direction = "N";
   private GeographicCoordinate coordinate;

   @Before
   public void setUp() {
      coordinate = new GeographicCoordinate(degrees, minutes, seconds, direction, 52.5);
   }

   @Test
   public void getDegrees() {
      assertEquals(degrees, coordinate.getDegrees());
   }

   @Test
   public void getMinutes() {
      assertEquals(minutes, coordinate.getMinutes());
   }

   @Test
   public void getSeconds() {
      assertEquals(seconds, coordinate.getSeconds());
   }

   @Test
   public void getDirection() {
      assertEquals(direction, coordinate.getDirection());
   }

   @Test
   public void getValue() {
      double value = 52.5;
      assertEquals(value, coordinate.getValue(), DELTA);
   }

}