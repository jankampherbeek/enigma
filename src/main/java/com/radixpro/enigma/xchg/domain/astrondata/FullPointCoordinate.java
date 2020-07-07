/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.astrondata;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * All coordinates for position and speed within a specific coordiantesystem.
 */
public class FullPointCoordinate {

   private final CoordinateSet3D position;
   private final CoordinateSet3D speed;

   /**
    * Constructor defines all properties.
    *
    * @param position Momentary position using 3 coordinates. PRE: not null.
    * @param speed    Speed using 3 coordinates. PRE: not null.
    */
   public FullPointCoordinate(final CoordinateSet3D position, final CoordinateSet3D speed) {
      this.position = checkNotNull(position);
      this.speed = checkNotNull(speed);
   }

   public CoordinateSet3D getPosition() {
      return position;
   }

   public CoordinateSet3D getSpeed() {
      return speed;
   }
}
