/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.analysis.factories;

import com.radixpro.enigma.be.analysis.AspectsForRadix;
import com.radixpro.enigma.be.analysis.MidpointsForRadix;
import com.radixpro.enigma.be.analysis.ProgRadixAspects;
import com.radixpro.enigma.be.analysis.handlers.AspectsHandler;
import com.radixpro.enigma.be.analysis.handlers.MidpointsHandler;
import com.radixpro.enigma.be.analysis.handlers.TransitsAspectHandler;

public class AnalysisHandlerFactory {

   public AspectsHandler createAspectsHandler() {
      AspectsForRadix analyzer = new AspectsForRadix();
      return new AspectsHandler(analyzer);
   }

   public MidpointsHandler createMidpointsHandler() {
      MidpointsForRadix analyzer = new MidpointsForRadix();
      return new MidpointsHandler(analyzer);
   }

   public TransitsAspectHandler createTransitsAspectHandler() {
      ProgRadixAspects progRadixAspects = new ProgRadixAspects();
      return new TransitsAspectHandler(progRadixAspects);
   }

}
