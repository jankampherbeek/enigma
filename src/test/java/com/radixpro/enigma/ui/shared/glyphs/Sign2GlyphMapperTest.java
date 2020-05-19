/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.glyphs;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Sign2GlyphMapperTest {

   private final Sign2GlyphMapper mapper = new Sign2GlyphMapper();

   @Test
   public void getGlyph() {
      assertEquals("3", mapper.getGlyph(3));
      assertEquals("=", mapper.getGlyph(12));
   }
}