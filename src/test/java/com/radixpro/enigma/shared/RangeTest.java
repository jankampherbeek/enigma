/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.shared;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RangeTest {

   private static final double DELTA = 0.00000001;
   private Range range;


   @Before
   public void setUp() {
      range = new Range(100.0, 200.0);
   }

   @Test
   public void checkValueNoChange() {
      assertEquals(150.0, range.checkValue(150.0), DELTA);
   }

   @Test
   public void checkValueToLarge() {
      assertEquals(120.0, range.checkValue(220.0), DELTA);
   }

   @Test
   public void checkValueToSmall() {
      assertEquals(150.0, range.checkValue(50.0), DELTA);
   }

   @Test
   public void checkValueNegative() {
      assertEquals(150.0, range.checkValue(-50.0), DELTA);
   }

}