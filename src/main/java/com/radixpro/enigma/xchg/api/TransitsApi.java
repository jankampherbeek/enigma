/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.calc.handlers.EphProgCalcHandler;
import com.radixpro.enigma.be.handlers.ProgAspectHandler;
import com.radixpro.enigma.xchg.api.requests.IProgCalcRequest;
import com.radixpro.enigma.xchg.api.requests.ProgAnalyzeRequest;
import com.radixpro.enigma.xchg.api.responses.EphProgAspectResponse;
import com.radixpro.enigma.xchg.api.responses.SimpleProgResponse;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * API for transits.
 */
public class TransitsApi {

   private final EphProgCalcHandler calcHandler;
   private final ProgAspectHandler aspectHandler;

   /**
    * Constructor should be created by factory.
    *
    * @param calcHandler   handler for calculating transit positions. PRE: not null.
    * @param aspectHandler handler for analyzing tansit aspects. PRE: not null.
    * @see ApiFactory
    */
   public TransitsApi(final EphProgCalcHandler calcHandler,
                      final ProgAspectHandler aspectHandler) {
      this.calcHandler = checkNotNull(calcHandler);
      this.aspectHandler = checkNotNull(aspectHandler);
   }

   public SimpleProgResponse calculateTransits(final IProgCalcRequest request) {
      return calcHandler.retrievePositions(request);
   }

   public EphProgAspectResponse defineAspects(final ProgAnalyzeRequest request) {
      return aspectHandler.analyzeAspects(request);
   }

}
