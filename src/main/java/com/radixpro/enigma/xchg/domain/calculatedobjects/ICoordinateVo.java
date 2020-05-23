/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.calculatedobjects;

import com.radixpro.enigma.xchg.domain.CelCoordinateElementVo;

/**
 * Interface for value objects that contain coordinates
 */
public interface ICoordinateVo {

   /**
    * Retrieve the coordinates of a position.
    *
    * @return the position.
    */
   CelCoordinateElementVo getPosition();

}
