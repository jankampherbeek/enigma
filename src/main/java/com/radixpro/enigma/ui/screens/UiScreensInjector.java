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
      return new ChartsAspects(XchgApiInjector.injectAspectsApi(), ScreensHelpersInjector.injectChartDataHelper(scope));
   }

   public static ChartsDrawing2d injectChartsDrawing2d(AppScope scope) {
      return new ChartsDrawing2d(ScreensHelpersInjector.injectRadixWheel(scope), scope.getRosetta());
   }

   public static ChartsInput injectChartsInput(AppScope scope) {
      return new ChartsInput(XchgApiInjector.injectPersistedChartDataApi(), UiValidatorsInjector.injectValidatedChartName(),
            UiValidatorsInjector.injectValidatedDate(), UiValidatorsInjector.injectValidatedTime(),
            UiValidatorsInjector.injectValidatedLongitude(), UiValidatorsInjector.injectValidatedLatitude(),
            UiHelpersInjector.injectDateTimeJulianCreator());
   }

   public static ChartsMidpoints injectChartsMidpoints(AppScope scope) {
      return new ChartsMidpoints(XchgApiInjector.injectMidpointsApi(), ScreensHelpersInjector.injectChartDataHelper(scope));
   }

   public static ChartsSearch injectChartsSearch(AppScope scope) {
      return new ChartsSearch(scope.getRosetta(), XchgApiInjector.injectPersistedChartDataApi());
   }

   public static ChartsStart injectChartsStart(AppScope scope) {
      return new ChartsStart(scope.getRosetta(), XchgApiInjector.injectCalculatedChartApi(), injectChartsTetenburg(scope),
            injectChartsAspects(scope), injectChartsMidpoints(scope), injectChartsTransitsInput(scope), injectChartsSearch(scope), injectChartsInput(scope),
            XchgApiInjector.injectPersistedChartDataApi(), XchgApiInjector.injectPersistedConfigurationApi(),
            XchgApiInjector.injectPersistedPropertyApi(), UiScreensInjector.injectConfigOverview(scope),
            ScreensHelpersInjector.injectPropertiesForConfig(scope), ScreensHelpersInjector.injectCelObjectsInConfig(scope),
            ScreensHelpersInjector.injectAspectsInConfig(scope), ScreensHelpersInjector.injectPropertiesTableForConfig(scope), injectChartsDrawing2d(scope));
   }

   public static ChartsTransitsInput injectChartsTransitsInput(AppScope scope) {
      return new ChartsTransitsInput(ScreensBlocksInjector.injectProgMetaInputBLock(scope), ScreensBlocksInjector.injectLocationInputBlock(scope),
            ScreensBlocksInjector.injectDateTimeInputBlock(), XchgApiInjector.injectTransitsApi(scope));
   }

   public static ChartsTetenburg injectChartsTetenburg(AppScope scope) {
      return new ChartsTetenburg(scope.getRosetta(), XchgApiInjector.injectTetenburgApi(),
            UiValidatorsInjector.injectValidatedDate(), UiHelpersInjector.injectDateTimeJulianCreator());
   }

   public static ConfigDetails injectConfigDetails(AppScope scope) {
      return new ConfigDetails(ScreensHelpersInjector.injectPropertiesForConfig(scope),
            ScreensHelpersInjector.injectPropertiesTableForConfig(scope), ScreensHelpersInjector.injectCelObjectsInConfig(scope),
            ScreensHelpersInjector.injectAspectsInConfig(scope));
   }

   public static ConfigEdit injectConfigEdit(AppScope scope) {
      return new ConfigEdit(XchgApiInjector.injectPersistedConfigurationApi(), scope.getRosetta());
   }

   public static ConfigNew injectConfigNew() {
      return new ConfigNew(XchgApiInjector.injectPersistedConfigurationApi(), UiValidatorsInjector.injectConfigNameValidator());
   }

   public static ConfigOverview injectConfigOverview(AppScope scope) {
      return new ConfigOverview(XchgApiInjector.injectPersistedConfigurationApi(), XchgApiInjector.injectPersistedPropertyApi(),
            injectConfigNew(), injectConfigEdit(scope), injectConfigDetails(scope), scope.getRosetta());
   }

   public static StatsDataNew injectStatsDataNew(AppScope scope) {
      return new StatsDataNew(ScreensBlocksInjector.injectNameDescriptionInputBlock(scope), XchgApiInjector.injectInputDataFileApi(),
            XchgApiInjector.injectPersistedPropertyApi());
   }

   public static StatsDataSearch injectStatsDataSearch(AppScope scope) {
      return new StatsDataSearch(XchgApiInjector.injectPersistedDataFileApi(scope), scope.getRosetta());
   }

   public static StatsProjNew injectStatsProjNew(AppScope scope) {
      return new StatsProjNew(ScreensBlocksInjector.injectNameDescriptionInputBlock(scope), ScreensBlocksInjector.injectBaseConfigInputBlock(),
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
