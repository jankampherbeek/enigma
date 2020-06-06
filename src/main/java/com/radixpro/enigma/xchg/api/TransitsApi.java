/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.calc.handlers.TransitsCalcHandler;
import com.radixpro.enigma.xchg.api.requests.TransitCalcRequest;
import com.radixpro.enigma.xchg.api.responses.SimpleProgResponse;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * API for transits.
 */
public class TransitsApi {

   private final TransitsCalcHandler calcHandler;

   /**
    * Constructor should be created by factory.
    *
    * @param calcHandler
    * @see com.radixpro.enigma.xchg.api.factories.ApiProgFactory
    */
   public TransitsApi(final TransitsCalcHandler calcHandler) {
      this.calcHandler = checkNotNull(calcHandler);
   }

   public SimpleProgResponse calculateTransits(final TransitCalcRequest request) {
      return calcHandler.retrieveTransitPositions(request);
   }

}
