/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.calculatedobjects;

import com.radixpro.enigma.xchg.domain.CelCoordinateElementVo;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Value object for a celestial coordinate.
 */
public class CelCoordinateVo implements ICoordinateVo {

   private final CelCoordinateElementVo position;
   private final CelCoordinateElementVo speed;

   /**
    * Constructor creates all properties.
    *
    * @param position the elements for the position. PRE: not null.
    * @param speed    the elements for the speed. PRE: not null.
    */
   public CelCoordinateVo(final CelCoordinateElementVo position, final CelCoordinateElementVo speed) {
      this.position = checkNotNull(position);
      this.speed = checkNotNull(speed);
   }

   @Override
   public CelCoordinateElementVo getPosition() {
      return position;
   }

   public CelCoordinateElementVo getSpeed() {
      return speed;
   }
}
