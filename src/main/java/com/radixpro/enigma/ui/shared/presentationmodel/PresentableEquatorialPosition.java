/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel;

import com.radixpro.enigma.ui.shared.glyphs.CelObject2GlyphMapper;
import com.radixpro.enigma.ui.shared.presentationmodel.valuetypes.PlainDmsValue;
import com.radixpro.enigma.ui.shared.presentationmodel.valuetypes.PlusMinusValue;
import com.radixpro.enigma.xchg.domain.CelestialObjects;
import com.radixpro.enigma.xchg.domain.astrondata.FullPointCoordinate;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Wrapper around CelObjectSinglePosition for the equatorial values; enables the use in a tableview.
 */
public class PresentableEquatorialPosition {

   private String formattedRightAscension;
   private String formattedRaSpeed;
   private String formattedDeclination;
   private String formattedDeclSpeed;
   private String celBodyGlyph;

   public PresentableEquatorialPosition(final CelestialObjects celestialObject,
                                        final FullPointCoordinate fpCoordinate) {
      checkNotNull(celestialObject);
      checkNotNull(fpCoordinate);
      createPresentablePosition(celestialObject, fpCoordinate);
   }

   private void createPresentablePosition(final CelestialObjects celestialObject,
                                          final FullPointCoordinate fpCoordinate) {
      checkNotNull(celestialObject);
      checkNotNull(fpCoordinate);
      formattedRightAscension = new PlainDmsValue(fpCoordinate.getPosition().getMainCoord()).getFormattedPosition();
      formattedRaSpeed = new PlusMinusValue(fpCoordinate.getSpeed().getMainCoord()).getFormattedPosition();
      formattedDeclination = new PlusMinusValue(fpCoordinate.getPosition().getDeviation()).getFormattedPosition();
      formattedDeclSpeed = new PlusMinusValue(fpCoordinate.getSpeed().getDeviation()).getFormattedPosition();
      celBodyGlyph = new CelObject2GlyphMapper().getGlyph(celestialObject);
   }

   public String getFormattedRightAscension() {
      return formattedRightAscension;
   }

   public String getFormattedRaSpeed() {
      return formattedRaSpeed;
   }

   public String getFormattedDeclination() {
      return formattedDeclination;
   }

   public String getFormattedDeclSpeed() {
      return formattedDeclSpeed;
   }

   public String getCelBodyGlyph() {
      return celBodyGlyph;
   }
}
