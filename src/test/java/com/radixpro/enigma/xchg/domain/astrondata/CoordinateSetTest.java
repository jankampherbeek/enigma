/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.astrondata;

import org.junit.Before;
import org.junit.Test;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

public class CoordinateSetTest {

   private final double mainCoord = 234.567;
   private final double deviation = -33.33;
   private CoordinateSet coordinateSet;

   @Before
   public void setUp() {
      coordinateSet = new CoordinateSet(mainCoord, deviation);
   }

   @Test
   public void getMainCoord() {
      assertEquals(mainCoord, coordinateSet.getMainCoord(), DELTA_8_POS);
   }

   @Test
   public void getDeviation() {
      assertEquals(deviation, coordinateSet.getDeviation(), DELTA_8_POS);
   }
}