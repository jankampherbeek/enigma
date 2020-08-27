/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.helpers;

import com.radixpro.enigma.domain.input.Location;
import org.junit.Before;
import org.junit.Test;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

public class LocationCreatorOldTest {

   private LocationCreator creator;

   @Before
   public void setUp() throws Exception {
      creator = new LocationCreator();
   }

   @Test
   public void createLocationHappyFlow() {
      Location location = creator.createLocation(50, 30, 0, "N", 70, 15, 0, "w");
      assertEquals(50.5, location.getGeoLat(), DELTA_8_POS);
      assertEquals(-70.25, location.getGeoLon(), DELTA_8_POS);
   }

   @Test(expected = IllegalArgumentException.class)
   public void createLocationWrongInput() {
      creator.createLocation(50, 100, 0, "N", 70, 15, 0, "w");
   }

   @Test(expected = IllegalArgumentException.class)
   public void createLocation180Overflow() {
      creator.createLocation(50, 30, 0, "N", 180, 15, 0, "w");
   }


}