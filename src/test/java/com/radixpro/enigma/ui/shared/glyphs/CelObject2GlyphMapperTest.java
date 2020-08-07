/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.glyphs;

import com.radixpro.enigma.references.CelestialObjects;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CelObject2GlyphMapperTest {

   private final CelObject2GlyphMapper mapper = new CelObject2GlyphMapper();

   @Test
   public void getGlyph() {
      assertEquals("a", mapper.getGlyph(CelestialObjects.SUN));
   }

}