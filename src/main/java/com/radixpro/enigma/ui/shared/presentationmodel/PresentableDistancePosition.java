/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel;

import com.radixpro.enigma.domain.astronpos.FullPointCoordinate;
import com.radixpro.enigma.references.CelestialObjects;
import com.radixpro.enigma.ui.shared.glyphs.CelObject2GlyphMapper;
import com.radixpro.enigma.ui.shared.presentationmodel.valuetypes.DecimalValue;
import org.jetbrains.annotations.NotNull;

/**
 * Wrapper around CelObjectSinglePosition for the distance values; enables the use in a tableview.
 */
public class PresentableDistancePosition {

   private String formattedDistance;
   private String formattedDistSpeed;
   private String celBodyGlyph;

   public PresentableDistancePosition(@NotNull final CelestialObjects celestialObject, @NotNull final FullPointCoordinate celObjectSinglePosition) {
      createPresentablePosition(celestialObject, celObjectSinglePosition);
   }

   private void createPresentablePosition(final CelestialObjects celestialObject,
                                          final FullPointCoordinate celObjectSinglePosition) {
      formattedDistance = new DecimalValue(celObjectSinglePosition.getPosition().getDistance()).getFormattedPosition();
      formattedDistSpeed = new DecimalValue(celObjectSinglePosition.getPosition().getDistance()).getFormattedPosition();
      celBodyGlyph = new CelObject2GlyphMapper().getGlyph(celestialObject);
   }

   public String getFormattedDistance() {
      return formattedDistance;
   }

   public String getFormattedDistSpeed() {
      return formattedDistSpeed;
   }

   public String getCelBodyGlyph() {
      return celBodyGlyph;
   }
}
