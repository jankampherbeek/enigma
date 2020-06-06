/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.presentationmodel;

import com.radixpro.enigma.xchg.domain.CelestialObjects;
import com.radixpro.enigma.xchg.domain.analysis.AnalyzablePoint;
import com.radixpro.enigma.xchg.domain.analysis.AnalyzedAspect;
import com.radixpro.enigma.xchg.domain.analysis.AspectTypes;
import com.radixpro.enigma.xchg.domain.analysis.IAnalyzedPair;
import org.junit.Before;
import org.junit.Test;

import static com.radixpro.enigma.shared.EnigmaDictionary.*;
import static org.junit.Assert.assertEquals;

public class PresentableAspectTest {

   private final AspectTypes aspectType = AspectTypes.SEXTILE;
   private PresentableAspect presAspect;

   @Before
   public void setUp() {
      AnalyzablePoint firstPoint = new AnalyzablePoint(CelestialObjects.SUN, 122.0);
      AnalyzablePoint secondPoint = new AnalyzablePoint(CelestialObjects.MOON, 184.0);
      double maxOrb = 8.0;
      double effectiveOrb = 2.0;
      IAnalyzedPair aspect = new AnalyzedAspect(firstPoint, secondPoint, aspectType, effectiveOrb, maxOrb);
      presAspect = new PresentableAspect(aspect);
   }

   @Test
   public void getFirstItemGlyph() {
      assertEquals("a", presAspect.getFirstItemGlyph());
   }

   @Test
   public void getSecondItemGlyph() {
      assertEquals("b", presAspect.getSecondItemGlyph());
   }

   @Test
   public void getAspectGlyph() {
      assertEquals("F", presAspect.getAspectGlyph());
   }

   @Test
   public void getEffectiveOrb() {
      assertEquals("02" + DEGREESIGN + "00" + MINUTESIGN + "00" + SECONDSIGN, presAspect.getEffectiveOrb());
   }

   @Test
   public void getPercOrb() {
      assertEquals("25", presAspect.getPercOrb());
   }
}