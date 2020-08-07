/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.analysis.handlers.AnHandlersFactory;
import com.radixpro.enigma.be.handlers.AspectsHandler;

/**
 * Factory for API's that handle calcualtions for a chart.
 */
public final class ApiFactory {

   private ApiFactory() {
      // prevent instantiation
   }


   public static AspectsApi createAspectsApi() {   // still in use by RadixWheel
      AspectsHandler handler = AnHandlersFactory.createAspectsHandler();
      return new AspectsApi(handler);
   }

}
