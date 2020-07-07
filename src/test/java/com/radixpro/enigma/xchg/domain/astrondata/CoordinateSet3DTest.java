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

public class CoordinateSet3DTest {

   private final double distance = 5.55;
   private CoordinateSet3D coordinateSet3D;

   @Before
   public void setUp() {
      double mainCoord = 234.567;
      double deviation = -33.33;
      coordinateSet3D = new CoordinateSet3D(mainCoord, deviation, distance);
   }

   @Test
   public void getDistance() {
      assertEquals(distance, coordinateSet3D.getDistance(), DELTA_8_POS);
   }

}