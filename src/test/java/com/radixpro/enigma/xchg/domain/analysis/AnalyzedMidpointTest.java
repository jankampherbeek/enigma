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

public class AnalyzedMidpointTest {

   private AnalyzablePoint firstPoint;
   private AnalyzablePoint secondPoint;
   private AnalyzablePoint centerPoint;
   private MidpointTypes midpointType = MidpointTypes.FULL;
   private double actualOrb = 0.228;
   private double maxOrb = 1.5;
   private AnalyzedMidpoint midpoint;

   @Before
   public void setUp() throws Exception {
      firstPoint = new AnalyzablePoint(CelestialObjects.SUN, 123.456);
      secondPoint = new AnalyzablePoint(CelestialObjects.SATURN, 153.456);
      centerPoint = new AnalyzablePoint(MundanePoints.ASC, 138.0);
      midpoint = new AnalyzedMidpoint(firstPoint, secondPoint, centerPoint, midpointType, actualOrb, maxOrb);
   }

   @Test
   public void getFirst() {
      assertEquals(firstPoint, midpoint.getFirst());
   }

   @Test
   public void getSecond() {
      assertEquals(secondPoint, midpoint.getSecond());
   }

   @Test
   public void getCenterPoint() {
      assertEquals(centerPoint, midpoint.getCenterPoint());
   }

   @Test
   public void getActualOrb() {
      assertEquals(actualOrb, midpoint.getActualOrb(), DELTA_8_POS);
   }

   @Test
   public void getMaxOrb() {
      assertEquals(maxOrb, midpoint.getMaxOrb(), DELTA_8_POS);
   }

   @Test
   public void getPercOrb() {
      assertEquals(15.2, midpoint.getPercOrb(), DELTA_8_POS);
   }
}