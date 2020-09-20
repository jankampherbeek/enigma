/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.calc.core;

import com.radixpro.enigma.be.calc.SeFrontend;
import com.radixpro.enigma.be.calc.assist.SePositionResultCelObjects;
import com.radixpro.enigma.be.calc.assist.SePositionResultHouses;
import com.radixpro.enigma.domain.input.Location;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SeFrontendTest {

   private final double delta = 0.00000001;
   private final double jdUt = 1234567.89;
   private final int flags = 1;

   private Location location;

   @Before
   public void setUp() {
      location = new Location(52.0, 7.0);
   }

   @Test
   public void getFrontend() {
      SeFrontend seFrontend = SeFrontend.INSTANCE;
      assertNotNull(seFrontend);
   }

   @Test
   public void getPositionsForCelBody() {
      int bodyId = 4;
      SePositionResultCelObjects result = SeFrontend.INSTANCE.getPositionsForCelBody(jdUt, bodyId, flags, location);
      assertEquals(148.08135699695939, result.getAllPositions()[0], delta);
   }

   @Test
   public void getPositionsForHouses() {
      int nrOfCusps = 12;
      char system = 'p';
      SePositionResultHouses result = SeFrontend.INSTANCE.getPositionsForHouses(jdUt, flags, location,
            system, nrOfCusps);
      assertEquals(59.97963584631173, result.getCusps()[3], delta);
      assertEquals(258.18944437108246, result.getAscMc()[2], delta);
   }

   @Test
   public void getHorizontalPosition() {
      int horFlags = 0;    // ecliptical
      double[] eclipticalCoordinates = new double[]{22.2, 2.2, 5.2};
      double[] result = SeFrontend.INSTANCE.getHorizontalPosition(jdUt, eclipticalCoordinates,
            location, horFlags);
      assertEquals(238.22139830075912, result[0], delta);
      assertEquals(-9.634283826590398, result[1], delta);

   }


   @Test
   public void isValidDateHappyFlow() {
      var seFrontend = SeFrontend.INSTANCE;
      assertTrue(seFrontend.isValidDate(1953, 1, 29, true));
   }

   @Test
   public void isValidDateWrongMonth() {
      var seFrontend = SeFrontend.INSTANCE;
      assertFalse(seFrontend.isValidDate(1953, 15, 29, true));
   }

   @Test
   public void isValidDateWrongDay() {
      var seFrontend = SeFrontend.INSTANCE;
      assertFalse(seFrontend.isValidDate(1953, 15, -29, true));
   }

   @Test
   public void isValidDateJulian() {
      var seFrontend = SeFrontend.INSTANCE;
      assertTrue(seFrontend.isValidDate(653, 1, 29, false));
   }

   @Test
   public void isValidDateValidLeapDay() {
      var seFrontend = SeFrontend.INSTANCE;
      assertTrue(seFrontend.isValidDate(1952, 2, 29, true));
   }

   @Test
   public void isValidDateWrongLeapDay() {
      var seFrontend = SeFrontend.INSTANCE;
      assertFalse(seFrontend.isValidDate(1953, 2, 29, true));
   }


}