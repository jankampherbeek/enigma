/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.config;

import com.radixpro.enigma.references.CelestialObjects;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Configuration for a specific celestial object.
 */
public class ConfiguredCelObject implements Serializable {
   private final CelestialObjects celObject;
   private final String glyph;
   private final int orbPercentage;
   private final boolean showInDrawing;

   /**
    * Constructor defines all members.
    *
    * @param celObject     The celestial object.
    * @param glyph         The glyph to be used.
    * @param orbPercentage Percentage for orb calculation.
    * @param showInDrawing True if object should be shown in drawing.
    */
   public ConfiguredCelObject(final CelestialObjects celObject,
                              final String glyph,
                              final int orbPercentage,
                              final boolean showInDrawing) {
      this.celObject = checkNotNull(celObject);
      this.glyph = checkNotNull(glyph);
      this.orbPercentage = orbPercentage;
      this.showInDrawing = showInDrawing;
   }

   public CelestialObjects getCelObject() {
      return celObject;
   }

   public String getGlyph() {
      return glyph;
   }

   public int getOrbPercentage() {
      return orbPercentage;
   }

   public boolean isShowInDrawing() {
      return showInDrawing;
   }
}
