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

   public static ChartsAspects injectChartsAspects() {
      return new ChartsAspects(XchgApiInjector.injectAspectsApi(), ScreensHelpersInjector.injectChartDataHelper());
   }

   public static ChartsDrawing2d injectChartsDrawing2d() {
      return new ChartsDrawing2d(ScreensHelpersInjector.injectRadixWheel());
   }

   public static ChartsInput injectChartsInput() {
      return new ChartsInput(XchgApiInjector.injectPersistedChartDataApi(), UiValidatorsInjector.injectValidatedChartName(),
            UiValidatorsInjector.injectValidatedDate(), UiValidatorsInjector.injectValidatedTime(),
            UiValidatorsInjector.injectValidatedLongitude(), UiValidatorsInjector.injectValidatedLatitude(),
            UiHelpersInjector.injectDateTimeJulianCreator());
   }

   public static ChartsMidpoints injectChartsMidpoints() {
      return new ChartsMidpoints(XchgApiInjector.injectMidpointsApi(), ScreensHelpersInjector.injectChartDataHelper());
   }

   public static ChartsSearch injectChartsSearch() {
      return new ChartsSearch(XchgApiInjector.injectPersistedChartDataApi());
   }

   public static ChartsStart injectChartsStart(AppScope scope) {
      return new ChartsStart(scope.getRosetta(), XchgApiInjector.injectCalculatedChartApi(), injectChartsTetenburg(scope),
            injectChartsAspects(), injectChartsMidpoints(), injectChartsTransitsInput(), injectChartsSearch(), injectChartsInput(),
            XchgApiInjector.injectPersistedChartDataApi(), XchgApiInjector.injectPersistedConfigurationApi(),
            XchgApiInjector.injectPersistedPropertyApi(), UiScreensInjector.injectConfigOverview(scope),
            ScreensHelpersInjector.injectPropertiesForConfig(scope), ScreensHelpersInjector.injectCelObjectsInConfig(scope),
            ScreensHelpersInjector.injectAspectsInConfig(scope), ScreensHelpersInjector.injectPropertiesTableForConfig(scope), injectChartsDrawing2d());
   }

   public static ChartsTransitsInput injectChartsTransitsInput() {
      return new ChartsTransitsInput(ScreensBlocksInjector.injectProgMetaInputBLock(), ScreensBlocksInjector.injectLocationInputBlock(),
            ScreensBlocksInjector.injectDateTimeInputBlock(), XchgApiInjector.injectTransitsApi());
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

   public static StatsDataNew injectStatsDataNew() {
      return new StatsDataNew(ScreensBlocksInjector.injectNameDescriptionInputBlock(), XchgApiInjector.injectInputDataFileApi(),
            XchgApiInjector.injectPersistedPropertyApi());
   }

   public static StatsDataSearch injectStatsDataSearch() {
      return new StatsDataSearch(XchgApiInjector.injectPersistedDataFileApi());
   }

   public static StatsProjNew injectStatsProjNew() {
      return new StatsProjNew(ScreensBlocksInjector.injectNameDescriptionInputBlock(), ScreensBlocksInjector.injectBaseConfigInputBlock(),
            injectStatsDataSearch());
   }

   public static StatsProjSearch injectStatsProjSearch() {
      return new StatsProjSearch();
   }

   public static StatsStart injectStatsStart() {
      return new StatsStart(ScreensBlocksInjector.injectStatsDataBlock(), ScreensBlocksInjector.injectStatsProjBlock(),
            injectStatsProjSearch(), XchgApiInjector.injectPersistedPropertyApi(), new DirectoryChooser());
   }

}
