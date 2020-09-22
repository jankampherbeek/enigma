/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens.blocks;

import com.radixpro.enigma.AppScope;
import com.radixpro.enigma.ui.helpers.UiHelpersInjector;
import com.radixpro.enigma.ui.screens.UiScreensInjector;
import com.radixpro.enigma.ui.validators.UiValidatorsInjector;

public class ScreensBlocksInjector {

   private ScreensBlocksInjector() {
      // prevent instantiation
   }

   public static BaseConfigInputBlock injectBaseConfigInputBlock() {
      return new BaseConfigInputBlock();
   }

   public static DateTimeInputBlock injectDateTimeInputBlock() {
      return new DateTimeInputBlock(UiValidatorsInjector.injectValidatedDate(), UiValidatorsInjector.injectValidatedTime(),
            UiValidatorsInjector.injectValidatedLongitude(), UiHelpersInjector.injectDateTimeJulianCreator());
   }

   public static LocationInputBlock injectLocationInputBlock(AppScope scope) {
      return new LocationInputBlock(UiValidatorsInjector.injectValidatedLongitude(),
            UiValidatorsInjector.injectValidatedLatitude());
   }

   public static NameDescriptionInputBlock injectNameDescriptionInputBlock(AppScope scope) {
      return new NameDescriptionInputBlock();
   }

   public static ProgMetaInputBlock injectProgMetaInputBLock(AppScope scope) {
      return new ProgMetaInputBlock();
   }

   public static StatsDataBlock injectStatsDataBlock(AppScope scope) {
      return new StatsDataBlock(UiScreensInjector.injectStatsDataNew(scope), UiScreensInjector.injectStatsDataSearch(scope));
   }

   public static StatsProjBlock injectStatsProjBlock(AppScope scope) {
      return new StatsProjBlock(UiScreensInjector.injectStatsProjNew(scope));
   }
}
