/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

/**
 * Defines glyphs for zodiacal signs.
 */
public class GlyphForSign {

   private final String glyph;

   /**
    * Constructor defines glyph.
    *
    * @param index index of the sign: 1 is Aries ... 12 is Pisces.
    */
   public GlyphForSign(final int index) {
      glyph = signGlyphFromIndex(index);
   }

   private String signGlyphFromIndex(final int index) {
      // todo Release 2020.2: use sign-glyphs from settings
      String glyphs = "1234567890-=";
      return glyphs.substring(index - 1, index);
   }

   public String getGlyph() {
      return this.glyph;
   }
}
