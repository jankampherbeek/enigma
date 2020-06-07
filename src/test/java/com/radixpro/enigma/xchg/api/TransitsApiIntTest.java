/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.xchg.api.factories.ApiProgFactory;
import com.radixpro.enigma.xchg.api.requests.TransitCalcRequest;
import com.radixpro.enigma.xchg.api.requests.TransitsAnalyzeRequest;
import com.radixpro.enigma.xchg.api.responses.SimpleProgResponse;
import com.radixpro.enigma.xchg.api.responses.TransitsAspectResponse;
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

import static com.radixpro.enigma.testsupport.TextConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

public class TransitsApiIntTest {

   private TransitsApi api;


   @Before
   public void setUp() {
      api = new ApiProgFactory().createTransitsApi();
   }

   @Test
   public void calculateTransits() {
      TransitCalcRequest request = createCalcRequest();
      final SimpleProgResponse response = api.calculateTransits(request);
      assertEquals(request, response.getRequest());
      assertEquals(3, response.getPositions().size());
      List<SimplePosVo> positions = response.getPositions();
      assertEquals(CelestialObjects.SUN, positions.get(0).getPoint());
      assertEquals(76.22484918221, positions.get(0).getLongitude(), DELTA_8_POS);
      assertEquals(22.724047787544, positions.get(0).getDeclination(), DELTA_8_POS);
      assertEquals(CelestialObjects.MARS, positions.get(1).getPoint());
      assertEquals(346.31867961823, positions.get(1).getLongitude(), DELTA_8_POS);
      assertEquals(-7.714613590212, positions.get(1).getDeclination(), DELTA_8_POS);
      assertEquals(CelestialObjects.URANUS, positions.get(2).getPoint());
      assertEquals(38.83133542694, positions.get(2).getLongitude(), DELTA_8_POS);
      assertEquals(14.017602186382, positions.get(2).getDeclination(), DELTA_8_POS);
   }

   @Test
   public void findAspects() {
      final TransitsAspectResponse response = api.defineAspects(createAnalyzeRequest());
      assertEquals(4, response.getAnalyzedAspects().size());
      AnalyzedAspectTransit analyzedAspect = (AnalyzedAspectTransit) response.getAnalyzedAspects().get(0);
      assertEquals(CelestialObjects.MARS, analyzedAspect.getFirst().getChartPoint());
      assertEquals(CelestialObjects.SUN, analyzedAspect.getSecond().getChartPoint());
      assertEquals(AspectTypes.TRIANGLE, analyzedAspect.getAspectType());
      assertEquals(0.2, analyzedAspect.getActualOrb(), DELTA_8_POS);
      assertEquals(1.0, analyzedAspect.getMaxOrb(), DELTA_8_POS);
      assertEquals(20.0, analyzedAspect.getPercOrb(), DELTA_8_POS);
   }

   private TransitCalcRequest createCalcRequest() {
      final SimpleDate date = new SimpleDate(2020, 6, 6, true);
      final SimpleTime time = new SimpleTime(13, 42, 0);
      final SimpleDateTime dateTime = new SimpleDateTime(date, time);
      final FullDateTime fullDateTime = new FullDateTime(dateTime, TimeZones.CET, true, 0.0);
      final GeographicCoordinate latInput = new GeographicCoordinate(52, 13, 0, "n", 52.216666666666667);
      final GeographicCoordinate longinput = new GeographicCoordinate(6, 54, 0, "e", 6.9);
      Location location = new Location(longinput, latInput, "Enschede");
      List<IChartPoints> points = new ArrayList<>();
      points.add(CelestialObjects.SUN);
      points.add(CelestialObjects.MARS);
      points.add(CelestialObjects.URANUS);
      final ICalcSettings settings = new ProgSettings(points, Ayanamshas.NONE, false, false);
      return new TransitCalcRequest(fullDateTime, location, settings);
   }

   private TransitsAnalyzeRequest createAnalyzeRequest() {
      List<SimplePosVo> transitPositions = new ArrayList<>();
      transitPositions.add(new SimplePosVo(CelestialObjects.MARS, 112.0, 1.0, 111.0, 5.5));    // 120 Sun 90 Mars
      transitPositions.add(new SimplePosVo(CelestialObjects.JUPITER, 274.5, -2.0, 275.0, -10.0));
      transitPositions.add(new SimplePosVo(CelestialObjects.SATURN, 163.0, 0.5, 164.0, 2.2));  // 60 Ura 0 Asc
      List<SimplePosVo> celestialPositions = new ArrayList<>();
      celestialPositions.add(new SimplePosVo(CelestialObjects.SUN, 351.8, 1.0, 352.0, -8.8));
      celestialPositions.add(new SimplePosVo(CelestialObjects.MARS, 21.5, 0.7, 22.0, 4.4));
      celestialPositions.add(new SimplePosVo(CelestialObjects.URANUS, 102.7, 0.3, 103.0, 4.0));
      List<SimplePosVo> mundanePositions = new ArrayList<>();
      mundanePositions.add(new SimplePosVo(MundanePoints.ASC, 162.5, 0.0, 162.0, 20.0));
      ChartPositionsVo chartPositions = new ChartPositionsVo(1L, celestialPositions, mundanePositions);
      List<AspectTypes> aspectTypes = new ArrayList<>();
      aspectTypes.add(AspectTypes.CONJUNCTION);
      aspectTypes.add(AspectTypes.SEXTILE);
      aspectTypes.add(AspectTypes.SQUARE);
      aspectTypes.add(AspectTypes.TRIANGLE);
      aspectTypes.add(AspectTypes.OPPOSITION);
      return new TransitsAnalyzeRequest(ProgAnalysisType.ASPECTS, transitPositions, chartPositions, aspectTypes, 1.0);
   }

}