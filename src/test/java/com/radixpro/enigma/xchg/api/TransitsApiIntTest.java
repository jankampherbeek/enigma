/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.AppScope;
import com.radixpro.enigma.domain.analysis.AnalyzedAspectTransit;
import com.radixpro.enigma.domain.astronpos.*;
import com.radixpro.enigma.domain.datetime.FullDateTime;
import com.radixpro.enigma.domain.datetime.SimpleDate;
import com.radixpro.enigma.domain.datetime.SimpleDateTime;
import com.radixpro.enigma.domain.datetime.SimpleTime;
import com.radixpro.enigma.domain.reqresp.EphProgAspectResponse;
import com.radixpro.enigma.domain.reqresp.EphProgCalcRequest;
import com.radixpro.enigma.domain.reqresp.ProgAnalyzeRequest;
import com.radixpro.enigma.domain.reqresp.SimpleProgResponse;
import com.radixpro.enigma.references.*;
import com.radixpro.enigma.xchg.api.settings.ICalcSettings;
import com.radixpro.enigma.xchg.api.settings.ProgSettings;
import com.radixpro.enigma.xchg.domain.GeographicCoordinate;
import com.radixpro.enigma.xchg.domain.IChartPoints;
import com.radixpro.enigma.xchg.domain.Location;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

public class TransitsApiIntTest {

   private TransitsApi api;


   @Before
   public void setUp() {
      api = XchgApiInjector.injectTransitsApi(new AppScope());
   }

   @Test
   public void calculateTransits() {
      EphProgCalcRequest request = createCalcRequest();
      final SimpleProgResponse response = api.calculateTransits(request);
      assertEquals(request, response.getRequest());
      assertEquals(3, response.getPositions().size());
      List<IPosition> positions = response.getPositions();
      assertEquals(CelestialObjects.SUN, positions.get(0).getChartPoint());
      assertEquals(76.22484918221, positions.get(0).getLongitude(), DELTA_8_POS);
      assertEquals(22.724047787544, positions.get(0).getDeclination(), DELTA_8_POS);
      assertEquals(CelestialObjects.MARS, positions.get(1).getChartPoint());
      assertEquals(346.31867961823, positions.get(1).getLongitude(), DELTA_8_POS);
      assertEquals(-7.714613590212, positions.get(1).getDeclination(), DELTA_8_POS);
      assertEquals(CelestialObjects.URANUS, positions.get(2).getChartPoint());
      assertEquals(38.83133542694, positions.get(2).getLongitude(), DELTA_8_POS);
      assertEquals(14.017602186382, positions.get(2).getDeclination(), DELTA_8_POS);
   }

   @Test
   public void findAspects() {
      final EphProgAspectResponse response = api.defineAspects(createAnalyzeRequest());
      assertEquals(4, response.getAnalyzedAspects().size());
      AnalyzedAspectTransit analyzedAspect = (AnalyzedAspectTransit) response.getAnalyzedAspects().get(0);
      assertEquals(CelestialObjects.MARS, analyzedAspect.getFirst().getChartPoint());
      assertEquals(CelestialObjects.SUN, analyzedAspect.getSecond().getChartPoint());
      assertEquals(AspectTypes.TRIANGLE, analyzedAspect.getAspectType());
      assertEquals(0.2, analyzedAspect.getActualOrb(), DELTA_8_POS);
      assertEquals(1.0, analyzedAspect.getMaxOrb(), DELTA_8_POS);
      assertEquals(20.0, analyzedAspect.getPercOrb(), DELTA_8_POS);
   }

   private EphProgCalcRequest createCalcRequest() {
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
      return new EphProgCalcRequest(fullDateTime, location, settings);
   }

   private ProgAnalyzeRequest createAnalyzeRequest() {
      List<IPosition> transitPositions = new ArrayList<>();
      transitPositions.add(createFpPosition(CelestialObjects.MARS, 112.0, 1.0, 111.0, 5.5));    // 120 Sun 90 Mars
      transitPositions.add(createFpPosition(CelestialObjects.JUPITER, 274.5, -2.0, 275.0, -10.0));
      transitPositions.add(createFpPosition(CelestialObjects.SATURN, 163.0, 0.5, 164.0, 2.2));  // 60 Ura 0 Asc
      List<IPosition> celestialPositions = new ArrayList<>();
      celestialPositions.add(createFpPosition(CelestialObjects.SUN, 351.8, 1.0, 352.0, -8.8));
      celestialPositions.add(createFpPosition(CelestialObjects.MARS, 21.5, 0.7, 22.0, 4.4));
      celestialPositions.add(createFpPosition(CelestialObjects.URANUS, 102.7, 0.3, 103.0, 4.0));
      List<IPosition> mundanePositions = new ArrayList<>();
      CoordinateSet csEq = new CoordinateSet(0.0, 0.0);
      CoordinateSet csHor = new CoordinateSet(162.0, 20.0);
      mundanePositions.add(new MundanePosition(MundanePoints.ASC, 162.5, csEq, csHor));
      AllMundanePositions allMundPos = new AllMundanePositions(mundanePositions, mundanePositions);
      CalculatedChart cChart = new CalculatedChart(celestialPositions, allMundPos);
      List<AspectTypes> aspectTypes = new ArrayList<>();
      aspectTypes.add(AspectTypes.CONJUNCTION);
      aspectTypes.add(AspectTypes.SEXTILE);
      aspectTypes.add(AspectTypes.SQUARE);
      aspectTypes.add(AspectTypes.TRIANGLE);
      aspectTypes.add(AspectTypes.OPPOSITION);
      return new ProgAnalyzeRequest(ProgAnalysisType.ASPECTS, transitPositions, cChart, aspectTypes, 1.0);
   }

   private FullPointPosition createFpPosition(final IChartPoints cPoint, final double lon, final double decl, final double azimuth, final double altitude) {
      CoordinateSet3D csEclPos = new CoordinateSet3D(lon, 0.0, 1.0);
      CoordinateSet3D csEclSpeed = new CoordinateSet3D(1.0, 0.0, 0.0);
      FullPointCoordinate fpcEcl = new FullPointCoordinate(csEclPos, csEclSpeed);
      CoordinateSet3D csEqPos = new CoordinateSet3D(0.0, decl, 1.0);
      CoordinateSet3D csEqSpeed = new CoordinateSet3D(0.0, 0.1, 0.0);
      FullPointCoordinate fpcEq = new FullPointCoordinate(csEqPos, csEqSpeed);
      CoordinateSet csHor = new CoordinateSet(azimuth, altitude);
      return new FullPointPosition(cPoint, fpcEcl, fpcEq, csHor);
   }

}