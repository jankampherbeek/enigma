/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.persistency;

import com.radixpro.enigma.AppScope;
import com.radixpro.enigma.be.persistency.mappers.BePersMappersInjector;
import com.radixpro.enigma.shared.converters.ShConvertersInjector;
import com.radixpro.enigma.ui.helpers.UiHelpersInjector;

public class BePersistencyInjector {

   private BePersistencyInjector() {
      // prevent instantiation
   }


   public static ChartDataDao injectChartDataDao() {
      return new ChartDataDao();
   }

   public static ConfigurationDao injectConfigurationDao() {
      return new ConfigurationDao();
   }

   public static DataFileDao injectDataFileDao(AppScope scope) {
      return new DataFileDao(injectJsonReader(scope), BePersMappersInjector.injectInputDataSetMapper(scope));
   }

   public static DataReaderCsv injectDataReaderCsv(AppScope scope) {
      return new DataReaderCsv(ShConvertersInjector.injectCsv2LocationConverter(scope), UiHelpersInjector.injectDateTimeJulianCreator(scope));
   }

   public static JsonReader injectJsonReader(AppScope scope) {
      return new JsonReader();
   }

   public static JsonWriter injectJsonWriter(AppScope scope) {
      return new JsonWriter();
   }

   public static PropertyDao injectPropertyDao() {
      return new PropertyDao();
   }

   public static VersionDao injectVersionDao() {
      return new VersionDao();
   }
}
