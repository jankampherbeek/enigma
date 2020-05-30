/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.analysis.AnalysisHandlerFactory;
import com.radixpro.enigma.be.analysis.AspectsHandler;
import com.radixpro.enigma.be.analysis.MidpointsHandler;

public class ApiFactory {

   public AspectsApi createAspectsApi() {
      AspectsHandler handler = new AnalysisHandlerFactory().createAspectsHandler();
      return new AspectsApi(handler);
   }

   public MidpointsApi createMidpointsApi() {
      MidpointsHandler handler = new AnalysisHandlerFactory().createMidpointsHandler();
      return new MidpointsApi(handler);
   }

}
