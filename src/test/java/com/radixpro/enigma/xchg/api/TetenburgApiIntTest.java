/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.domain.input.DateTimeJulian;
import com.radixpro.enigma.domain.input.Location;
import com.radixpro.enigma.domain.reqresp.TetenburgRequest;
import com.radixpro.enigma.domain.reqresp.TetenburgResponse;
import com.radixpro.enigma.references.TimeZones;
import com.radixpro.enigma.ui.helpers.DateTimeCreator;
import org.junit.Before;
import org.junit.Test;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

public class TetenburgApiIntTest {

   private TetenburgApi api;

   @Before
   public void setUp() {
      api = XchgApiInjector.injectTetenburgApi();
   }

   @Test
   public void calculateCriticalPoint() {
      TetenburgRequest request = new TetenburgRequest(251.1, 1.015277777778, createLocation(), createBirthDateTime(),
            createProgDateTime());
      TetenburgResponse response = api.calculateCriticalPoint(request);
      assertEquals("OK", response.getResultMsg());
      assertEquals(350.130787045016, response.getLongAsc(), DELTA_8_POS);
   }

   private Location createLocation() {
      return new Location(52.2166666667, 6.9);
   }

   private DateTimeJulian createBirthDateTime() {
      return DateTimeCreator.INSTANCE.createDateTimeJulian("1953/1/29", "G", "8:03:30", TimeZones.CET, false, 0.0);
   }

   private DateTimeJulian createProgDateTime() {
      return DateTimeCreator.INSTANCE.createDateTimeJulian("1968/1/26", "G", "22:00:00", TimeZones.CET, false, 0.0);
   }
}