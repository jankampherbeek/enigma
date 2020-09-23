/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.calc.SeFrontend;
import com.radixpro.enigma.be.handlers.BeHandlersInjector;
import com.radixpro.enigma.be.persistency.BePersistencyInjector;

public class XchgApiInjector {

   public static AspectsApi injectAspectsApi() {
      return new AspectsApi(BeHandlersInjector.injectAspectsHandler());
   }

   public static CalculatedChartApi injectCalculatedChartApi() {
      return new CalculatedChartApi(BeHandlersInjector.injectCalculatedChartHandler());
   }

   public static DateTimeApi injectDateTimeApi() {
      return new DateTimeApi(SeFrontend.INSTANCE);
   }     // TODO move SeFrontend to scope

   public static MidpointsApi injectMidpointsApi() {
      return new MidpointsApi(BeHandlersInjector.injectMidpointsHandler());
   }

   public static InputDataFileApi injectInputDataFileApi() {
      return new InputDataFileApi(BeHandlersInjector.injectInputDataFileHandler());
   }

   public static PersistedChartDataApi injectPersistedChartDataApi() {
      return new PersistedChartDataApi(BePersistencyInjector.injectChartDataDao());
   }

   public static PersistedConfigurationApi injectPersistedConfigurationApi() {
      return new PersistedConfigurationApi(BePersistencyInjector.injectConfigurationDao());
   }

   public static PersistedDataFileApi injectPersistedDataFileApi() {
      return new PersistedDataFileApi(BeHandlersInjector.injectDataFileHandler());
   }

   public static PersistedPropertyApi injectPersistedPropertyApi() {
      return new PersistedPropertyApi(BePersistencyInjector.injectPropertyDao());
   }

   public static PrimaryApi injectPrimaryApi() {
      return new PrimaryApi(BeHandlersInjector.injectPrimaryHandler());
   }

   public static SecundaryApi injectSecundaryApi() {
      return new SecundaryApi(BeHandlersInjector.injectEphProgCalcHandler(), BeHandlersInjector.injectSecundaryDateHandler(),
            BeHandlersInjector.injectProgAspectHandler());
   }

   public static SolarReturnApi injectSolarReturnApi() {
      return new SolarReturnApi(BeHandlersInjector.injectSolarReturnHandler());
   }

   public static TetenburgApi injectTetenburgApi() {
      return new TetenburgApi(BeHandlersInjector.injectTetenburgHandler());
   }

   public static TransitsApi injectTransitsApi() {
      return new TransitsApi(BeHandlersInjector.injectEphProgCalcHandler(), BeHandlersInjector.injectProgAspectHandler());
   }

   public static VersionApi injectVersionApi() {
      return new VersionApi(BePersistencyInjector.injectVersionDao());
   }

}
