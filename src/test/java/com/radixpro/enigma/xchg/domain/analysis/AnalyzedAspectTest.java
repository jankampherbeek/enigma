/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.analysis;

import com.radixpro.enigma.xchg.domain.AspectTypes;
import com.radixpro.enigma.xchg.domain.CelestialObjects;
import org.junit.Before;
import org.junit.Test;

import static com.radixpro.enigma.testsupport.TextConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

public class AnalyzedAspectTest {

   private AnalyzablePoint firstPoint;
   private AnalyzablePoint secondPoint;
   private AspectTypes aspectType = AspectTypes.OPPOSITION;
   private double actualOrb = 2.0;
   private double maxOrb = 6.0;
   private AnalyzedAspect aspect;

   @Before
   public void setUp() throws Exception {
      firstPoint = new AnalyzablePoint(CelestialObjects.SUN, 100.0);
      secondPoint = new AnalyzablePoint(CelestialObjects.MOON, 282.0);
      aspect = new AnalyzedAspect(firstPoint, secondPoint, aspectType, actualOrb, maxOrb);
   }

   @Test
   public void getFirst() {
      assertEquals(firstPoint, aspect.getFirst());
   }

   @Test
   public void getSecond() {
      assertEquals(secondPoint, aspect.getSecond());
   }

   @Test
   public void getActualOrb() {
      assertEquals(actualOrb, aspect.getActualOrb(), DELTA_8_POS);
   }

   @Test
   public void getMaxOrb() {
      assertEquals(maxOrb, aspect.getMaxOrb(), DELTA_8_POS);
   }

   @Test
   public void getPercOrb() {
      assertEquals(33.33333333333, aspect.getPercOrb(), DELTA_8_POS);
   }
}