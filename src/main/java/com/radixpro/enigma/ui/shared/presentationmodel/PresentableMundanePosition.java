/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel;

import com.radixpro.enigma.ui.shared.glyphs.Sign2GlyphMapper;
import com.radixpro.enigma.ui.shared.presentationmodel.valuetypes.LongAndGlyphValue;
import com.radixpro.enigma.ui.shared.presentationmodel.valuetypes.LongWithGlyph;
import com.radixpro.enigma.ui.shared.presentationmodel.valuetypes.PlainDmsValue;
import com.radixpro.enigma.ui.shared.presentationmodel.valuetypes.PlusMinusValue;
import com.radixpro.enigma.xchg.domain.astrondata.MundanePosition;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Wrapper around HousePosition; enables the use in a tableview.
 */
public class PresentableMundanePosition {

   private final String name;
   private String formattedLongitude;
   private String formattedRa;
   private String formattedDeclination;
   private String formattedAzimuth;
   private String formattedAltitude;
   private String signGlyph;

   /**
    * The constructor populates all properties.
    *
    * @param name     Name for the mundane position. Possibly a number for the cusp, an acronym for vertex etc.
    * @param position An instance of MundanePosition that contains the data that must be presented.
    */
   public PresentableMundanePosition(final String name, final MundanePosition position) {
      this.name = checkNotNull(name);
      createMundanePosition(checkNotNull(position));
   }

   private void createMundanePosition(final MundanePosition position) {
      LongWithGlyph longWithGlyph = new LongAndGlyphValue(position.getLongitude()).getLongWithGlyph();
      formattedLongitude = longWithGlyph.getPosition();
      signGlyph = new Sign2GlyphMapper().getGlyph(longWithGlyph.getSignIndex());
      formattedRa = new PlainDmsValue(position.getEqPos().getMainCoord()).getFormattedPosition();
      formattedDeclination = new PlusMinusValue(position.getEqPos().getDeviation()).getFormattedPosition();
      formattedAzimuth = new PlainDmsValue(position.getHorPos().getMainCoord()).getFormattedPosition();
      formattedAltitude = new PlusMinusValue(position.getHorPos().getDeviation()).getFormattedPosition();
   }

   public String getName() {
      return name;
   }

   public String getFormattedLongitude() {
      return formattedLongitude;
   }

   public String getFormattedRa() {
      return formattedRa;
   }

   public String getFormattedDeclination() {
      return formattedDeclination;
   }

   public String getFormattedAzimuth() {
      return formattedAzimuth;
   }

   public String getFormattedAltitude() {
      return formattedAltitude;
   }

   public String getSignGlyph() {
      return signGlyph;
   }
}
