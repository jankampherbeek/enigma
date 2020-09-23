/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.domain.astronpos.*;
import com.radixpro.enigma.domain.input.DateTimeJulian;
import com.radixpro.enigma.domain.input.Location;
import com.radixpro.enigma.domain.reqresp.PrimaryCalcRequest;
import com.radixpro.enigma.domain.reqresp.SimpleProgResponse;
import com.radixpro.enigma.references.*;
import com.radixpro.enigma.ui.helpers.DateTimeJulianCreator;
import com.radixpro.enigma.ui.helpers.LocationCreator;
import com.radixpro.enigma.xchg.api.settings.ICalcSettings;
import com.radixpro.enigma.xchg.api.settings.ProgSettings;
import com.radixpro.enigma.xchg.domain.IChartPoints;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class PrimaryApiIntTest {

   private PrimaryApi api;
   private DateTimeJulianCreator dateTimeJulianCreator;

   @Before
   public void setUp() {
      dateTimeJulianCreator = new DateTimeJulianCreator();
      api = XchgApiInjector.injectPrimaryApi();
   }

   @Test
   public void calculatePrimary() {
      final PrimaryCalcRequest request = new PrimaryCalcRequest(createDateTimeProg(), createDateTimeRadix(), createSettings(), TimeKeys.REAL_SECUNDARY_SUN,
            createLocation(), createCalculatedChart());
      final SimpleProgResponse response = api.calculatePrimary(request);
      assertTrue(response.getPositions().size() > 0);

   }


   private DateTimeJulian createDateTimeRadix() {
      return dateTimeJulianCreator.createDateTime("2000/1/1", "G", "12:00:00", TimeZones.UT, false, 0.0);
   }

   private DateTimeJulian createDateTimeProg() {
      return dateTimeJulianCreator.createDateTime("2020/1/1", "G", "12:00:00", TimeZones.UT, false, 0.0);
   }

   private ICalcSettings createSettings() {
      final boolean sidereal = false;
      final boolean topocentric = false;
      List<IChartPoints> points = new ArrayList<>();
      points.add(CelestialObjects.SUN);
      points.add(CelestialObjects.MOON);
      points.add(CelestialObjects.MERCURY);
      points.add(CelestialObjects.VENUS);
      points.add(CelestialObjects.MARS);
      return new ProgSettings(points, Ayanamshas.NONE, sidereal, topocentric);
   }

   private Location createLocation() {
      return new LocationCreator().createLocation(52, 0, 0, "N", 6, 54, 9, "E");
   }

   private CalculatedChart createCalculatedChart() {
      List<IPosition> celPoints = new ArrayList<>();
      celPoints.add(createFPP(CelestialObjects.SUN, 100.0, 13.13));
      celPoints.add(createFPP(CelestialObjects.MOON, 200.0, -12.12));
      celPoints.add(createFPP(CelestialObjects.MERCURY, 110.0, 12.0));
      celPoints.add(createFPP(CelestialObjects.VENUS, 108.0, 11.11));
      celPoints.add(createFPP(CelestialObjects.MARS, 350.0, -1.0));
      return new CalculatedChart(celPoints, createAmPos());
   }

   private FullPointPosition createFPP(IChartPoints point, double lon, double decl) {
      final FullPointCoordinate eclPos = new FullPointCoordinate(new CoordinateSet3D(lon, 0.0, 1.0),
            new CoordinateSet3D(0.0, 0.0, 1.0));
      final FullPointCoordinate eqPos = new FullPointCoordinate(new CoordinateSet3D(0.0, decl, 1.0),
            new CoordinateSet3D(0.0, 0.0, 1.0));
      final CoordinateSet horPos = new CoordinateSet(0.0, 0.0);
      return new FullPointPosition(point, eclPos, eqPos, horPos);
   }

   private AllMundanePositions createAmPos() {
      List<IPosition> cusps = new ArrayList<>();
      List<IPosition> specPoints = new ArrayList<>();
      specPoints.add(new MundanePosition(MundanePoints.ASC, 33.33, new CoordinateSet(0.0, 0.0),
            new CoordinateSet(0.0, 0.0)));
      specPoints.add(new MundanePosition(MundanePoints.MC, 299.9, new CoordinateSet(298.0, 0.0),
            new CoordinateSet(0.0, 0.0)));
      return new AllMundanePositions(cusps, specPoints);
   }

}