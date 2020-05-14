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


   /**
    * Constructor defines glyph.
    *
    * @param index index of the celestial object. Id as used in the enum CelestialObjects.
    */
   public GlyphForCelObject(final int index) {
      glyph = celObjectGlyphFromIndex(index);
   }

   private String celObjectGlyphFromIndex(final int index) {
      // todo Release 2020.2: use celbody-glyphs from settings
      switch (index) {
         case 1:
            glyph = "a";
            break;   // Sun
         case 2:
            glyph = "b";
            break;   // Moon
         case 3:
            glyph = "c";
            break;   // Mercury
         case 4:
            glyph = "d";
            break;   // Venus
         case 5:
            glyph = "e";
            break;   // Earth
         case 6:
            glyph = "f";
            break;   // Mars
         case 7:
            glyph = "g";
            break;   // Jupiter
         case 8:
            glyph = "h";
            break;   // Saturn
         case 9:
            glyph = "i";
            break;   // Uranus
         case 10:
            glyph = "j";
            break;  // Neptune
         case 11:
            glyph = "k";
            break;  // Pluto
         case 12:
            glyph = "w";
            break;  // Chiron
         case 13:
         case 14:
            glyph = "{";
            break;        // Lunar node
         default:
            glyph = "";
      }
      return glyph;
   }

   public String getGlyph() {
      return this.glyph;
   }
}
