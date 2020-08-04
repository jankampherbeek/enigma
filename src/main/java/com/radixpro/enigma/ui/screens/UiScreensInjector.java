/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens;

import com.radixpro.enigma.AppScope;
import com.radixpro.enigma.ui.screens.helpers.ScreensHelpersInjector;
import com.radixpro.enigma.xchg.api.XchgApiInjector;


public class UiScreensInjector {

   private UiScreensInjector() {
      // prevent instantiation
   }

   public static ChartsAspects injectChartsAspects(AppScope scope) {
      return new ChartsAspects(scope.getSessionState(), scope.getRosetta(), XchgApiInjector.injectAspectsApi(scope),
            ScreensHelpersInjector.injectChartDataHelper(scope));
   }

   public static ChartsStart injectChartsStart(AppScope scope) {
      return new ChartsStart(scope.getRosetta(), scope.getSessionState(), XchgApiInjector.injectCalculatedChartApi(scope), injectChartsTetenburg(scope),
            injectChartsAspects(scope), injectChartsMidpoints(scope));
   }

   public static ChartsMidpoints injectChartsMidpoints(AppScope scope) {
      return new ChartsMidpoints(scope.getSessionState(), scope.getRosetta(), XchgApiInjector.injectMidpointsApi(scope),
            ScreensHelpersInjector.injectChartDataHelper(scope));
   }

   public static ChartsTetenburg injectChartsTetenburg(AppScope scope) {
      return new ChartsTetenburg(scope.getSessionState(), scope.getRosetta(), XchgApiInjector.injectTetenburgApi(scope));
   }

}
