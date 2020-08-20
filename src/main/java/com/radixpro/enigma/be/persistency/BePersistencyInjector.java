/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.persistency;

import com.radixpro.enigma.AppScope;
import com.radixpro.enigma.shared.converters.ShConvertersInjector;

public class BePersistencyInjector {

   private BePersistencyInjector() {
      // prevent instantiation
   }


   public static ChartDataDao injectChartDataDao(AppScope scope) {
      return new ChartDataDao(scope.getAppDb());
   }

   public static ConfigurationDao injectConfigurationDao(AppScope scope) {
      return new ConfigurationDao(scope.getAppDb());
   }

   public static DataReaderCsv injectDataReaderCsv(AppScope scope) {
      return new DataReaderCsv(ShConvertersInjector.injectCsv2LocationConverter(scope), ShConvertersInjector.injectCsv2FullDateTimeConverter(scope));
   }

   public static JsonWriter injectJsonWriter(AppScope scope) {
      return new JsonWriter();
   }

   public static PropertyDao injectPropertyDao(AppScope scope) {
      return new PropertyDao(scope.getAppDb());
   }

   public static VersionDao injectVersionDao(AppScope scope) {
      return new VersionDao();
   }
}
