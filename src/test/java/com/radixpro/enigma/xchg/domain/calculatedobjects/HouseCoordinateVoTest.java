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

public class HouseCoordinateVoTest {

   private CelCoordinateElementVo element;
   private HouseCoordinateVo vo;

   @Before
   public void setUp() throws Exception {
      element = new CelCoordinateElementVo(11.1, 22.2, 33.3);
      vo = new HouseCoordinateVo(element);
   }

   @Test
   public void getPosition() {
      assertEquals(element, vo.getPosition());
   }
}