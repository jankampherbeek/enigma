/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.analysis.handlers.TransitsAspectHandler;
import com.radixpro.enigma.be.calc.handlers.TransitsCalcHandler;
import com.radixpro.enigma.xchg.api.requests.TransitCalcRequest;
import com.radixpro.enigma.xchg.api.requests.TransitsAnalyzeRequest;
import com.radixpro.enigma.xchg.api.responses.SimpleProgResponse;
import com.radixpro.enigma.xchg.api.responses.TransitsAspectResponse;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * API for transits.
 */
public class TransitsApi {

   private final TransitsCalcHandler calcHandler;
   private final TransitsAspectHandler aspectHandler;

   /**
    * Constructor should be created by factory.
    *
    * @param calcHandler   handler for calculating transit positions. PRE: not null.
    * @param aspectHandler handler for analyzing tansit aspects. PRE: not null.
    * @see com.radixpro.enigma.xchg.api.factories.ApiProgFactory
    */
   public TransitsApi(final TransitsCalcHandler calcHandler,
                      final TransitsAspectHandler aspectHandler) {
      this.calcHandler = checkNotNull(calcHandler);
      this.aspectHandler = checkNotNull(aspectHandler);
   }

   public SimpleProgResponse calculateTransits(final TransitCalcRequest request) {
      return calcHandler.retrieveTransitPositions(request);
   }

   public TransitsAspectResponse defineAspects(final TransitsAnalyzeRequest request) {
      return aspectHandler.analyzeAspects(request);
   }

}
