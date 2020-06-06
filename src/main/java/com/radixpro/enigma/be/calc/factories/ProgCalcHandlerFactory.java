/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.factories;

import com.radixpro.enigma.be.calc.core.SeFrontend;
import com.radixpro.enigma.be.calc.handlers.TransitsCalcHandler;

/**
 * Factory for handlers that take care of calculations for progressive techniques.
 */
public class ProgCalcHandlerFactory {

   public TransitsCalcHandler createTransitsCalcHandler() {
      return new TransitsCalcHandler(SeFrontend.getFrontend());
   }

}
