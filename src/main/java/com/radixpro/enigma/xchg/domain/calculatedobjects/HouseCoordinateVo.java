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
 * Value object for a coordinate of a mundane point.
 */
public class HouseCoordinateVo implements ICoordinateVo {

   private final CelCoordinateElementVo position;

   /**
    * Constructor defines all elements (currently only one).
    *
    * @param position the values for the coordinates.
    */
   public HouseCoordinateVo(CelCoordinateElementVo position) {
      this.position = checkNotNull(position);
   }

   @Override
   public CelCoordinateElementVo getPosition() {
      return position;
   }
}
