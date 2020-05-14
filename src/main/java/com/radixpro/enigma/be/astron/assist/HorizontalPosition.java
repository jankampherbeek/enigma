/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.astron.assist;

import com.radixpro.enigma.be.astron.core.SeFrontend;
import com.radixpro.enigma.xchg.domain.Location;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Horizontal coordinates: azimuth and altitude. Converts from ecliptical coordinates to horizontal coordinates.
 */
public class HorizontalPosition {

   private double azimuth;
   private double altitude;

   /**
    * Calculate horizontal position.
    *
    * @param seFrontend Instance (singleton) of SeFrontend.
    * @param jdUt       Julian day number for UT.
    * @param eclCoord   ecliptical coördinates: index 0 = longitude, 1 = latitude, 2 = distance.
    * @param location   geographic longitude and latitude.
    * @param flags      settings for calculation.
    */
   public HorizontalPosition(final SeFrontend seFrontend, final double jdUt, final double[] eclCoord,
                             final Location location, final int flags) {
      calculate(checkNotNull(seFrontend), jdUt, checkNotNull(eclCoord), checkNotNull(location), flags);
   }

   /**
    * Constructor using known coordinates.
    *
    * @param azimuth  Azimuth in degrees.
    * @param altitude Altitude in degrees.
    */
   public HorizontalPosition(final double azimuth, final double altitude) {
      this.azimuth = azimuth;
      this.altitude = altitude;
   }

   private void calculate(final SeFrontend seFrontend, final double jdUt, final double[] eclCoord,
                          final Location location, final int flags) {
      SeFrontend seFrontendInstance = checkNotNull(seFrontend);
      double[] horizontalPosition = seFrontendInstance.getHorizontalPosition(jdUt, checkNotNull(eclCoord), checkNotNull(location), flags);
      azimuth = horizontalPosition[0];
      altitude = horizontalPosition[1];   // true altitude, index 2 = apparent altitude
   }

   public double getAzimuth() {
      return this.azimuth;
   }

   public double getAltitude() {
      return this.altitude;
   }
}
