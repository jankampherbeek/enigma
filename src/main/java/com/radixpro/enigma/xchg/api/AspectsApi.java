/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.handlers.AspectsHandler;
import com.radixpro.enigma.domain.analysis.IAnalyzedPair;
import com.radixpro.enigma.domain.astronpos.IPosition;
import com.radixpro.enigma.domain.config.AspectConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AspectsApi {

   private final AspectsHandler handler;

   /**
    * Use ApiFactory.createAspectsApi() for construction.
    *
    * @param handler The handler that prepares the analysis.
    */
   public AspectsApi(@NotNull final AspectsHandler handler) {
      this.handler = handler;
   }

   public List<IAnalyzedPair> analyzeAspects(@NotNull final List<IPosition> celObjects,
                                             @NotNull final List<IPosition> mundaneValues,
                                             @NotNull final AspectConfiguration config) {
      return handler.retrieveAspects(celObjects, mundaneValues, config);
   }

}
