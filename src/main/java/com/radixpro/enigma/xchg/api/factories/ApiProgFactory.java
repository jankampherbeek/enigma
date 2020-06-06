/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.factories;

import com.radixpro.enigma.be.calc.factories.ProgCalcHandlerFactory;
import com.radixpro.enigma.be.calc.handlers.TransitsCalcHandler;
import com.radixpro.enigma.xchg.api.TransitsApi;

/**
 * Factory for API's that take care of calculations and analysuis for proggressive positions
 */
public class ApiProgFactory {

   public TransitsApi createTransitsApi() {
      TransitsCalcHandler handler = new ProgCalcHandlerFactory().createTransitsCalcHandler();
      return new TransitsApi(handler);
   }

}
