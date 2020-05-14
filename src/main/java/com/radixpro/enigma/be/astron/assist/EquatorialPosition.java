/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.astron.assist;

import com.radixpro.enigma.be.astron.core.SeFrontend;
import com.radixpro.enigma.be.astron.main.Obliquity;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Equatorial position: right ascension and declination.
 * Converts from ecliptic coördinates to equatorial coördinates. Can only be used for house positions as a zero latitude
 * is assumed. There is no need for conversion of planetary positions as they already contain equatorial values.
 */
public class EquatorialPosition {

   private double rightAscension;
   private double declination;

   /**
    * Constructor for positions that need to be calculated.
    *
    * @param seFrontend Instance (singleton) of SeFrontend.
    * @param longitude  THe longitude in degrees.
    * @param jdUt       Julian Day for UT.
    */
   public EquatorialPosition(final SeFrontend seFrontend, final double longitude, final double jdUt) {
      calculatePositions(checkNotNull(seFrontend), longitude, jdUt);
   }

   /**
    * Constructor for already known equaotrila positions.
    *
    * @param rightAscension Right Ascendsion in degrees.
    * @param declination    Decliantion in de degrees.
    */
   public EquatorialPosition(final double rightAscension, final double declination) {
      this.rightAscension = rightAscension;
      this.declination = declination;
   }

   private void calculatePositions(final SeFrontend seFrontend, final double longitude, final double jdUt) {
      SeFrontend seFrontendInstance = checkNotNull(seFrontend);
      double latitude = 0.0;
      double distance = 1.0;
      final double[] eclipticPositions = {longitude, latitude, distance};
      double obliquity = new Obliquity(seFrontendInstance, jdUt).getTrueObliquity();
      final double[] equatorialPositions = seFrontendInstance.convertToEquatorial(eclipticPositions, obliquity);
      rightAscension = equatorialPositions[0];
      declination = equatorialPositions[1];
   }

   public double getRightAscension() {
      return this.rightAscension;
   }

   public double getDeclination() {
      return this.declination;
   }
}

