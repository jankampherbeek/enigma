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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LocationTest {

   private static final double DELTA = 0.00000001;
   private final String name = "Location Name";
   @Mock
   private GeographicCoordinate longitudeGeoCoordMock;
   @Mock
   private GeographicCoordinate latitudeGeoCoordMock;
   private Location location;

   @Before
   public void setUp() throws Exception {
      when(longitudeGeoCoordMock.getValue()).thenReturn(40.5);
      when(latitudeGeoCoordMock.getValue()).thenReturn(-5.25);
      location = new Location(longitudeGeoCoordMock, latitudeGeoCoordMock, name);
   }

   @Test
   public void getGeoLat() {
      assertEquals(-5.25, location.getGeoLat(), DELTA);
   }

   @Test
   public void getGeoLong() {
      assertEquals(40.5, location.getGeoLong(), DELTA);
   }

   @Test
   public void getName() {
      assertEquals(name, location.getName());
   }

   @Test
   public void getLongInput() {
      assertEquals(longitudeGeoCoordMock, location.getLongInput());
   }

   @Test
   public void getLatInput() {
      assertEquals(latitudeGeoCoordMock, location.getLatInput());
   }
}
