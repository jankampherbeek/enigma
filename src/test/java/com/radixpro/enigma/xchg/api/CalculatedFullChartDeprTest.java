/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.xchg.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CalculatedFullChartDeprTest {

   @Mock
   private Location locationMock;
   @Mock
   private FullDateTime fullDateTimeMock;
   //   @Mock
//   private SimpleDate simpleDateMock;
//   @Mock
//   private SimpleTime simpleTimeMock;
   @Mock
   private CalculationSettings settingsMock;
   private FullChartDepr fullChartDepr;

   @Before
   public void setUp() {
      final List<CelestialObjects> bodies = new ArrayList<>();
      bodies.add(CelestialObjects.SUN);
      bodies.add(CelestialObjects.MOON);
      when(locationMock.getGeoLat()).thenReturn(44.4);
      when(locationMock.getGeoLong()).thenReturn(22.2);
      when(settingsMock.isTopocentric()).thenReturn(true);
      when(settingsMock.getHouseSystem()).thenReturn(HouseSystems.PLACIDUS);
      when(settingsMock.getCelBodies()).thenReturn(bodies);
      fullChartDepr = new FullChartDepr(fullDateTimeMock, locationMock, settingsMock);
   }

   @Test
   public void getBodies() {
      assertEquals(2, fullChartDepr.getBodies().size());
   }

   @Test
   public void getHouseValues() {
      assertNotNull(fullChartDepr.getMundaneValues());
   }

   @Test
   public void getSettings() {
      assertEquals(settingsMock, fullChartDepr.getSettings());
   }

   @Test
   public void getDateTime() {
      assertEquals(fullDateTimeMock, fullChartDepr.getFullDateTime());
   }

   @Test
   public void getLocation() {
      assertEquals(locationMock, fullChartDepr.getLocation());
   }
}