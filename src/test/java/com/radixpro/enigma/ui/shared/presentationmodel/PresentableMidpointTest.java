/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.presentationmodel;

import com.radixpro.enigma.xchg.domain.CelestialObjects;
import com.radixpro.enigma.xchg.domain.analysis.*;
import org.junit.Before;
import org.junit.Test;

import static com.radixpro.enigma.shared.EnigmaDictionary.*;
import static org.junit.Assert.assertEquals;

public class PresentableMidpointTest {

   private IAnalyzedPair midpoint;
   private AnalyzablePoint firstPoint;
   private AnalyzablePoint secondPoint;
   private AnalyzablePoint thirdPoint;
   private MidpointTypes midpointType = MidpointTypes.QUARTER;
   private double effectiveOrb = 0.8;
   private double maxOrb = 1.6;
   private PresentableMidpoint presMidpoint;


   @Before
   public void setUp() throws Exception {
      firstPoint = new AnalyzablePoint(CelestialObjects.SUN, 122.0);
      secondPoint = new AnalyzablePoint(CelestialObjects.MOON, 162.0);
      thirdPoint = new AnalyzablePoint(MundanePoints.ASC, 142.8);
      midpoint = new AnalyzedMidpoint(firstPoint, secondPoint, thirdPoint, midpointType, effectiveOrb, maxOrb);
      presMidpoint = new PresentableMidpoint(midpoint);
   }

   @Test
   public void getFirstItemGlyph() {
      assertEquals("a", presMidpoint.getFirstItemGlyph());
   }

   @Test
   public void getSecondItemGlyph() {
      assertEquals("b", presMidpoint.getSecondItemGlyph());
   }

   @Test
   public void getThirdItemGlyph() {
      assertEquals("A", presMidpoint.getThirdItemGlyph());
   }

   @Test
   public void getMidpointType() {
      assertEquals("90" + DEGREESIGN, presMidpoint.getMidpointType());
   }

   @Test
   public void getEffectiveOrb() {
      assertEquals("00" + DEGREESIGN + "48" + MINUTESIGN + "00" + SECONDSIGN, presMidpoint.getEffectiveOrb());
   }

   @Test
   public void getPercOrb() {
      assertEquals("50", presMidpoint.getPercOrb());
   }
}