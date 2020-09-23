/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.presentationmodel;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.domain.analysis.AnalyzedMidpoint;
import com.radixpro.enigma.domain.analysis.IAnalyzedPair;
import com.radixpro.enigma.references.CelestialObjects;
import com.radixpro.enigma.references.MundanePoints;
import com.radixpro.enigma.ui.charts.screens.helpers.GlyphForCelObject;
import com.radixpro.enigma.ui.shared.formatters.SexagesimalFormatter;
import com.radixpro.enigma.xchg.domain.IChartPoints;
import org.jetbrains.annotations.NotNull;

public class PresentableMidpoint {

   private String firstItemGlyph;
   private String secondItemGlyph;
   private String thirdItemGlyph;
   private String midpointType;
   private String effectiveOrb;
   private String percOrb;

   // TODO combine logic of PresentableMidpoint and PresentableAspect (maybe abstract parent ?).
   public PresentableMidpoint(@NotNull final IAnalyzedPair midpoint) {
      createDataDescription(midpoint);
   }


   private void createDataDescription(IAnalyzedPair pair) {
      SexagesimalFormatter sexFormatter = new SexagesimalFormatter(2);
      AnalyzedMidpoint midpoint = (AnalyzedMidpoint) pair;
      firstItemGlyph = defineGlyphForItem(pair.getFirst().getChartPoint());
      secondItemGlyph = defineGlyphForItem(pair.getSecond().getChartPoint());
      thirdItemGlyph = defineGlyphForItem(((AnalyzedMidpoint) pair).getCenterPoint().getChartPoint());
      midpointType = Rosetta.getText(midpoint.getMidpointType().getFullRbId());
      effectiveOrb = sexFormatter.formatDms(midpoint.getActualOrb());
      percOrb = Integer.toString((int) midpoint.getPercOrb());
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

   public String getThirdItemGlyph() {
      return thirdItemGlyph;
   }

   public String getMidpointType() {
      return midpointType;
   }

   public String getEffectiveOrb() {
      return effectiveOrb;
   }

   public String getPercOrb() {
      return percOrb;
   }
}
