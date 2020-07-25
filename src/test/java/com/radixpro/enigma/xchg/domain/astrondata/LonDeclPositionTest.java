/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.astrondata;

import com.radixpro.enigma.xchg.domain.CelestialObjects;
import com.radixpro.enigma.xchg.domain.IChartPoints;
import org.junit.Before;
import org.junit.Test;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

public class LonDeclPositionTest {

   private final double longitude = 222.2;
   private final double declination = -3.33;
   private final IChartPoints chartPoint = CelestialObjects.SATURN;
   private LonDeclPosition ldPos;

   @Before
   public void setUp() throws Exception {
      ldPos = new LonDeclPosition(chartPoint, longitude, declination);
   }

   @Test
   public void getLongitude() {
      assertEquals(longitude, ldPos.getLongitude(), DELTA_8_POS);
   }

   @Test
   public void getDeclination() {
      assertEquals(declination, ldPos.getDeclination(), DELTA_8_POS);
   }

   @Test
   public void getChartPoint() {
      assertEquals(chartPoint, ldPos.getChartPoint());
   }
}