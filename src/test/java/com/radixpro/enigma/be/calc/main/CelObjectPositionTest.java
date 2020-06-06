/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.calc.main;

import com.radixpro.enigma.be.calc.assist.SePositionResultCelObjects;
import com.radixpro.enigma.be.calc.core.SeFrontend;
import com.radixpro.enigma.xchg.domain.CelestialObjects;
import com.radixpro.enigma.xchg.domain.Location;
import com.radixpro.enigma.xchg.domain.SeFlags;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CelObjectPositionTest {

   private final CelestialObjects celBodyToCalc = CelestialObjects.MERCURY;
   private final double geoLat = 52.0;
   private final double geoLong = 7.0;
   @Mock
   private SeFrontend seFrontendMock;
   @Mock
   private Location locationMock;
   @Mock
   private SePositionResultCelObjects sePositionResultMock;
   private CelObjectPosition celObjectPosition;

   @Before
   public void setUp() {
      when(sePositionResultMock.getErrorMsg()).thenReturn("errormsg");
      when(sePositionResultMock.getAllPositions()).thenReturn(new double[]{1.1, 1.2, 1.3, 1.4, 1.5, 1.6});
      when(seFrontendMock.getPositionsForCelBody(anyDouble(), anyInt(), anyInt(), any())).thenReturn(sePositionResultMock);
      when(seFrontendMock.getHorizontalPosition(anyDouble(), any(), any(),
            anyInt())).thenReturn(new double[]{3.4, 5.6});
      ArrayList<SeFlags> flagList = new ArrayList<>();
      flagList.add(SeFlags.SWISSEPH);
      Double jdUt = 1234567.891;
      celObjectPosition = new CelObjectPosition(seFrontendMock, jdUt, celBodyToCalc, locationMock, flagList);
   }

   @Test
   public void getEclipticalPosition() {
      assertNotNull(celObjectPosition.getEclipticalPosition());
   }

   @Test
   public void getEquatorialPosition() {
      assertNotNull(celObjectPosition.getEquatorialPosition());
   }

   @Test
   public void getHorizontalPosition() {
      assertNotNull(celObjectPosition.getHorizontalPosition());
   }

   @Test
   public void getCelestialBody() {
      assertEquals(celBodyToCalc, celObjectPosition.getCelestialBody());
   }
}