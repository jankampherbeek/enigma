/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens;

import com.radixpro.enigma.AppScope;
import com.radixpro.enigma.ui.screens.blocks.ScreensBlocksInjector;
import com.radixpro.enigma.ui.screens.helpers.ScreensHelpersInjector;
import com.radixpro.enigma.ui.validators.UiValidatorsInjector;
import com.radixpro.enigma.xchg.api.XchgApiInjector;


public class UiScreensInjector {

   private UiScreensInjector() {
      // prevent instantiation
   }

   public static ChartsAspects injectChartsAspects(AppScope scope) {
      return new ChartsAspects(scope.getSessionState(), scope.getRosetta(), XchgApiInjector.injectAspectsApi(scope),
            ScreensHelpersInjector.injectChartDataHelper(scope));
   }

   public static ChartsInput injectChartsInput(AppScope scope) {
      return new ChartsInput(scope.getRosetta(), XchgApiInjector.injectPersistedChartDataApi(scope), UiValidatorsInjector.injectValidatedChartName(scope),
            UiValidatorsInjector.injectValidatedDate(scope), UiValidatorsInjector.injectValidatedTime(scope),
            UiValidatorsInjector.injectValidatedLongitude(scope), UiValidatorsInjector.injectValidatedLatitude(scope));
   }

   public static ChartsMidpoints injectChartsMidpoints(AppScope scope) {
      return new ChartsMidpoints(scope.getSessionState(), scope.getRosetta(), XchgApiInjector.injectMidpointsApi(scope),
            ScreensHelpersInjector.injectChartDataHelper(scope));
   }

   public static ChartsSearch injectChartsSearch(AppScope scope) {
      return new ChartsSearch(scope.getRosetta(), XchgApiInjector.injectPersistedChartDataApi(scope));
   }

   public static ChartsStart injectChartsStart(AppScope scope) {
      return new ChartsStart(scope.getRosetta(), scope.getSessionState(), XchgApiInjector.injectCalculatedChartApi(scope), injectChartsTetenburg(scope),
            injectChartsAspects(scope), injectChartsMidpoints(scope), injectChartsTransitsInput(scope), injectChartsSearch(scope), injectChartsInput(scope),
            XchgApiInjector.injectPersistedChartDataApi(scope));
   }

   public static ChartsTransitsInput injectChartsTransitsInput(AppScope scope) {
      return new ChartsTransitsInput(ScreensBlocksInjector.injectProgMetaInputBLock(scope), ScreensBlocksInjector.injectLocationInputBlock(scope),
            ScreensBlocksInjector.injectDateTimeInputBlock(scope));
   }

   public static ChartsTetenburg injectChartsTetenburg(AppScope scope) {
      return new ChartsTetenburg(scope.getSessionState(), scope.getRosetta(), XchgApiInjector.injectTetenburgApi(scope),
            UiValidatorsInjector.injectValidatedDate(scope));
   }

}
