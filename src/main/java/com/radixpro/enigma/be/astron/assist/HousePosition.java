/*
 * Jan Kampherbeek, (c) 2019.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.be.astron.assist;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Dto for all positions for a house-cusp, or other mundane point.
 */
public class HousePosition {

   private final EquatorialPosition equatorialPosition;
   private final HorizontalPosition horizontalPosition;
   private final double longitude;

   /**
    * Constructor defines all members.
    *
    * @param longitude          Longitude in degrees. Latitude is not defined as it is always zero.
    * @param equatorialPosition Equatorial coordinates.
    * @param horizontalPosition Hirzontal coordinates.
    */
   public HousePosition(final double longitude, final EquatorialPosition equatorialPosition,
                        final HorizontalPosition horizontalPosition) {
      this.longitude = longitude;
      this.equatorialPosition = checkNotNull(equatorialPosition);
      this.horizontalPosition = checkNotNull(horizontalPosition);
   }

   public EquatorialPosition getEquatorialPosition() {
      return this.equatorialPosition;
   }

   public HorizontalPosition getHorizontalPosition() {
      return this.horizontalPosition;
   }

   public double getLongitude() {
      return this.longitude;
   }
}
