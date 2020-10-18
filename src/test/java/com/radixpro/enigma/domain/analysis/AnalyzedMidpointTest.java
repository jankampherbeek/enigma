/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.analysis;

import com.radixpro.enigma.references.CelestialObjects;
import com.radixpro.enigma.references.MidpointTypes;
import com.radixpro.enigma.references.MundanePointsAstron;
import org.junit.Before;
import org.junit.Test;

import static com.radixpro.enigma.testsupport.TestConstants.DELTA_8_POS;
import static org.junit.Assert.assertEquals;

public class AnalyzedMidpointTest {

   private AnalyzablePoint firstPoint;
   private AnalyzablePoint secondPoint;
   private AnalyzablePoint centerPoint;
   private final MidpointTypes midpointType = MidpointTypes.FULL;
   private final double actualOrb = 0.228;
   private final double maxOrb = 1.5;
   private AnalyzedMidpoint midpoint;

   @Before
   public void setUp() {
      firstPoint = new AnalyzablePoint(CelestialObjects.SUN, 123.456);
      secondPoint = new AnalyzablePoint(CelestialObjects.SATURN, 153.456);
      centerPoint = new AnalyzablePoint(MundanePointsAstron.ASC, 138.0);
      midpoint = new AnalyzedMidpoint(firstPoint, secondPoint, centerPoint, midpointType, actualOrb, maxOrb);
   }


   @Test
   public void getPercOrb() {
      assertEquals(15.2, midpoint.getPercOrb(), DELTA_8_POS);
   }
}