/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.assist;

import com.radixpro.enigma.xchg.domain.CelestialObjects;
import com.radixpro.enigma.xchg.domain.IChartPoints;
import org.junit.Before;
import org.junit.Test;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

public class SpaeculumPropSaItemTest {

   private final IChartPoints chartPoint = CelestialObjects.URANUS;
   private final double lon = 101.1;
   private final double ra = 102.2;
   private final double decl = 20.0;
   private final double sa = 45.45;
   private final double propSa = 48.48;
   private final int quadrant = 3;
   private SpaeculumPropSaItem spsItem;

   @Before
   public void setUp() throws Exception {
      spsItem = new SpaeculumPropSaItem(chartPoint, lon, ra, decl, sa, propSa, quadrant);
   }

   @Test
   public void getChartPoint() {
      assertEquals(chartPoint, spsItem.getChartPoint());
   }

   @Test
   public void getLon() {
      assertEquals(lon, spsItem.getLon(), DELTA_8_POS);
   }

   @Test
   public void getRa() {
      assertEquals(ra, spsItem.getRa(), DELTA_8_POS);
   }

   @Test
   public void getDecl() {
      assertEquals(decl, spsItem.getDecl(), DELTA_8_POS);
   }

   @Test
   public void getSa() {
      assertEquals(sa, spsItem.getSa(), DELTA_8_POS);
   }

   @Test
   public void getPropSa() {
      assertEquals(propSa, spsItem.getPropSa(), DELTA_8_POS);
   }

   @Test
   public void getQuadrant() {
      assertEquals(quadrant, spsItem.getQuadrant());
   }

}