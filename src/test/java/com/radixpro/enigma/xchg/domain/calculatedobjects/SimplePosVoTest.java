/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.calculatedobjects;

import com.radixpro.enigma.xchg.domain.CelestialObjects;
import com.radixpro.enigma.xchg.domain.IChartPoints;
import org.junit.Before;
import org.junit.Test;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

public class SimplePosVoTest {

   private final IChartPoints point = CelestialObjects.MERCURY;
   private final double longitude = 123.456;
   private final double latitude = -1.1;
   private final double rightAscension = 124.888;
   private final double declination = 20.0;
   private SimplePosVo vo;

   @Before
   public void setUp() {
      vo = new SimplePosVo(point, longitude, latitude, rightAscension, declination);
   }

   @Test
   public void getPoint() {
      assertEquals(point, vo.getPoint());
   }

   @Test
   public void getLongitude() {
      assertEquals(longitude, vo.getLongitude(), DELTA_8_POS);
   }

   @Test
   public void getLatitude() {
      assertEquals(latitude, vo.getLatitude(), DELTA_8_POS);
   }

   @Test
   public void getRightAscension() {
      assertEquals(rightAscension, vo.getRightAscension(), DELTA_8_POS);
   }

   @Test
   public void getDeclination() {
      assertEquals(declination, vo.getDeclination(), DELTA_8_POS);
   }
}