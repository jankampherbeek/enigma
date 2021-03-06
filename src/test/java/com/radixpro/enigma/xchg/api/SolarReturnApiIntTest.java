/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.references.*;
import com.radixpro.enigma.xchg.api.settings.ChartCalcSettings;
import com.radixpro.enigma.xchg.domain.IChartPoints;

import java.util.ArrayList;
import java.util.List;

public class SolarReturnApiIntTest {

   private SolarReturnApi api;

   // TODO fix and enable integration tests for SolarReturnApi

//   @Before
//   public void setUp() {
//      api = XchgApiInjector.injectSolarReturnApi(new AppScope());
//   }
//
//   @Test
//   public void calculateSolarReturn() {
//      final SolarReturnRequest request = new SolarReturnRequest(createFullDateTime(), createSettings(), createLocation(), 309.11833333, 2020);
//      final SolarReturnResponse response = api.calculateSolarReturn(request);
//      assertEquals("OK", response.getResultMsg());
//      CalculatedChart responseChart = response.getSolarReturnChart();
//      assertEquals(309.11833333, responseChart.getCelPoints().get(0).getLongitude(), DELTA_5_POS);
//   }


   private ChartCalcSettings createSettings() {
      List<IChartPoints> celPoints = new ArrayList<>();
      celPoints.add(CelestialObjects.SUN);
      celPoints.add(CelestialObjects.MOON);
      celPoints.add(CelestialObjects.MERCURY);
      celPoints.add(CelestialObjects.VENUS);
      return new ChartCalcSettings(celPoints, ObserverPositions.GEOCENTRIC, EclipticProjections.TROPICAL, Ayanamshas.NONE, HouseSystems.CAMPANUS);
   }


}