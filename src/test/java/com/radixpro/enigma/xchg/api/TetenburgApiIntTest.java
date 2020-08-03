/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.AppScope;
import com.radixpro.enigma.xchg.api.requests.TetenburgRequest;
import com.radixpro.enigma.xchg.api.responses.TetenburgResponse;
import com.radixpro.enigma.xchg.domain.*;
import org.junit.Before;
import org.junit.Test;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

public class TetenburgApiIntTest {

   private TetenburgApi api;

   @Before
   public void setUp() throws Exception {
      api = XchgApiInjector.injectTetenburgApi(new AppScope());
   }

   @Test
   public void calculateCriticalPoint() {
      TetenburgRequest request = new TetenburgRequest(251.1, 1.015277777778, createLocation(), createBirthDateTime(), createProgDateTime());
      TetenburgResponse response = api.calculateCriticalPoint(request);
      assertEquals("OK", response.getResultMsg());
      assertEquals(350.130787045016, response.getLongAsc(), DELTA_8_POS);
   }

   private Location createLocation() {
      GeographicCoordinate geoLong = new GeographicCoordinate(6, 54, 0, "e", 6.9);
      GeographicCoordinate geoLat = new GeographicCoordinate(52, 13, 0, "n", 52.216666666677);
      return new Location(geoLong, geoLat, "Enschede");
   }

   private FullDateTime createBirthDateTime() {
      SimpleDate date = new SimpleDate(1953, 1, 29, true);
      SimpleTime time = new SimpleTime(8, 3, 30);
      SimpleDateTime dateTime = new SimpleDateTime(date, time);
      return new FullDateTime(dateTime, TimeZones.CET, false, 0.0);
   }

   private FullDateTime createProgDateTime() {
      SimpleDate date = new SimpleDate(1968, 1, 26, true);
      SimpleTime time = new SimpleTime(22, 0, 0);
      SimpleDateTime dateTime = new SimpleDateTime(date, time);
      return new FullDateTime(dateTime, TimeZones.CET, false, 0.0);
   }
}