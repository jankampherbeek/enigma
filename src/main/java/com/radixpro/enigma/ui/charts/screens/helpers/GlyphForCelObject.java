/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

/**
 * Defines glyphs for celestial objects.
 */
public class GlyphForCelObject {

   private String glyph;

   public String getGlyph(final int index) {
      // todo Release 2020.2: use celbody-glyphs from settings
      switch (index) {
         case 1 -> glyph = "a";        // Sun
         case 2 -> glyph = "b";        // Moon
         case 3 -> glyph = "c";        // Mercury
         case 4 -> glyph = "d";        // Venus
         case 5 -> glyph = "e";        // Earth
         case 6 -> glyph = "f";        // Mars
         case 7 -> glyph = "g";        // Jupiter
         case 8 -> glyph = "h";        // Saturn
         case 9 -> glyph = "i";        // Uranus
         case 10 -> glyph = "j";       // Neptune
         case 11 -> glyph = "k";       // Pluto
         case 12 -> glyph = "w";       // Chiron
         case 13, 14 -> glyph = "{";   // Lunar node
         default -> glyph = "";
      }
      return glyph;
   }

   public String getGlyph() {
      return this.glyph;
   }
}
