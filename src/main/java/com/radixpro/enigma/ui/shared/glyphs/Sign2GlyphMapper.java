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
      String glyph;
      switch (indexOfSign) {
         case 1:
            glyph = "1";
            break;
         case 2:
            glyph = "2";
            break;
         case 3:
            glyph = "3";
            break;
         case 4:
            glyph = "4";
            break;
         case 5:
            glyph = "5";
            break;
         case 6:
            glyph = "6";
            break;
         case 7:
            glyph = "7";
            break;
         case 8:
            glyph = "8";
            break;
         case 9:
            glyph = "9";
            break;
         case 10:
            glyph = "0";
            break;
         case 11:
            glyph = "-";
            break;
         case 12:
            glyph = "=";
            break;
         default:
            glyph = " ";
            break;
      }
      return glyph;
   }

}
