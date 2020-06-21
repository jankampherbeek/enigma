/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.requests;

import com.radixpro.enigma.xchg.api.settings.CalcSettingsFake;
import com.radixpro.enigma.xchg.api.settings.ICalcSettings;
import com.radixpro.enigma.xchg.domain.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SecundaryCalcRequestTest {

   private FullDateTime dateTime;
   private FullDateTime birthDateTime;
   private Location location;
   private ICalcSettings settings;
   private SecundaryCalcRequest request;

   @Before
   public void setUp() throws Exception {
      dateTime = createDateTime(2020);
      birthDateTime = createDateTime(1953);
      location = createLocation();
      settings = new CalcSettingsFake();
      request = new SecundaryCalcRequest(dateTime, birthDateTime, location, settings);
   }

   @Test
   public void getDateTime() {
      assertEquals(dateTime, request.getDateTime());
   }

   @Test
   public void getBirthDateTime() {
      assertEquals(birthDateTime, request.getBirthDateTime());
   }

   @Test
   public void getSettings() {
      assertEquals(settings, request.getSettings());
   }

   @Test
   public void getLocation() {
      assertEquals(location, request.getLocation());
   }

   private FullDateTime createDateTime(final int year) {
      SimpleDate date = new SimpleDate(year, 6, 5, true);
      SimpleTime time = new SimpleTime(21, 43, 00);
      SimpleDateTime dateTime = new SimpleDateTime(date, time);
      return new FullDateTime(dateTime, TimeZones.CET, true, 0.0);
   }

   private Location createLocation() {
      GeographicCoordinate longInput = new GeographicCoordinate(6, 54, 0, "e", 6.9);
      GeographicCoordinate latInput = new GeographicCoordinate(52, 30, 0, "n", 52.5);
      return new Location(longInput, latInput, "Somewhere");
   }
}