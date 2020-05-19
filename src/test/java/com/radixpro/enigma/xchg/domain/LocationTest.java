/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LocationTest {

   private static final double DELTA = 0.00000001;
   private final String name = "Location Name";
   private GeographicCoordinate longitudeGeoCoord;
   private GeographicCoordinate latitudeGeoCoord;
   private Location location;

   @Before
   public void setUp() throws Exception {
      longitudeGeoCoord = new GeographicCoordinate(122, 33, 44, "w", 122.56222222222222);
      latitudeGeoCoord = new GeographicCoordinate(18, 19, 20, "s", 18.32222222222222);
      location = new Location(longitudeGeoCoord, latitudeGeoCoord, name);
   }

   @Test
   public void getGeoLat() {
      assertEquals(18.32222222222, location.getGeoLat(), DELTA);
   }

   @Test
   public void getGeoLong() {
      assertEquals(122.562222222222, location.getGeoLong(), DELTA);
   }

   @Test
   public void getName() {
      assertEquals(name, location.getName());
   }

   @Test
   public void getLongInput() {
      assertEquals(longitudeGeoCoord, location.getLongInput());
   }

   @Test
   public void getLatInput() {
      assertEquals(latitudeGeoCoord, location.getLatInput());
   }

   @Test
   public void getFormattedLocation() {
      assertEquals("Location Name / 122:33:44 w / 18:19:20 s", location.getFormattedLocation());
   }
}
