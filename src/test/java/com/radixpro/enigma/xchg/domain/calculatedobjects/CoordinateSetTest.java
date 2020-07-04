/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.calculatedobjects;

import org.junit.Before;
import org.junit.Test;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

public class CoordinateSetTest {

   private final double mainPos = 122.22;
   private final double deviation = -45.45;
   private final double mainPosLarge = 370.0;
   private final double deviationSmall = -100.0;
   private CoordinateSet coordinateSet;

   @Before
   public void setUp() throws Exception {
      coordinateSet = new CoordinateSet(mainPos, deviation);
   }

   @Test
   public void getMainPos() {
      assertEquals(mainPos, coordinateSet.getMainPos(), DELTA_8_POS);
   }

   @Test
   public void getDeviation() {
      assertEquals(deviation, coordinateSet.getDeviation(), DELTA_8_POS);
   }

   @Test(expected = IllegalArgumentException.class)
   public void setMainPosLarge() {
      coordinateSet = new CoordinateSet(mainPosLarge, deviation);
   }

   @Test(expected = IllegalArgumentException.class)
   public void setDeviationSmall() {
      coordinateSet = new CoordinateSet(mainPos, deviationSmall);
   }

}