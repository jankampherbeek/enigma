/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.handlers.MidpointsHandler;
import com.radixpro.enigma.domain.astronpos.IPosition;
import com.radixpro.enigma.xchg.domain.analysis.IAnalyzedPair;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class MidpointsApi {

   private final MidpointsHandler handler;

   /**
    * Use ApiFactory.createMidpointsApi() for construction.
    *
    * @param handler The handler that prepares the analysis.
    */
   public MidpointsApi(final MidpointsHandler handler) {
      this.handler = checkNotNull(handler);
   }

   public List<IAnalyzedPair> analyseMidpoints(final List<IPosition> celObjects, final List<IPosition> mundaneValues) {
      return handler.retrieveMidpoints(checkNotNull(celObjects), checkNotNull(mundaneValues));
   }

}
