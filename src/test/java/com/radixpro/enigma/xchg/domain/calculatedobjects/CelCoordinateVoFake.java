/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.calculatedobjects;

import com.radixpro.enigma.xchg.domain.CelCoordinateElementVo;

/**
 * Fake for CelCoordinateVo
 */
public class CelCoordinateVoFake implements ICoordinateVo {

   @Override
   public CelCoordinateElementVo getPosition() {
      return new CelCoordinateElementVo(1.1, 2.2, 3.3);
   }
}
