/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain.config;

import com.radixpro.enigma.xchg.domain.Aspects;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Configuration info for a specific aspect.
 */
public class ConfiguredAspect implements Serializable {

   private final boolean showInDrawing;
   private final Aspects aspect;
   private final int orbPercentage;
   private final String glyph;

   public ConfiguredAspect(final Aspects aspect, final int orbPercentage, final String glyph,
                           final boolean showInDrawing) {
      this.aspect = checkNotNull(aspect);
      this.orbPercentage = orbPercentage;
      this.glyph = checkNotNull(glyph);
      this.showInDrawing = showInDrawing;
   }

   public boolean isShowInDrawing() {
      return showInDrawing;
   }

   public Aspects getAspect() {
      return aspect;
   }

   public int getOrbPercentage() {
      return orbPercentage;
   }

   public String getGlyph() {
      return glyph;
   }
}
