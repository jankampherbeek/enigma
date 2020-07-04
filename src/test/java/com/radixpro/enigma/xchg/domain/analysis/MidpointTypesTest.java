/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.analysis;

import org.junit.Test;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

public class MidpointTypesTest {

   private final MidpointTypes midpointType = MidpointTypes.QUARTER;

   @Test
   public void getMidpointForId() {
      assertEquals(MidpointTypes.SIXTEENTH, midpointType.getMidpointForId(4));
   }

   @Test
   public void getId() {
      assertEquals(2, midpointType.getId());
   }

   @Test
   public void getAngle() {
      assertEquals(90.0, midpointType.getAngle(), DELTA_8_POS);
   }

   @Test
   public void getFullRbId() {
      assertEquals("midpoints.quarter", midpointType.getFullRbId());
   }

   @Test
   public void total() {
      assertEquals(4, MidpointTypes.values().length);
   }
}