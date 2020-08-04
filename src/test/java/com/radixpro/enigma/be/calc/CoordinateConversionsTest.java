/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc;

import com.radixpro.enigma.xchg.domain.Location;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CoordinateConversionsTest {

   private final double jdUt = 123456.789;
   private final double[] eclipticalCoordinates = new double[]{100.1, 2.1, 3.1};
   @Mock
   private Location locationMock;

   @Before
   public void setUp() {
      int flags = 0;
   }

   @Test
   public void getAzimuth() {
      assertEquals(116.98747217272705, CoordinateConversions.eclipticToHorizontal(jdUt, eclipticalCoordinates, locationMock)[0], DELTA_8_POS);
   }

   @Test
   public void getAltitude() {
      assertEquals(16.26345612775, CoordinateConversions.eclipticToHorizontal(jdUt, eclipticalCoordinates, locationMock)[1], DELTA_8_POS);
   }

}