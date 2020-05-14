/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel;

import com.radixpro.enigma.ui.shared.glyphs.CelObject2GlyphMapper;
import com.radixpro.enigma.ui.shared.presentationmodel.valuetypes.PlainDmsValue;
import com.radixpro.enigma.ui.shared.presentationmodel.valuetypes.PlusMinusValue;
import com.radixpro.enigma.xchg.domain.CelObjectSinglePosition;
import com.radixpro.enigma.xchg.domain.CelestialObjects;

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
                                        final CelObjectSinglePosition celObjectSinglePosition) {
      checkNotNull(celestialObject);
      checkNotNull(celObjectSinglePosition);
      createPresentablePosition(celestialObject, celObjectSinglePosition);
   }

   private void createPresentablePosition(final CelestialObjects celestialObject,
                                          final CelObjectSinglePosition celObjectSinglePosition) {
      checkNotNull(celestialObject);
      checkNotNull(celObjectSinglePosition);
      formattedRightAscension = new PlainDmsValue(celObjectSinglePosition.getMainPosition()).getFormattedPosition();
      formattedRaSpeed = new PlusMinusValue(celObjectSinglePosition.getMainSpeed()).getFormattedPosition();
      formattedDeclination = new PlusMinusValue(celObjectSinglePosition.getDeviationPosition()).getFormattedPosition();
      formattedDeclSpeed = new PlusMinusValue(celObjectSinglePosition.getDeviationSpeed()).getFormattedPosition();
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
