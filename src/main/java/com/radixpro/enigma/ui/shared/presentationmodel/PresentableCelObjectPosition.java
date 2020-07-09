/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel;

import com.radixpro.enigma.be.calc.main.CelObjectPosition;
import com.radixpro.enigma.ui.shared.glyphs.CelObject2GlyphMapper;
import com.radixpro.enigma.ui.shared.glyphs.Sign2GlyphMapper;
import com.radixpro.enigma.ui.shared.presentationmodel.valuetypes.*;
import com.radixpro.enigma.xchg.domain.CelObjectSinglePosition;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Wrapper around CelObjectSinglePosition; enables the use in a tableview.
 */
public class PresentableCelObjectPosition {

   private String formattedLongitude;
   private String formattedLongSpeed;
   private String formattedLatitude;
   private String formattedLatSpeed;
   private String signGlyph;
   private String celBodyGlyph;
   private String formattedRightAscension;
   private String formattedRaSpeed;
   private String formattedDeclination;
   private String formattedDeclSpeed;
   private String formattedAzimuth;
   private String formattedAltitude;
   private String formattedDistance;
   private String formattedDistSpeed;

   /**
    * Constructor populates all properties.
    *
    * @param celObjectPosition contains longitude, latitude, right ascension, declination, radv and daily speeds.
    *                          Also knows which celestial object we are showing.
    * @param horPos            contains azimuth and altitude
    */
   public PresentableCelObjectPosition(final CelObjectPosition celObjectPosition,
                                       final double[] horPos) {
      createPresentablePosition(checkNotNull(celObjectPosition), checkNotNull(horPos));
   }

   private void createPresentablePosition(final CelObjectPosition celObjectPos,
                                          final double[] horPos) {
      checkNotNull(celObjectPos);
      checkNotNull(horPos);

      CelObjectSinglePosition eclPos = celObjectPos.getEclipticalPosition();
      CelObjectSinglePosition equPos = celObjectPos.getEquatorialPosition();
      double mainEclPos = eclPos.getMainPosition();
      LongWithGlyph longWithGlyph = new LongAndGlyphValue(mainEclPos).getLongWithGlyph();
      formattedLongitude = longWithGlyph.getPosition();
      signGlyph = new Sign2GlyphMapper().getGlyph(longWithGlyph.getSignIndex());
      celBodyGlyph = new CelObject2GlyphMapper().getGlyph(celObjectPos.getCelestialBody());
      formattedLongSpeed = new PlusMinusValue(eclPos.getMainSpeed()).getFormattedPosition();
      formattedLatitude = new PlusMinusValue(eclPos.getDeviationPosition()).getFormattedPosition();
      formattedLatSpeed = new PlusMinusValue(eclPos.getDeviationSpeed()).getFormattedPosition();
      formattedRightAscension = new PlainDmsValue(equPos.getMainPosition()).getFormattedPosition();
      formattedRaSpeed = new PlusMinusValue(equPos.getMainSpeed()).getFormattedPosition();
      formattedDeclination = new PlusMinusValue(equPos.getDeviationPosition()).getFormattedPosition();
      formattedDeclSpeed = new PlusMinusValue(equPos.getDeviationSpeed()).getFormattedPosition();
      formattedAzimuth = new PlainDmsValue(horPos[0]).getFormattedPosition();
      formattedAltitude = new PlusMinusValue(horPos[1]).getFormattedPosition();
      formattedDistance = new DecimalValue(eclPos.getDistancePosition()).getFormattedPosition();
      formattedDistSpeed = new DecimalValue(eclPos.getDistanceSpeed()).getFormattedPosition();
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

   public String getFormattedAzimuth() {
      return formattedAzimuth;
   }

   public String getFormattedAltitude() {
      return formattedAltitude;
   }

   public String getFormattedDistance() {
      return formattedDistance;
   }

   public String getFormattedDistSpeed() {
      return formattedDistSpeed;
   }
}
