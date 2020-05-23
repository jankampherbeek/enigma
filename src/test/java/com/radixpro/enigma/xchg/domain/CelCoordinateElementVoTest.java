/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain;

import org.junit.Before;
import org.junit.Test;

import static com.radixpro.enigma.testsupport.TextConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

public class CelCoordinateElementVoTest {

   private final double base = 123.456;
   private final double deviation = -22.3;
   private final double distance = 33.3;
   private CelCoordinateElementVo vo;

   @Before
   public void setUp() throws Exception {
      vo = new CelCoordinateElementVo(base, deviation, distance);
   }

   @Test
   public void getBase() {
      assertEquals(base, vo.getBase(), DELTA_8_POS);
   }

   @Test
   public void getDeviation() {
      assertEquals(deviation, vo.getDeviation(), DELTA_8_POS);
   }

   @Test
   public void getDistance() {
      assertEquals(distance, vo.getDistance(), DELTA_8_POS);
   }

   @Test(expected = IllegalArgumentException.class)
   public void checkPreBase() {
      vo = new CelCoordinateElementVo(-400.18, deviation, distance);
   }

   @Test(expected = IllegalArgumentException.class)
   public void checkPreDeviation() {
      vo = new CelCoordinateElementVo(base, 110.11, distance);
   }


}