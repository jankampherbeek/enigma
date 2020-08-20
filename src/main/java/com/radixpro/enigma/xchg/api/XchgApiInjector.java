/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.AppScope;
import com.radixpro.enigma.be.calc.SeFrontend;
import com.radixpro.enigma.be.handlers.BeHandlersInjector;
import com.radixpro.enigma.be.persistency.BePersistencyInjector;

public class XchgApiInjector {

   public static AspectsApi injectAspectsApi(AppScope scope) {
      return new AspectsApi(BeHandlersInjector.injectAspectsHandler(scope));
   }

   public static CalculatedChartApi injectCalculatedChartApi(AppScope scope) {
      return new CalculatedChartApi(BeHandlersInjector.injectCalculatedChartHandler(scope));
   }

   public static DateTimeApi injectDateTimeApi(AppScope scope) {
      return new DateTimeApi(SeFrontend.getFrontend());
   }     // TODO move SeFrontend to scope

   public static MidpointsApi injectMidpointsApi(AppScope scope) {
      return new MidpointsApi(BeHandlersInjector.injectMidpointsHandler(scope));
   }

   public static InputDataFileApi injectInputDataFileApi(AppScope scope) {
      return new InputDataFileApi(BeHandlersInjector.injectInputDataFileHandler(scope));
   }

   public static PersistedChartDataApi injectPersistedChartDataApi(AppScope scope) {
      return new PersistedChartDataApi(BePersistencyInjector.injectChartDataDao(scope));
   }

   public static PersistedConfigurationApi injectPersistedConfigurationApi(AppScope scope) {
      return new PersistedConfigurationApi(BePersistencyInjector.injectConfigurationDao(scope));
   }

   public static PersistedPropertyApi injectPersistedPropertyApi(AppScope scope) {
      return new PersistedPropertyApi(BePersistencyInjector.injectPropertyDao(scope));
   }

   public static PrimaryApi injectPrimaryApi(AppScope scope) {
      return new PrimaryApi(BeHandlersInjector.injectPrimaryHandler(scope));
   }

   public static SecundaryApi injectSecundaryApi(AppScope scope) {
      return new SecundaryApi(BeHandlersInjector.injectEphProgCalcHandler(scope), BeHandlersInjector.injectSecundaryDateHandler(scope),
            BeHandlersInjector.injectProgAspectHandler(scope));
   }

   public static SolarReturnApi injectSolarReturnApi(AppScope scope) {
      return new SolarReturnApi(BeHandlersInjector.injectSolarReturnHandler(scope), scope.getRosetta());
   }

   public static TetenburgApi injectTetenburgApi(AppScope scope) {
      return new TetenburgApi(BeHandlersInjector.injectTetenburgHandler(scope));
   }

   public static TransitsApi injectTransitsApi(AppScope scope) {
      return new TransitsApi(BeHandlersInjector.injectEphProgCalcHandler(scope), BeHandlersInjector.injectProgAspectHandler(scope));
   }

   public static VersionApi injectVersionApi(AppScope scope) {
      return new VersionApi(BePersistencyInjector.injectVersionDao(scope));
   }

}
