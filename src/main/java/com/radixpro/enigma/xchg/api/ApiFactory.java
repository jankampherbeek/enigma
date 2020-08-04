/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.analysis.handlers.AnHandlersFactory;
import com.radixpro.enigma.be.calc.handlers.CaHandlersFactory;
import com.radixpro.enigma.be.calc.handlers.EphProgCalcHandler;
import com.radixpro.enigma.be.handlers.AspectsHandler;
import com.radixpro.enigma.be.handlers.ProgAspectHandler;
import com.radixpro.enigma.be.handlers.SecundaryDateHandler;
import com.radixpro.enigma.shared.common.Rosetta;

/**
 * Factory for API's that handle calcualtions for a chart.
 */
public final class ApiFactory {

   private ApiFactory() {
      // prevent instantiation
   }

   public static CalculatedChartApi getCalculatedChartApi() {
      return new CalculatedChartApi(CaHandlersFactory.getCalculatedChartHandler());
   }

   public static AspectsApi createAspectsApi() {
      AspectsHandler handler = AnHandlersFactory.createAspectsHandler();
      return new AspectsApi(handler);
   }
//
//   public static MidpointsApi createMidpointsApi() {
//      MidpointsHandler handler = AnHandlersFactory.createMidpointsHandler();
//      return new MidpointsApi(handler);
//   }

   public static PersistedConfigurationApi getPersistedConfigurationApi() {
      return new PersistedConfigurationApi();
   }

   public static TransitsApi getTransitsApi() {
      EphProgCalcHandler calcHandler = CaHandlersFactory.getTransitsCalcHandler();
      ProgAspectHandler aspectHandler = AnHandlersFactory.createTransitsAspectHandler();
      return new TransitsApi(calcHandler, aspectHandler);
   }

   public static SecundaryApi getSecundaryApi() {
      EphProgCalcHandler calcHandler = CaHandlersFactory.getTransitsCalcHandler();
      ProgAspectHandler aspectHandler = AnHandlersFactory.createTransitsAspectHandler();
      SecundaryDateHandler dateHandler = new SecundaryDateHandler();
      return new SecundaryApi(calcHandler, dateHandler, aspectHandler);
   }

   public static SolarReturnApi getSolarReturnApi() {
      return new SolarReturnApi(CaHandlersFactory.getSolarReturnHandler(), Rosetta.getRosetta());
   }

//   public static TetenburgApi getTetenburgApi() {
//      return new TetenburgApi(CaHandlersFactory.getTetenburgHandler());
//   }

   public static PrimaryApi getPrimaryApi() {
      return new PrimaryApi(CaHandlersFactory.getPrimaryHandler());
   }

}
