/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FullChartDeprTest {

   @Mock
   private FullDateTime fullDateTimeMock;
   @Mock
   private SimpleDate simpleDateMock;
   @Mock
   private SimpleTime simpleTimeMock;
   @Mock
   private Location locationMock;
   @Mock
   private CalculationSettings settingsMock;

   private FullChartDepr fullChartDepr;

   @Before
   public void setUp() {
      List<CelestialObjects> celBodies = new ArrayList<>();
      celBodies.add(CelestialObjects.SUN);
      celBodies.add(CelestialObjects.MOON);
      when(settingsMock.isTopocentric()).thenReturn(false);
      when(settingsMock.isSidereal()).thenReturn(true);
      when(settingsMock.getHouseSystem()).thenReturn(HouseSystems.AXIAL);
      when(settingsMock.getCelBodies()).thenReturn(celBodies);
      fullChartDepr = new FullChartDepr(fullDateTimeMock, locationMock, settingsMock);
   }

   @Test
   public void getHouseValues() {
      assertNotNull(fullChartDepr.getMundaneValues());
   }

   @Test
   public void getBodies() {
      assertEquals(2, fullChartDepr.getBodies().size());
   }

   @Test
   public void getDateTime() {
      assertEquals(fullDateTimeMock, fullChartDepr.getFullDateTime());
   }

   @Test
   public void getLocation() {
      assertEquals(locationMock, fullChartDepr.getLocation());
   }

   @Test
   public void getSettings() {
      assertEquals(settingsMock, fullChartDepr.getSettings());
   }

   @Test
   public void getObliquity() {
      assertTrue(fullChartDepr.getObliquity() > 0.0);
   }


}