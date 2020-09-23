/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens.helpers;

import com.radixpro.enigma.xchg.api.XchgApiInjector;


public class ScreensHelpersInjector {

   private ScreensHelpersInjector() {
      // prevent instantiation
   }

   public static AspectsInConfig injectAspectsInConfig() {
      return new AspectsInConfig();
   }

   public static CelObjectsInConfig injectCelObjectsInConfig() {
      return new CelObjectsInConfig();
   }

   public static ChartDataHelper injectChartDataHelper() {
      return new ChartDataHelper();
   }

   public static PropertiesForConfig injectPropertiesForConfig() {
      return new PropertiesForConfig();
   }

   public static PropertiesTableForConfig injectPropertiesTableForConfig() {
      return new PropertiesTableForConfig();
   }

   public static RadixWheel injectRadixWheel() {
      return new RadixWheel(XchgApiInjector.injectAspectsApi());
   }

}
