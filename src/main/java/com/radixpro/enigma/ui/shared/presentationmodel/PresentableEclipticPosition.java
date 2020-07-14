/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel;

import com.radixpro.enigma.ui.shared.glyphs.CelObject2GlyphMapper;
import com.radixpro.enigma.ui.shared.glyphs.Sign2GlyphMapper;
import com.radixpro.enigma.ui.shared.presentationmodel.valuetypes.LongAndGlyphValue;
import com.radixpro.enigma.ui.shared.presentationmodel.valuetypes.LongWithGlyph;
import com.radixpro.enigma.ui.shared.presentationmodel.valuetypes.PlusMinusValue;
import com.radixpro.enigma.xchg.domain.CelestialObjects;
import com.radixpro.enigma.xchg.domain.astrondata.FullPointCoordinate;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Wrapper around CelObjectSinglePosition for the ecliptic values; enables the use in a tableview.
 */
public class PresentableEclipticPosition {

   private String formattedLongitude;
   private String formattedLongSpeed;
   private String formattedLatitude;
   private String formattedLatSpeed;
   private String signGlyph;
   private String celBodyGlyph;


   public PresentableEclipticPosition(final CelestialObjects celestialObject,
                                      final FullPointCoordinate fpCoordinate) {
      checkNotNull(celestialObject);
      checkNotNull(fpCoordinate);
      createPresentablePosition(celestialObject, fpCoordinate);
   }

   private void createPresentablePosition(final CelestialObjects celestialObject,
                                          final FullPointCoordinate fpCoordinate) {
      double mainPosition = fpCoordinate.getPosition().getMainCoord();
      LongWithGlyph longWithGlyph = new LongAndGlyphValue(mainPosition).getLongWithGlyph();
      formattedLongitude = longWithGlyph.getPosition();
      signGlyph = new Sign2GlyphMapper().getGlyph(longWithGlyph.getSignIndex());
      formattedLongSpeed = new PlusMinusValue(fpCoordinate.getSpeed().getMainCoord()).getFormattedPosition();
      formattedLatitude = new PlusMinusValue(fpCoordinate.getPosition().getDeviation()).getFormattedPosition();
      formattedLatSpeed = new PlusMinusValue(fpCoordinate.getSpeed().getDeviation()).getFormattedPosition();
      celBodyGlyph = new CelObject2GlyphMapper().getGlyph(celestialObject);
   }

   public String getFormattedLongitude() {
      return formattedLongitude;
   }

   public String getFormattedLongSpeed() {
      return formattedLongSpeed;
   }

   public String getFormattedLatitude() {
      return formattedLatitude;
   }

   public String getFormattedLatSpeed() {
      return formattedLatSpeed;
   }

   public String getSignGlyph() {
      return signGlyph;
   }

   public String getCelBodyGlyph() {
      return celBodyGlyph;
   }
}
