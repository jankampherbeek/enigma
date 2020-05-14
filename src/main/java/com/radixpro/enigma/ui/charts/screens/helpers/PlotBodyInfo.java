/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.charts.screens.helpers;

import com.radixpro.enigma.ui.shared.formatters.SexagesimalFormatter;
import com.radixpro.enigma.xchg.domain.CelestialObjects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Postion of celestial body with an additional plotposition Can be used in a sort.
 * The positions are defined by the angle from the ascendant.
 * This class is mutable: there is a setter for correctedAngle
 */
public class PlotBodyInfo {

   private final CelestialObjects celObject;
   private final double angleFromAsc;
   private final String posText;  // Text in degrees and minutes within a sign, the sign is not given.
   private double correctedAngle;

   /**
    * Constructor defines the initial values.
    *
    * @param celObject    the celestial object
    * @param angleFromAsc the angle in degrees from the ascendant, counted counter-clockwise
    * @param longitude    the ecliptical longitude
    */
   public PlotBodyInfo(final CelestialObjects celObject, final double angleFromAsc, final double longitude) {
      this.celObject = checkNotNull(celObject);
      this.angleFromAsc = angleFromAsc;
      this.correctedAngle = angleFromAsc;
      this.posText = formatText(longitude);
   }

   private String formatText(final double longitude) {
      double longitudeInSign = longitude % 30;
      SexagesimalFormatter formatter = new SexagesimalFormatter(2);
      return formatter.formatDm(longitudeInSign);
   }

   public CelestialObjects getCelObject() {
      return this.celObject;
   }

   public double getAngleFromAsc() {
      return this.angleFromAsc;
   }

   public String getPosText() {
      return this.posText;
   }

   public double getCorrectedAngle() {
      return this.correctedAngle;
   }

   public void setCorrectedAngle(double correctedAngle) {
      this.correctedAngle = correctedAngle;
   }
}
