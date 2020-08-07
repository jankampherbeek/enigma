/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.glyphs;

import com.radixpro.enigma.references.CelestialObjects;
import com.radixpro.enigma.xchg.domain.IChartPoints;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Maps a celestial object to the corresponding glyph.
 */
public class CelObject2GlyphMapper {

   public String getGlyph(final IChartPoints celObject) {
      checkNotNull(celObject);
      return switch ((CelestialObjects) celObject) {
         case SUN -> "a";
         case MOON -> "b";
         case MERCURY -> "c";
         case VENUS -> "d";
         case MARS -> "f";
         case JUPITER -> "g";
         case SATURN -> "h";
         case URANUS -> "i";
         case NEPTUNE -> "j";
         case PLUTO -> "k";
         case MEAN_NODE -> "{";
         case CHEIRON -> "w";
         default -> " ";
      };
   }

}
