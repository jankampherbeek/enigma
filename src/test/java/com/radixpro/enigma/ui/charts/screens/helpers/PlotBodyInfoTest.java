/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

import com.radixpro.enigma.xchg.domain.CelestialObjects;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlotBodyInfoTest {

   private static final double DELTA = 0.00000001;
   private PlotBodyInfo plotBodyInfo;

   @Before
   public void setUp() throws Exception {
      plotBodyInfo = new PlotBodyInfo(CelestialObjects.SATURN, 30.0, 32.5);
   }

   @Test
   public void getCelObject() {
      assertEquals(CelestialObjects.SATURN, plotBodyInfo.getCelObject());
   }

   @Test
   public void getAngleFromAsc() {
      assertEquals(30.0, plotBodyInfo.getAngleFromAsc(), DELTA);
   }

   @Test
   public void getCorrectedAngle() {
      assertEquals(30.0, plotBodyInfo.getCorrectedAngle(), DELTA);
   }

   @Test
   public void setCorrectedAngle() {
      plotBodyInfo.setCorrectedAngle(33.0);
      assertEquals(33.0, plotBodyInfo.getCorrectedAngle(), DELTA);
   }

   @Test
   public void getPosText() {
      assertEquals("02°30′", plotBodyInfo.getPosText());
   }
}