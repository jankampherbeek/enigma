/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.factories;

import com.radixpro.enigma.be.analysis.factories.AnalysisHandlerFactory;
import com.radixpro.enigma.be.analysis.handlers.AspectsHandler;
import com.radixpro.enigma.be.analysis.handlers.MidpointsHandler;
import com.radixpro.enigma.xchg.api.AspectsApi;
import com.radixpro.enigma.xchg.api.MidpointsApi;

public class ApiAnalysisFactory {

   public AspectsApi createAspectsApi() {
      AspectsHandler handler = new AnalysisHandlerFactory().createAspectsHandler();
      return new AspectsApi(handler);
   }

   public MidpointsApi createMidpointsApi() {
      MidpointsHandler handler = new AnalysisHandlerFactory().createMidpointsHandler();
      return new MidpointsApi(handler);
   }

}
