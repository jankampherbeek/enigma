/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.presentationmodel;

import com.radixpro.enigma.ui.charts.screens.helpers.GlyphForAspect;
import com.radixpro.enigma.ui.charts.screens.helpers.GlyphForCelObject;
import com.radixpro.enigma.ui.shared.formatters.SexagesimalFormatter;
import com.radixpro.enigma.xchg.domain.CelestialObjects;
import com.radixpro.enigma.xchg.domain.IChartPoints;
import com.radixpro.enigma.xchg.domain.MundanePoints;
import com.radixpro.enigma.xchg.domain.analysis.AnalyzedAspect;
import com.radixpro.enigma.xchg.domain.analysis.IAnalyzedPair;

import static com.google.common.base.Preconditions.checkNotNull;

public class PresentableAspect {
   private String firstItemGlyph;
   private String secondItemGlyph;
   private String aspectGlyph;
   private String effectiveOrb;
   private String percOrb;

   public PresentableAspect(final IAnalyzedPair aspect) {
      createDataDescription(checkNotNull(aspect));
   }

   private void createDataDescription(IAnalyzedPair pair) {
      SexagesimalFormatter sexFormatter = new SexagesimalFormatter(2);
      GlyphForAspect glyphForAspect = new GlyphForAspect();
      AnalyzedAspect aspect = (AnalyzedAspect) pair;
      firstItemGlyph = defineGlyphForItem(pair.getFirst().getChartPoint());
      secondItemGlyph = defineGlyphForItem(pair.getSecond().getChartPoint());
      aspectGlyph = glyphForAspect.getGlyph(((AnalyzedAspect) pair).getAspectType().getId());
      effectiveOrb = sexFormatter.formatDms(aspect.getActualOrb());
      percOrb = Integer.toString((int) aspect.getPercOrb());
   }

   private String defineGlyphForItem(IChartPoints point) {
      GlyphForCelObject glyphForCelObject = new GlyphForCelObject();
      if (point instanceof CelestialObjects) {
         return glyphForCelObject.getGlyph(point.getId());
      } else if (point instanceof MundanePoints) {
         if (point == MundanePoints.MC) return "M";    // glyph for Mc
         if (point == MundanePoints.ASC) return "A";    // glyph for Asc
      }
      return "";
   }

   public String getFirstItemGlyph() {
      return firstItemGlyph;
   }

   public String getSecondItemGlyph() {
      return secondItemGlyph;
   }

   public String getAspectGlyph() {
      return aspectGlyph;
   }

   public String getEffectiveOrb() {
      return effectiveOrb;
   }

   public String getPercOrb() {
      return percOrb;
   }
}

