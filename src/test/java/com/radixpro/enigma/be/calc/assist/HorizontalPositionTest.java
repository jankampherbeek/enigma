/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.calc.assist;

import com.radixpro.enigma.be.calc.core.SeFrontend;
import com.radixpro.enigma.xchg.domain.Location;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HorizontalPositionTest {

   private final double geoLat = 50.0;
   private final double geoLon = -100.0;
   private final double[] horPositions = {3.3, 4.4};
   private final double delta = 0.00000001;
   @Mock
   private SeFrontend seFrontendMock;
   @Mock
   private Location locationMock;
   private HorizontalPosition horizontalPosition;

   @Before
   public void setUp() {
      when(seFrontendMock.getHorizontalPosition(anyDouble(), any(), any(),
            anyInt())).thenReturn(horPositions);
      double[] eclipticalCoordinates = new double[]{100.1, 2.1, 3.1};
      int flags = 0;
      double jdUt = 123456.789;
      horizontalPosition = new HorizontalPosition(seFrontendMock, jdUt, eclipticalCoordinates,
            locationMock, flags);
   }

   @Test
   public void getAzimuth() {
      assertEquals(3.3, horizontalPosition.getAzimuth(), delta);
   }

   @Test
   public void getAltitude() {
      assertEquals(4.4, horizontalPosition.getAltitude(), delta);
   }
}