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
import org.jetbrains.annotations.NotNull;

/**
 * Wrapper around HorizontalPosition (azimuth and altitude); enables the use in a tableview.
 */
public class PresentableHorizontalPosition {

   private String formattedAzimuth;
   private String formattedAltitude;
   private String celBodyGlyph;

   public PresentableHorizontalPosition(@NotNull final CelestialObjects celObject, @NotNull double[] azAlt) {
      createHorizontalPosition(celObject, azAlt);
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
