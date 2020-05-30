/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.analysis.AspectsHandler;
import com.radixpro.enigma.xchg.domain.analysis.IAnalyzedPair;
import com.radixpro.enigma.xchg.domain.calculatedobjects.IObjectVo;
import com.radixpro.enigma.xchg.domain.config.AspectConfiguration;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class AspectsApi {

   private final AspectsHandler handler;

   /**
    * Use ApiFactory.createAspectsApi() for construction.
    *
    * @param handler The handler that prepares the analysis.
    */
   public AspectsApi(final AspectsHandler handler) {
      this.handler = checkNotNull(handler);
   }

   public List<IAnalyzedPair> analyzeAspects(final List<IObjectVo> celObjects,
                                             final List<IObjectVo> mundaneValues,
                                             final AspectConfiguration config) {
      return handler.retrieveAspects(checkNotNull(celObjects), checkNotNull(mundaneValues), checkNotNull(config));
   }

}
