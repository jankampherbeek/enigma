/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.calc.core;

import com.radixpro.enigma.be.calc.assist.SePositionResultCelObjects;
import com.radixpro.enigma.be.calc.assist.SePositionResultHouses;
import com.radixpro.enigma.xchg.domain.CelObjectSinglePosition;
import com.radixpro.enigma.xchg.domain.Location;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SeFrontendTest {

   private final double delta = 0.00000001;
   private final double jdUt = 1234567.89;
   private final int flags = 1;
   @Mock
   private CelObjectSinglePosition celObjectSinglePositionMock;
   @Mock
   private Location locationMock;

   @Before
   public void setUp() {
      double geoLat = 52.0;
      when(locationMock.getGeoLat()).thenReturn(geoLat);
      double geoLong = 7.0;
      when(locationMock.getGeoLong()).thenReturn(geoLong);
   }

   @Test
   public void getFrontend() {
      SeFrontend seFrontend = SeFrontend.getFrontend();
      assertNotNull(seFrontend);
   }

   @Test
   public void getJulianDay() {
      double[] result = SeFrontend.getFrontend().getJulianDay(1953, 1, 29, 7, 37, 0, true);
      assertEquals(2434406.8177128294, result[0], delta);
      assertEquals(2434406.8173611113, result[1], delta);
   }


   @Test
   public void getPositionsForCelBody() {
      int bodyId = 4;
      SePositionResultCelObjects result = SeFrontend.getFrontend().getPositionsForCelBody(jdUt, bodyId, flags, locationMock);
      assertEquals(148.08135699695939, result.getAllPositions()[0], delta);
   }

   @Test
   public void getPositionsForHouses() {
      int nrOfCusps = 12;
      char system = 'p';
      SePositionResultHouses result = SeFrontend.getFrontend().getPositionsForHouses(jdUt, flags, locationMock,
            system, nrOfCusps);
      assertEquals(59.97963584631173, result.getCusps()[3], delta);
      assertEquals(258.18944437108246, result.getAscMc()[2], delta);
   }

   @Test
   public void getHorizontalPosition() {
      int horFlags = 0;    // ecliptical
      double[] eclipticalCoordinates = new double[]{22.2, 2.2, 5.2};
      double[] result = SeFrontend.getFrontend().getHorizontalPosition(jdUt, eclipticalCoordinates,
            locationMock, horFlags);
      assertEquals(238.22139830075912, result[0], delta);
      assertEquals(-9.634283826590398, result[1], delta);

   }


   @Test
   public void isValidDateHappyFlow() {
      var seFrontend = SeFrontend.getFrontend();
      assertTrue(seFrontend.isValidDate(1953, 1, 29, true));
   }

   @Test
   public void isValidDateWrongMonth() {
      var seFrontend = SeFrontend.getFrontend();
      assertFalse(seFrontend.isValidDate(1953, 15, 29, true));
   }

   @Test
   public void isValidDateWrongDay() {
      var seFrontend = SeFrontend.getFrontend();
      assertFalse(seFrontend.isValidDate(1953, 15, -29, true));
   }

   @Test
   public void isValidDateJulian() {
      var seFrontend = SeFrontend.getFrontend();
      assertTrue(seFrontend.isValidDate(653, 1, 29, false));
   }

   @Test
   public void isValidDateValidLeapDay() {
      var seFrontend = SeFrontend.getFrontend();
      assertTrue(seFrontend.isValidDate(1952, 2, 29, true));
   }

   @Test
   public void isValidDateWrongLeapDay() {
      var seFrontend = SeFrontend.getFrontend();
      assertFalse(seFrontend.isValidDate(1953, 2, 29, true));
   }


}