/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

public class GlyphForAspect {


   public String getGlyph(final int index) {
      // todo Release 2020.2: use aspect-glyphs from settings
      String glyph;
      switch (index) {
         case 1 -> glyph = "B";     // Conjunction
         case 2 -> glyph = "C";     // Opposition
         case 3 -> glyph = "D";     // Triangle
         case 4 -> glyph = "E";     // Square
         case 5 -> glyph = "F";     // Sextile
         case 6 -> glyph = "G";     // Semisextile
         case 7 -> glyph = "H";     // Inconjunct
         case 8 -> glyph = "I";     // Semisquare
         case 9 -> glyph = "J";     // Sesquiquadrate
         case 10 -> glyph = "K";     // Quintile
         case 11 -> glyph = "L";     // Biquintile
         case 12 -> glyph = "N";     // Septile
         case 13 -> glyph = "O";     // Parallel
         case 14 -> glyph = "P";     // Contraparallel
         case 15 -> glyph = "Ï";     // Vigintile
         case 16 -> glyph = "Ö";     // Semiquintile
         case 17 -> glyph = "Õ";     // Tridecile
         case 18 -> glyph = "Ú";     // Biseptile
         case 19 -> glyph = "Û";     // Triseptile
         case 20 -> glyph = "Ü";     // Novile
         case 21 -> glyph = "Ü";     // Binovile
         case 22 -> glyph = "|";     // Quadranovile
         case 23 -> glyph = "ç";     // Undecile
         case 24 -> glyph = "Ç";     // Centile
         default -> glyph = "";
      }
      return glyph;
   }
}
