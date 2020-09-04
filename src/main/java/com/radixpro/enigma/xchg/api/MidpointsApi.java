/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.handlers.MidpointsHandler;
import com.radixpro.enigma.domain.analysis.IAnalyzedPair;
import com.radixpro.enigma.domain.astronpos.IPosition;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MidpointsApi {

   private final MidpointsHandler handler;

   public MidpointsApi(@NotNull final MidpointsHandler handler) {
      this.handler = handler;
   }

   public List<IAnalyzedPair> analyseMidpoints(@NotNull final List<IPosition> celObjects, @NotNull final List<IPosition> mundaneValues) {
      return handler.retrieveMidpoints(celObjects, mundaneValues);
   }

}
