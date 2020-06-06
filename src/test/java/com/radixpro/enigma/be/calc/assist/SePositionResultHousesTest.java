/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.calc.assist;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SePositionResultHousesTest {

   private final double[] cusps = {0.0, 1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 7.7, 8.8, 9.9, 10.10, 11.11, 12.12};
   private final double[] ascMc = {1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 7.7, 8.8, 0.0, 0.0};
   private SePositionResultHouses sePositionResultHouses;

   @Before
   public void setUp() {
      sePositionResultHouses = new SePositionResultHouses(ascMc, cusps);
   }

   @Test
   public void getAscMc() {
      assertEquals(ascMc, sePositionResultHouses.getAscMc());
   }

   @Test
   public void getCusps() {
      assertEquals(cusps, sePositionResultHouses.getCusps());
   }
}