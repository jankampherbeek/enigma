/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain.config;

import com.radixpro.enigma.xchg.domain.analysis.AspectTypes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConfiguredAspectTest {

   private final AspectTypes aspect = AspectTypes.OPPOSITION;
   private final int orbPercentage = 90;
   private final String glyph = "E";
   private final boolean showInDrawing = true;
   private ConfiguredAspect configuredAspect;

   @Before
   public void setUp() {
      configuredAspect = new ConfiguredAspect(aspect, orbPercentage, glyph, showInDrawing);
   }

   @Test
   public void getAspect() {
      assertEquals(aspect, configuredAspect.getAspect());
   }

   @Test
   public void getOrbPercentage() {
      assertEquals(orbPercentage, configuredAspect.getOrbPercentage());
   }

   @Test
   public void getGlyph() {
      assertEquals(glyph, configuredAspect.getGlyph());
   }

   @Test
   public void isShowInDrawing() {
      assertEquals(showInDrawing, configuredAspect.isShowInDrawing());
   }

}