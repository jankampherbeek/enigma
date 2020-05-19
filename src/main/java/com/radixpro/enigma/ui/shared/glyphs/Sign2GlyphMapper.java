/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.glyphs;

/**
 * Maps an index for a sign (1 = Aries ... 12 = Pisces) to the corresponding glyph.
 */
public class Sign2GlyphMapper {

   public String getGlyph(final int indexOfSign) {
      String glyph = switch (indexOfSign) {
         case 1 -> "1";
         case 2 -> "2";
         case 3 -> "3";
         case 4 -> "4";
         case 5 -> "5";
         case 6 -> "6";
         case 7 -> "7";
         case 8 -> "8";
         case 9 -> "9";
         case 10 -> "0";
         case 11 -> "-";
         case 12 -> "=";
         default -> " ";
      };
      return glyph;
   }

}
