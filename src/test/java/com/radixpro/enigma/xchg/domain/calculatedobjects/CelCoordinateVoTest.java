/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.calculatedobjects;

import com.radixpro.enigma.xchg.domain.CelCoordinateElementVo;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CelCoordinateVoTest {

   private CelCoordinateElementVo pos;
   private CelCoordinateElementVo speed;
   private CelCoordinateVo vo;

   @Before
   public void setUp() {
      pos = new CelCoordinateElementVo(100.0, 1.0, 20.0);
      speed = new CelCoordinateElementVo(1.0, 0.5, -0.5);
      vo = new CelCoordinateVo(pos, speed);
   }

   @Test
   public void getPosition() {
      assertEquals(pos, vo.getPosition());
   }

   @Test
   public void getSpeed() {
      assertEquals(speed, vo.getSpeed());
   }

}