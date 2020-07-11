/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.xchg.api.factories.ApiProgFactory;
import com.radixpro.enigma.xchg.api.requests.SolarReturnRequest;
import com.radixpro.enigma.xchg.api.responses.SolarReturnResponse;
import com.radixpro.enigma.xchg.domain.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_5_POS;
import static org.junit.Assert.assertEquals;

public class SolarReturnApiIntTest {

   private SolarReturnApi api;

   @Before
   public void setUp() {
      api = new ApiProgFactory().getSolarReturnApi();
   }

   @Test
   public void calculateSolarReturn() {
      final SolarReturnRequest request = new SolarReturnRequest(createFullDateTime(), createSettings(), createLocation(), 309.11833333, 2020);
      final SolarReturnResponse response = api.calculateSolarReturn(request);
      assertEquals("OK", response.getResultMsg());
      FullChartDepr responseChart = response.getSolarReturnChart();
      assertEquals(309.11833333, responseChart.getBodies().get(0).getEclipticalPosition().getMainPosition(), DELTA_5_POS);
   }

   private FullDateTime createFullDateTime() {
      SimpleDate date = new SimpleDate(1953, 1, 29, true);
      SimpleTime time = new SimpleTime(8, 37, 30);
      SimpleDateTime dateTime = new SimpleDateTime(date, time);
      return new FullDateTime(dateTime, TimeZones.CET, false, 0.0);
   }

   private Location createLocation() {
      GeographicCoordinate geoLat = new GeographicCoordinate(52, 13, 0, "n", 52.21666666667);
      GeographicCoordinate geoLong = new GeographicCoordinate(6, 54, 0, "e", 6.9);
      return new Location(geoLat, geoLong, "Enschede");
   }

   private CalculationSettings createSettings() {
      List<CelestialObjects> celPoints = new ArrayList<>();
      celPoints.add(CelestialObjects.SUN);
      celPoints.add(CelestialObjects.MOON);
      celPoints.add(CelestialObjects.MERCURY);
      celPoints.add(CelestialObjects.VENUS);
      return new CalculationSettings(celPoints, HouseSystems.PLACIDUS, Ayanamshas.NONE, false, false);
   }


}