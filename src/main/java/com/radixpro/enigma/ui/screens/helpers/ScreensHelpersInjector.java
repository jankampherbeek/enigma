/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens.helpers;

import com.radixpro.enigma.AppScope;
import com.radixpro.enigma.xchg.api.XchgApiInjector;


public class ScreensHelpersInjector {

   private ScreensHelpersInjector() {
      // prevent instantiation
   }

   public static AspectsInConfig injectAspectsInConfig(AppScope scope) {
      return new AspectsInConfig(scope.getRosetta());
   }

   public static CelObjectsInConfig injectCelObjectsInConfig(AppScope scope) {
      return new CelObjectsInConfig(scope.getRosetta());
   }

   public static ChartDataHelper injectChartDataHelper() {
      return new ChartDataHelper();
   }

   public static PropertiesForConfig injectPropertiesForConfig(AppScope scope) {
      return new PropertiesForConfig(scope.getRosetta());
   }

   public static PropertiesTableForConfig injectPropertiesTableForConfig(AppScope scope) {
      return new PropertiesTableForConfig(scope.getRosetta());
   }

   public static RadixWheel injectRadixWheel() {
      return new RadixWheel(XchgApiInjector.injectAspectsApi());
   }

}
