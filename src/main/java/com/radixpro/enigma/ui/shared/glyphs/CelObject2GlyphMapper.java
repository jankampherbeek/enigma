/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.glyphs;

import com.radixpro.enigma.xchg.domain.CelestialObjects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Maps a celestial object to the corresponding glyph.
 */
public class CelObject2GlyphMapper {

   public String getGlyph(final CelestialObjects celObject) {
      checkNotNull(celObject);
      String glyph;
      switch (celObject) {
         case SUN:
            glyph = "a";
            break;
         case MOON:
            glyph = "b";
            break;
         case MERCURY:
            glyph = "c";
            break;
         case VENUS:
            glyph = "d";
            break;
         case EARTH:
            glyph = "e";
            break;
         case MARS:
            glyph = "f";
            break;
         case JUPITER:
            glyph = "g";
            break;
         case SATURN:
            glyph = "h";
            break;
         case URANUS:
            glyph = "i";
            break;
         case NEPTUNE:
            glyph = "j";
            break;
         case PLUTO:
            glyph = "k";
            break;
         case MEAN_NODE:
            glyph = "{";
            break;
         case CHEIRON:
            glyph = "w";
            break;
         default:
            glyph = " ";
            break;
      }
      return glyph;
   }

}
