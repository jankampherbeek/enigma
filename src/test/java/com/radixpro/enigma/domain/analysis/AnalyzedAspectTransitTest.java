/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.analysis;

import com.radixpro.enigma.references.AspectTypes;
import com.radixpro.enigma.references.CelestialObjects;
import org.junit.Before;
import org.junit.Test;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
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
   public void getPercOrb() {
      assertEquals(33.33333333333333, aspectTransit.getPercOrb(), DELTA_8_POS);
   }
}