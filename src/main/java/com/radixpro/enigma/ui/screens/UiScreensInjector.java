/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens;

import com.radixpro.enigma.ui.screens.blocks.ScreensBlocksInjector;
import com.radixpro.enigma.ui.screens.helpers.ScreensHelpersInjector;
import com.radixpro.enigma.ui.validators.UiValidatorsInjector;
import com.radixpro.enigma.xchg.api.XchgApiInjector;


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
            UiValidatorsInjector.injectValidatedLongitude(), UiValidatorsInjector.injectValidatedLatitude());
   }

   public static ChartsMidpoints injectChartsMidpoints() {
      return new ChartsMidpoints(XchgApiInjector.injectMidpointsApi(), ScreensHelpersInjector.injectChartDataHelper());
   }

   public static ChartsSearch injectChartsSearch() {
      return new ChartsSearch(XchgApiInjector.injectPersistedChartDataApi());
   }

   public static ChartsStart injectChartsStart() {
      return new ChartsStart(XchgApiInjector.injectCalculatedChartApi(), injectChartsTetenburg(),
            injectChartsAspects(), injectChartsMidpoints(), injectChartsTransitsInput(), injectChartsSearch(), injectChartsInput(),
            XchgApiInjector.injectPersistedChartDataApi(), XchgApiInjector.injectPersistedConfigurationApi(),
            XchgApiInjector.injectPersistedPropertyApi(), UiScreensInjector.injectConfigOverview(),
            ScreensHelpersInjector.injectPropertiesForConfig(), ScreensHelpersInjector.injectCelObjectsInConfig(),
            ScreensHelpersInjector.injectAspectsInConfig(), ScreensHelpersInjector.injectPropertiesTableForConfig(), injectChartsDrawing2d());
   }

   public static ChartsTransitsInput injectChartsTransitsInput() {
      return new ChartsTransitsInput(ScreensBlocksInjector.injectProgMetaInputBLock(), ScreensBlocksInjector.injectLocationInputBlock(),
            ScreensBlocksInjector.injectDateTimeInputBlock(), XchgApiInjector.injectTransitsApi());
   }

   public static ChartsTetenburg injectChartsTetenburg() {
      return new ChartsTetenburg(XchgApiInjector.injectTetenburgApi(),
            UiValidatorsInjector.injectValidatedDate());
   }

   public static ConfigDetails injectConfigDetails() {
      return new ConfigDetails(ScreensHelpersInjector.injectPropertiesForConfig(),
            ScreensHelpersInjector.injectPropertiesTableForConfig(), ScreensHelpersInjector.injectCelObjectsInConfig(),
            ScreensHelpersInjector.injectAspectsInConfig());
   }

   public static ConfigEdit injectConfigEdit() {
      return new ConfigEdit(XchgApiInjector.injectPersistedConfigurationApi());
   }

   public static ConfigNew injectConfigNew() {
      return new ConfigNew(XchgApiInjector.injectPersistedConfigurationApi(), UiValidatorsInjector.injectConfigNameValidator());
   }

   public static ConfigOverview injectConfigOverview() {
      return new ConfigOverview(XchgApiInjector.injectPersistedConfigurationApi(), XchgApiInjector.injectPersistedPropertyApi(),
            injectConfigNew(), injectConfigEdit(), injectConfigDetails());
   }


}
