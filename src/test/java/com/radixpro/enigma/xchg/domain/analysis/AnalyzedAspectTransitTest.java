/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.analysis;

import com.radixpro.enigma.xchg.domain.CelestialObjects;
import org.junit.Before;
import org.junit.Test;

import static com.radixpro.enigma.testsupport.TextConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

public class AnalyzedAspectTransitTest {

   private final AspectTypes aspectType = AspectTypes.SEXTILE;
   private final double orb = 0.4;
   private final double maxOrb = 1.2;
   private AnalyzablePoint transitPoint;
   private AnalyzablePoint radixPoint;
   private AnalyzedAspectTransit aspectTransit;

   @Before
   public void setUp() throws Exception {
      transitPoint = new AnalyzablePoint(CelestialObjects.URANUS, 102.5);
      radixPoint = new AnalyzablePoint(CelestialObjects.MOON, 162.9);
      aspectTransit = new AnalyzedAspectTransit(transitPoint, radixPoint, aspectType, orb, maxOrb);
   }

   @Test
   public void getTransitPoint() {
      assertEquals(transitPoint, aspectTransit.getFirst());
   }

   @Test
   public void getRadixPoint() {
      assertEquals(radixPoint, aspectTransit.getSecond());
   }

   @Test
   public void getAspectType() {
      assertEquals(aspectType, aspectTransit.getAspectType());
   }

   @Test
   public void getOrb() {
      assertEquals(orb, aspectTransit.getActualOrb(), DELTA_8_POS);
   }

   @Test
   public void getMaxOrb() {
      assertEquals(maxOrb, aspectTransit.getMaxOrb(), DELTA_8_POS);
   }

   @Test
   public void getPercOrb() {
      assertEquals(33.33333333333333, aspectTransit.getPercOrb(), DELTA_8_POS);
   }
}