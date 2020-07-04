/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.calc.assist;

import com.radixpro.enigma.be.calc.converters.EclipticEquatorialConversions;
import com.radixpro.enigma.be.calc.core.SeFrontend;
import com.radixpro.enigma.be.calc.main.Obliquity;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Equatorial position: right ascension and declination.
 * Converts from ecliptic coordinates to equatorial coordinates. Can only be used for house positions as a zero latitude
 * is assumed. There is no need for conversion of planetary positions as they already contain equatorial values.
 */
public class EquatorialPositionForHouses {

   private double rightAscension;
   private double declination;
   private EclipticEquatorialConversions conversions;

   /**
    * Constructor should be called via EquatorialPositionForHousesFactory.
    *
    * @param seFrontend Instance (singleton) of SeFrontend.
    * @param longitude  THe longitude in degrees.
    * @param jdUt       Julian Day for UT.
    * @see com.radixpro.enigma.be.calc.factories.EquatorialPositionForHousesFactory
    */
   public EquatorialPositionForHouses(final SeFrontend seFrontend, final EclipticEquatorialConversions conversions, final double longitude, final double jdUt) {
      checkNotNull(seFrontend);
      this.conversions = checkNotNull(conversions);
      calculatePositions(checkNotNull(seFrontend), longitude, jdUt);
   }

   /**
    * Constructor for already known equatorial positions.
    *
    * @param rightAscension Right Ascension in degrees.
    * @param declination    Declination in degrees.
    */
   public EquatorialPositionForHouses(final double rightAscension, final double declination) {
      this.rightAscension = rightAscension;
      this.declination = declination;
   }

   private void calculatePositions(final SeFrontend seFrontend, final double longitude, final double jdUt) {
      SeFrontend seFrontendInstance = checkNotNull(seFrontend);
      double latitude = 0.0;
      double distance = 1.0;
      final double[] eclipticPositions = {longitude, latitude, distance};
      double obliquity = new Obliquity(seFrontendInstance, jdUt).getTrueObliquity();
      final double[] equatorialPositions = conversions.convertToEquatorial(eclipticPositions, obliquity);
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

