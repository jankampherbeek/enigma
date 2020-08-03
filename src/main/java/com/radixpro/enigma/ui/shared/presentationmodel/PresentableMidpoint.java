/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.presentationmodel;

import com.radixpro.enigma.shared.common.Rosetta;
import com.radixpro.enigma.ui.charts.screens.helpers.GlyphForCelObject;
import com.radixpro.enigma.ui.shared.formatters.SexagesimalFormatter;
import com.radixpro.enigma.xchg.domain.CelestialObjects;
import com.radixpro.enigma.xchg.domain.IChartPoints;
import com.radixpro.enigma.xchg.domain.MundanePoints;
import com.radixpro.enigma.xchg.domain.analysis.AnalyzedMidpoint;
import com.radixpro.enigma.xchg.domain.analysis.IAnalyzedPair;

import static com.google.common.base.Preconditions.checkNotNull;

public class PresentableMidpoint {

   private String firstItemGlyph;
   private String secondItemGlyph;
   private String thirdItemGlyph;
   private String midpointType;
   private String effectiveOrb;
   private String percOrb;
   private final Rosetta rosetta;

   // TODO combine logic of PresentableMidpoint and PresentableAspect (maybe abstract parent ?).
   public PresentableMidpoint(final IAnalyzedPair midpoint) {
      rosetta = Rosetta.getRosetta();
      createDataDescription(checkNotNull(midpoint));
   }


   private void createDataDescription(IAnalyzedPair pair) {
      SexagesimalFormatter sexFormatter = new SexagesimalFormatter(2);
      AnalyzedMidpoint midpoint = (AnalyzedMidpoint) pair;
      firstItemGlyph = defineGlyphForItem(pair.getFirst().getChartPoint());
      secondItemGlyph = defineGlyphForItem(pair.getSecond().getChartPoint());
      thirdItemGlyph = defineGlyphForItem(((AnalyzedMidpoint) pair).getCenterPoint().getChartPoint());
      midpointType = rosetta.getText(midpoint.getMidpointType().getFullRbId());
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
