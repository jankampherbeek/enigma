/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel;

import com.radixpro.enigma.references.CelestialObjects;
import com.radixpro.enigma.ui.shared.glyphs.CelObject2GlyphMapper;
import com.radixpro.enigma.ui.shared.presentationmodel.valuetypes.PlainDmsValue;
import com.radixpro.enigma.ui.shared.presentationmodel.valuetypes.PlusMinusValue;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Wrapper around HorizontalPosition (azimuth and altitude); enables the use in a tableview.
 */
public class PresentableHorizontalPosition {

   private String formattedAzimuth;
   private String formattedAltitude;
   private String celBodyGlyph;

   public PresentableHorizontalPosition(final CelestialObjects celObject, double[] azAlt) {
      createHorizontalPosition(checkNotNull(celObject), checkNotNull(azAlt));
   }

   private void createHorizontalPosition(final CelestialObjects celObject, double[] azAlt) {
      formattedAzimuth = new PlainDmsValue(azAlt[0]).getFormattedPosition();
      formattedAltitude = new PlusMinusValue(azAlt[1]).getFormattedPosition();
      celBodyGlyph = new CelObject2GlyphMapper().getGlyph(celObject);
   }

   public String getFormattedAzimuth() {
      return formattedAzimuth;
   }

   public String getFormattedAltitude() {
      return formattedAltitude;
   }

   public String getCelBodyGlyph() {
      return celBodyGlyph;
   }
}
