/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.factories;

import com.radixpro.enigma.be.analysis.factories.AnalysisHandlerFactory;
import com.radixpro.enigma.be.analysis.handlers.ProgAspectHandler;
import com.radixpro.enigma.be.calc.factories.ProgCalcFactory;
import com.radixpro.enigma.be.calc.handlers.EphProgCalcHandler;
import com.radixpro.enigma.be.calc.handlers.SecundaryDateHandler;
import com.radixpro.enigma.shared.Rosetta;
import com.radixpro.enigma.xchg.api.SecundaryApi;
import com.radixpro.enigma.xchg.api.SolarReturnApi;
import com.radixpro.enigma.xchg.api.TransitsApi;

/**
 * Factory for API's that take care of calculations and analysis for proggressive positions
 */
public class ApiProgFactory {

   public TransitsApi createTransitsApi() {
      EphProgCalcHandler calcHandler = new ProgCalcFactory().getTransitsCalcHandler();
      ProgAspectHandler aspectHandler = new AnalysisHandlerFactory().createTransitsAspectHandler();
      return new TransitsApi(calcHandler, aspectHandler);
   }

   public SecundaryApi createSecundaryApi() {
      EphProgCalcHandler calcHandler = new ProgCalcFactory().getTransitsCalcHandler();
      ProgAspectHandler aspectHandler = new AnalysisHandlerFactory().createTransitsAspectHandler();
      SecundaryDateHandler dateHandler = new SecundaryDateHandler();
      return new SecundaryApi(calcHandler, dateHandler, aspectHandler);
   }

   public SolarReturnApi getSolarReturnApi() {
      return new SolarReturnApi(new ProgCalcFactory().createSolarReturnHandler(), Rosetta.getRosetta());
   }

}
