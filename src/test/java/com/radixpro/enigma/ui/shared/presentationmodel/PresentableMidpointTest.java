/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.presentationmodel;

import com.radixpro.enigma.domain.analysis.AnalyzablePoint;
import com.radixpro.enigma.domain.analysis.AnalyzedMidpoint;
import com.radixpro.enigma.domain.analysis.IAnalyzedPair;
import com.radixpro.enigma.references.CelestialObjects;
import com.radixpro.enigma.references.MidpointTypes;
import com.radixpro.enigma.references.MundanePointsAstron;
import org.junit.Before;
import org.junit.Test;

import static com.radixpro.enigma.shared.common.EnigmaDictionary.*;
import static org.junit.Assert.assertEquals;

public class PresentableMidpointTest {

   private final MidpointTypes midpointType = MidpointTypes.QUARTER;
   private PresentableMidpoint presMidpoint;


   @Before
   public void setUp() {
      AnalyzablePoint firstPoint = new AnalyzablePoint(CelestialObjects.SUN, 122.0);
      AnalyzablePoint secondPoint = new AnalyzablePoint(CelestialObjects.MOON, 162.0);
      AnalyzablePoint thirdPoint = new AnalyzablePoint(MundanePointsAstron.ASC, 142.8);
      double maxOrb = 1.6;
      double effectiveOrb = 0.8;
      IAnalyzedPair midpoint = new AnalyzedMidpoint(firstPoint, secondPoint, thirdPoint, midpointType, effectiveOrb, maxOrb);
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