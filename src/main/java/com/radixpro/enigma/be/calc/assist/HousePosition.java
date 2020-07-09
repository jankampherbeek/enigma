/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.calc.assist;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Dto for all positions for a house-cusp, or other mundane point.
 */
public class HousePosition {

   private final EquatorialPositionForHouses equatorialPositionForHouses;
   private final double[] azAlt;
   private final double longitude;

   /**
    * Constructor defines all members.
    *
    * @param longitude                   Longitude in degrees. Latitude is not defined as it is always zero.
    * @param equatorialPositionForHouses Equatorial coordinates.
    * @param azAlt                       Horizontal coordinates.
    */
   public HousePosition(final double longitude, final EquatorialPositionForHouses equatorialPositionForHouses,
                        final double[] azAlt) {
      this.longitude = longitude;
      this.equatorialPositionForHouses = checkNotNull(equatorialPositionForHouses);
      this.azAlt = checkNotNull(azAlt);
   }

   public EquatorialPositionForHouses getEquatorialPositionForHouses() {
      return this.equatorialPositionForHouses;
   }

   public double[] getEclipticHorizontalConverter() {
      return azAlt;
   }

   public double getLongitude() {
      return this.longitude;
   }
}
