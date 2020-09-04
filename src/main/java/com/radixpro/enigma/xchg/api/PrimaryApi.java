/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.handlers.PrimaryHandler;
import com.radixpro.enigma.domain.reqresp.PrimaryCalcRequest;
import com.radixpro.enigma.domain.reqresp.SimpleProgResponse;
import org.jetbrains.annotations.NotNull;


public class PrimaryApi {

   private final PrimaryHandler primaryHandler;

   public PrimaryApi(@NotNull final PrimaryHandler primaryHandler) {
      this.primaryHandler = primaryHandler;
   }

   public SimpleProgResponse calculatePrimary(final PrimaryCalcRequest request) {
      return primaryHandler.performCalculations(request);
   }
}
