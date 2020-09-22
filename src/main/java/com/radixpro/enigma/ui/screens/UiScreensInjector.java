/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens;

import com.radixpro.enigma.AppScope;
import com.radixpro.enigma.ui.helpers.UiHelpersInjector;
import com.radixpro.enigma.ui.screens.blocks.ScreensBlocksInjector;
import com.radixpro.enigma.ui.screens.helpers.ScreensHelpersInjector;
import com.radixpro.enigma.ui.validators.UiValidatorsInjector;
import com.radixpro.enigma.xchg.api.XchgApiInjector;
import javafx.stage.DirectoryChooser;


public class UiScreensInjector {

   private UiScreensInjector() {
      // prevent instantiation
   }

   public static ChartsAspects injectChartsAspects(AppScope scope) {
      return new ChartsAspects(scope.getSessionState(), scope.getRosetta(), XchgApiInjector.injectAspectsApi(scope),
            ScreensHelpersInjector.injectChartDataHelper(scope));
   }

   public static ChartsDrawing2d injectChartsDrawing2d(AppScope scope) {
      return new ChartsDrawing2d(ScreensHelpersInjector.injectRadixWheel(scope), scope.getRosetta(), scope.getSessionState());
   }

   public static ChartsInput injectChartsInput(AppScope scope) {
      return new ChartsInput(XchgApiInjector.injectPersistedChartDataApi(), UiValidatorsInjector.injectValidatedChartName(scope),
            UiValidatorsInjector.injectValidatedDate(scope), UiValidatorsInjector.injectValidatedTime(scope),
            UiValidatorsInjector.injectValidatedLongitude(scope), UiValidatorsInjector.injectValidatedLatitude(scope),
            UiHelpersInjector.injectDateTimeJulianCreator(scope));
   }

   public static ChartsMidpoints injectChartsMidpoints(AppScope scope) {
      return new ChartsMidpoints(scope.getSessionState(), XchgApiInjector.injectMidpointsApi(scope),
            ScreensHelpersInjector.injectChartDataHelper(scope));
   }

   public static ChartsSearch injectChartsSearch(AppScope scope) {
      return new ChartsSearch(scope.getRosetta(), XchgApiInjector.injectPersistedChartDataApi());
   }

   public static ChartsStart injectChartsStart(AppScope scope) {
      return new ChartsStart(scope.getRosetta(), scope.getSessionState(), XchgApiInjector.injectCalculatedChartApi(scope), injectChartsTetenburg(scope),
            injectChartsAspects(scope), injectChartsMidpoints(scope), injectChartsTransitsInput(scope), injectChartsSearch(scope), injectChartsInput(scope),
            XchgApiInjector.injectPersistedChartDataApi(), XchgApiInjector.injectPersistedConfigurationApi(),
            XchgApiInjector.injectPersistedPropertyApi(), UiScreensInjector.injectConfigOverview(scope),
            ScreensHelpersInjector.injectPropertiesForConfig(scope), ScreensHelpersInjector.injectCelObjectsInConfig(scope),
            ScreensHelpersInjector.injectAspectsInConfig(scope), ScreensHelpersInjector.injectPropertiesTableForConfig(scope), injectChartsDrawing2d(scope));
   }

   public static ChartsTransitsInput injectChartsTransitsInput(AppScope scope) {
      return new ChartsTransitsInput(ScreensBlocksInjector.injectProgMetaInputBLock(scope), ScreensBlocksInjector.injectLocationInputBlock(scope),
            ScreensBlocksInjector.injectDateTimeInputBlock(scope), XchgApiInjector.injectTransitsApi(scope));
   }

   public static ChartsTetenburg injectChartsTetenburg(AppScope scope) {
      return new ChartsTetenburg(scope.getSessionState(), scope.getRosetta(), XchgApiInjector.injectTetenburgApi(scope),
            UiValidatorsInjector.injectValidatedDate(scope), UiHelpersInjector.injectDateTimeJulianCreator(scope));
   }

   public static ConfigDetails injectConfigDetails(AppScope scope) {
      return new ConfigDetails(ScreensHelpersInjector.injectPropertiesForConfig(scope),
            ScreensHelpersInjector.injectPropertiesTableForConfig(scope), ScreensHelpersInjector.injectCelObjectsInConfig(scope),
            ScreensHelpersInjector.injectAspectsInConfig(scope), scope.getSessionState());
   }

   public static ConfigEdit injectConfigEdit(AppScope scope) {
      return new ConfigEdit(XchgApiInjector.injectPersistedConfigurationApi(), scope.getRosetta(), scope.getSessionState());
   }

   public static ConfigNew injectConfigNew(AppScope scope) {
      return new ConfigNew(scope.getRosetta(), XchgApiInjector.injectPersistedConfigurationApi(), UiValidatorsInjector.injectConfigNameValidator(scope),
            scope.getSessionState());
   }

   public static ConfigOverview injectConfigOverview(AppScope scope) {
      return new ConfigOverview(XchgApiInjector.injectPersistedConfigurationApi(), XchgApiInjector.injectPersistedPropertyApi(),
            injectConfigNew(scope), injectConfigEdit(scope), injectConfigDetails(scope), scope.getRosetta(), scope.getSessionState());
   }

   public static StatsDataNew injectStatsDataNew(AppScope scope) {
      return new StatsDataNew(ScreensBlocksInjector.injectNameDescriptionInputBlock(scope), XchgApiInjector.injectInputDataFileApi(scope),
            XchgApiInjector.injectPersistedPropertyApi());
   }

   public static StatsDataSearch injectStatsDataSearch(AppScope scope) {
      return new StatsDataSearch(XchgApiInjector.injectPersistedDataFileApi(scope), scope.getRosetta());
   }

   public static StatsProjNew injectStatsProjNew(AppScope scope) {
      return new StatsProjNew(ScreensBlocksInjector.injectNameDescriptionInputBlock(scope), ScreensBlocksInjector.injectBaseConfigInputBlock(scope),
            injectStatsDataSearch(scope));
   }

   public static StatsProjSearch injectStatsProjSearch(AppScope scope) {
      return new StatsProjSearch(scope);
   }

   public static StatsStart injectStatsStart(AppScope scope) {
      return new StatsStart(scope.getRosetta(), ScreensBlocksInjector.injectStatsDataBlock(scope), ScreensBlocksInjector.injectStatsProjBlock(scope),
            injectStatsProjSearch(scope), XchgApiInjector.injectPersistedPropertyApi(), new DirectoryChooser());
   }

}
