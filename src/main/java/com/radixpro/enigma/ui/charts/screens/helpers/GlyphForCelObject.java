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
// Sun
         case 1 -> glyph = "a";
// Moon
         case 2 -> glyph = "b";
// Mercury
         case 3 -> glyph = "c";
// Venus
         case 4 -> glyph = "d";
// Earth
         case 5 -> glyph = "e";
// Mars
         case 6 -> glyph = "f";
// Jupiter
         case 7 -> glyph = "g";
// Saturn
         case 8 -> glyph = "h";
// Uranus
         case 9 -> glyph = "i";
// Neptune
         case 10 -> glyph = "j";
// Pluto
         case 11 -> glyph = "k";
// Chiron
         case 12 -> glyph = "w";
// Lunar node
         case 13, 14 -> glyph = "{";
         default -> glyph = "";
      }
      return glyph;
   }

   public String getGlyph() {
      return this.glyph;
   }
}
