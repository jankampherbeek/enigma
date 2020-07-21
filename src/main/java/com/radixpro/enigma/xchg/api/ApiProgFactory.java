/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.analysis.factories.AnalysisHandlerFactory;
import com.radixpro.enigma.be.analysis.handlers.ProgAspectHandler;
import com.radixpro.enigma.be.calc.handlers.prog.EphProgCalcHandler;
import com.radixpro.enigma.be.calc.handlers.prog.HandlerProgFactory;
import com.radixpro.enigma.be.calc.handlers.prog.SecundaryDateHandler;
import com.radixpro.enigma.shared.Rosetta;

/**
 * Factory for API's that take care of calculations and analysis for proggressive positions
 */
public class ApiProgFactory {

   public TransitsApi getTransitsApi() {
      EphProgCalcHandler calcHandler = new HandlerProgFactory().getTransitsCalcHandler();
      ProgAspectHandler aspectHandler = new AnalysisHandlerFactory().createTransitsAspectHandler();
      return new TransitsApi(calcHandler, aspectHandler);
   }

   public SecundaryApi getSecundaryApi() {
      EphProgCalcHandler calcHandler = new HandlerProgFactory().getTransitsCalcHandler();
      ProgAspectHandler aspectHandler = new AnalysisHandlerFactory().createTransitsAspectHandler();
      SecundaryDateHandler dateHandler = new SecundaryDateHandler();
      return new SecundaryApi(calcHandler, dateHandler, aspectHandler);
   }

   public SolarReturnApi getSolarReturnApi() {
      return new SolarReturnApi(new HandlerProgFactory().getSolarReturnHandler(), Rosetta.getRosetta());
   }

   public TetenburgApi getTetenburgApi() {
      return new TetenburgApi(new HandlerProgFactory().getTetenburgHandler());
   }

   public PrimaryApi getPrimaryApi() {
      return new PrimaryApi(new HandlerProgFactory().getPrimaryHandler());
   }

}
