/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.settings;

import com.radixpro.enigma.xchg.domain.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ChartCalcSettingsTest {

   private ObserverPositions obsPos = ObserverPositions.GEOCENTRIC;
   private EclipticProjections eclProj = EclipticProjections.TROPICAL;
   private Ayanamshas ayanamsha = Ayanamshas.NONE;
   private HouseSystems houseSys = HouseSystems.CAMPANUS;
   private ChartCalcSettings chartCalcSettings;

   @Before
   public void setUp() throws Exception {
      List<IChartPoints> points = new ArrayList<>();
      points.add(CelestialObjects.SUN);
      points.add(CelestialObjects.MOON);
      chartCalcSettings = new ChartCalcSettings(points, obsPos, eclProj, ayanamsha, houseSys);
   }

   @Test
   public void getPoints() {
      assertEquals(2, chartCalcSettings.getPoints().size());
      assertEquals(CelestialObjects.MOON, chartCalcSettings.getPoints().get(1));
   }

   @Test
   public void getObsPos() {
      assertEquals(obsPos, chartCalcSettings.getObsPos());
   }

   @Test
   public void getEclProj() {
      assertEquals(eclProj, chartCalcSettings.getEclProj());
   }

   @Test
   public void getAyanamsha() {
      assertEquals(ayanamsha, chartCalcSettings.getAyanamsha());
   }

   @Test
   public void getHouseSystem() {
      assertEquals(houseSys, chartCalcSettings.getHouseSystem());
   }
}