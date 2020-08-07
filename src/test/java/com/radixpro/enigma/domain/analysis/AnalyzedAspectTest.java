/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.analysis;


import com.radixpro.enigma.references.AspectTypes;
import com.radixpro.enigma.references.CelestialObjects;
import com.radixpro.enigma.testsupport.TestConstants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class AnalyzedAspectTest {

   private AnalyzablePoint firstPoint;
   private AnalyzablePoint secondPoint;
   private final AspectTypes aspectType = AspectTypes.OPPOSITION;
   private final double actualOrb = 2.0;
   private final double maxOrb = 6.0;
   private AnalyzedAspect aspect;

   @Before
   public void setUp() {
      firstPoint = new AnalyzablePoint(CelestialObjects.SUN, 100.0);
      secondPoint = new AnalyzablePoint(CelestialObjects.MOON, 282.0);
      aspect = new AnalyzedAspect(firstPoint, secondPoint, aspectType, actualOrb, maxOrb);
   }

   @Test
   public void getPercOrb() {
      Assert.assertEquals(33.33333333333, aspect.getPercOrb(), TestConstants.DELTA_8_POS);
   }

}