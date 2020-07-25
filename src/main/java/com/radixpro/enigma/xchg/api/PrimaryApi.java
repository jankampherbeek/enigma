/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.calc.handlers.prog.PrimaryHandler;
import com.radixpro.enigma.xchg.api.requests.PrimaryCalcRequest;
import com.radixpro.enigma.xchg.api.responses.SimpleProgResponse;

import static com.google.common.base.Preconditions.checkNotNull;

public class PrimaryApi {

   private final PrimaryHandler primaryHandler;

   /**
    * Instantiate via factory.
    *
    * @param primaryHandler handler. PRE: not null.
    * @see ApiProgFactory
    */
   public PrimaryApi(final PrimaryHandler primaryHandler) {
      this.primaryHandler = checkNotNull(primaryHandler);
   }

   public SimpleProgResponse calculatePrimary(final PrimaryCalcRequest request) {
      return primaryHandler.performCalculations(request);
   }
}
