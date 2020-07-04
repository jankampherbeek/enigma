/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.xchg.api.factories.ApiProgFactory;
import com.radixpro.enigma.xchg.api.requests.ProgAnalyzeRequest;
import com.radixpro.enigma.xchg.api.requests.SecundaryCalcRequest;
import com.radixpro.enigma.xchg.api.responses.EphProgAspectResponse;
import com.radixpro.enigma.xchg.api.responses.SimpleProgResponse;
import com.radixpro.enigma.xchg.api.settings.ICalcSettings;
import com.radixpro.enigma.xchg.api.settings.ProgSettings;
import com.radixpro.enigma.xchg.domain.*;
import com.radixpro.enigma.xchg.domain.analysis.*;
import com.radixpro.enigma.xchg.domain.calculatedcharts.ChartPositionsVo;
import com.radixpro.enigma.xchg.domain.calculatedobjects.SimplePosVo;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

public class SecundaryApiTest {

   private SecundaryApi api;

   @Before
   public void setUp() throws Exception {
      api = new ApiProgFactory().createSecundaryApi();
   }

   @Test
   public void calculateSecundary() {
      SecundaryCalcRequest request = createCalcRequest();
      final SimpleProgResponse response = api.calculateSecundary(request);
      assertEquals(3, response.getPositions().size());
      List<SimplePosVo> positions = response.getPositions();
      assertEquals(CelestialObjects.SUN, positions.get(0).getPoint());
      // Difference in Julian days is 7305
      // Divide by length of tropical year : 365.24219 gives 20.0004276614
      // which is 20 days 0 h 0 m and 37 sec  --> 2000/6/26 13:42:37  CET
      // Positions (no parallax):
      // Sun 5d12m10s CN    decl 23d20m07s N
      // Moon 23d24m52s AR
      // Mars 06d41m21s CN


      assertEquals(95.20299984447792, positions.get(0).getLongitude(), DELTA_8_POS);
      assertEquals(23.3354796103748, positions.get(0).getDeclination(), DELTA_8_POS);
      assertEquals(23.414673045168932, positions.get(1).getLongitude(), DELTA_8_POS);
      assertEquals(96.68935276274844, positions.get(2).getLongitude(), DELTA_8_POS);
   }

   @Test
   public void defineAspects() {
      SecundaryCalcRequest request = createCalcRequest();
      final SimpleProgResponse response = api.calculateSecundary(request);
      List<SimplePosVo> progPositions = response.getPositions();
      final EphProgAspectResponse ephProgAspectResponse = api.defineAspects(createAnalyzeRequest(progPositions));
      assertEquals(1, ephProgAspectResponse.getAnalyzedAspects().size());
      IAnalyzedPair aspect = ephProgAspectResponse.getAnalyzedAspects().get(0);
      assertEquals(CelestialObjects.MOON, aspect.getFirst().getChartPoint());
      assertEquals(CelestialObjects.MARS, aspect.getSecond().getChartPoint());
   }

   private SecundaryCalcRequest createCalcRequest() {
      SimpleDate date = new SimpleDate(2020, 6, 6, true);
      final SimpleTime time = new SimpleTime(13, 42, 0);
      SimpleDateTime dateTime = new SimpleDateTime(date, time);
      final FullDateTime eventDateTime = new FullDateTime(dateTime, TimeZones.CET, false, 0.0);

      date = new SimpleDate(2000, 6, 6, true);
      dateTime = new SimpleDateTime(date, time);
      final FullDateTime birthDateTime = new FullDateTime(dateTime, TimeZones.CET, false, 0.0);

      final GeographicCoordinate latInput = new GeographicCoordinate(52, 13, 0, "n", 52.216666666666667);
      final GeographicCoordinate longinput = new GeographicCoordinate(6, 54, 0, "e", 6.9);
      Location location = new Location(longinput, latInput, "Enschede");
      List<IChartPoints> points = new ArrayList<>();
      points.add(CelestialObjects.SUN);
      points.add(CelestialObjects.MOON);
      points.add(CelestialObjects.MARS);
      final ICalcSettings settings = new ProgSettings(points, Ayanamshas.NONE, false, false);
      return new SecundaryCalcRequest(eventDateTime, birthDateTime, location, settings);
   }

   private ProgAnalyzeRequest createAnalyzeRequest(final List<SimplePosVo> progPositions) {
      List<AspectTypes> aspects = new ArrayList<>();
      aspects.add(AspectTypes.CONJUNCTION);
      aspects.add(AspectTypes.OPPOSITION);
      aspects.add(AspectTypes.TRIANGLE);
      aspects.add(AspectTypes.SQUARE);
      aspects.add(AspectTypes.SEXTILE);
      List<SimplePosVo> celestialPositions = new ArrayList<>();
      celestialPositions.add(new SimplePosVo(CelestialObjects.SUN, 351.8, 1.0, 352.0, -8.8));
      celestialPositions.add(new SimplePosVo(CelestialObjects.MARS, 21.5, 0.7, 22.0, 4.4));
      celestialPositions.add(new SimplePosVo(CelestialObjects.URANUS, 102.7, 0.3, 103.0, 4.0));
      List<SimplePosVo> mundanePositions = new ArrayList<>();
      mundanePositions.add(new SimplePosVo(MundanePoints.ASC, 162.5, 0.0, 162.0, 20.0));
      ChartPositionsVo chartPositions = new ChartPositionsVo(1L, celestialPositions, mundanePositions);

      return new ProgAnalyzeRequest(ProgAnalysisType.ASPECTS, progPositions, chartPositions, aspects, 2.0);
   }

}