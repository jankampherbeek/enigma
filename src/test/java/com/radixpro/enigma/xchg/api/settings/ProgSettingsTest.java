/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.settings;

import com.radixpro.enigma.references.Ayanamshas;
import com.radixpro.enigma.references.CelestialObjects;
import com.radixpro.enigma.xchg.domain.IChartPoints;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProgSettingsTest {

   private final Ayanamshas ayanamsha = Ayanamshas.HUBER;
   private final boolean sidereal = true;
   private final boolean topocentric = false;
   private List<IChartPoints> points;
   private ProgSettings progSettings;

   @Before
   public void setUp() throws Exception {
      points = new ArrayList<>();
      points.add(CelestialObjects.NEPTUNE);
      points.add(CelestialObjects.URANUS);
      progSettings = new ProgSettings(points, ayanamsha, sidereal, topocentric);
   }

   @Test
   public void getPoints() {
      assertEquals(points, progSettings.getPoints());
   }

   @Test
   public void getAyamsha() {
      assertEquals(ayanamsha, progSettings.getAyanamsha());
   }

   @Test
   public void isSidereal() {
      assertEquals(sidereal, progSettings.isSidereal());
   }

   @Test
   public void isTopocentric() {
      assertEquals(topocentric, progSettings.isTopocentric());
   }
}