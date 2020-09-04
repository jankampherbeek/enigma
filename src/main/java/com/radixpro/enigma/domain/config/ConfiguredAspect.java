/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.config;

import com.radixpro.enigma.references.AspectTypes;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Configuration info for a specific aspect.
 */
public class ConfiguredAspect implements Serializable {

   private final boolean showInDrawing;
   private final AspectTypes aspect;
   private final int orbPercentage;
   private final String glyph;

   public ConfiguredAspect(@NotNull final AspectTypes aspect, final int orbPercentage, @NotNull final String glyph, final boolean showInDrawing) {
      this.aspect = aspect;
      this.orbPercentage = orbPercentage;
      this.glyph = glyph;
      this.showInDrawing = showInDrawing;
   }

   public boolean isShowInDrawing() {
      return showInDrawing;
   }

   public AspectTypes getAspect() {
      return aspect;
   }

   public int getOrbPercentage() {
      return orbPercentage;
   }

   public String getGlyph() {
      return glyph;
   }
}
