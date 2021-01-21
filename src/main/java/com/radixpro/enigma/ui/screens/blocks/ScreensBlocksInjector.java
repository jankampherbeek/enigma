/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens.blocks;

import com.radixpro.enigma.ui.validators.UiValidatorsInjector;

public class ScreensBlocksInjector {

   private ScreensBlocksInjector() {
      // prevent instantiation
   }

   public static DateTimeInputBlock injectDateTimeInputBlock() {
      return new DateTimeInputBlock(UiValidatorsInjector.injectValidatedDate(), UiValidatorsInjector.injectValidatedTime(),
            UiValidatorsInjector.injectValidatedLongitude());
   }

   public static LocationInputBlock injectLocationInputBlock() {
      return new LocationInputBlock(UiValidatorsInjector.injectValidatedLongitude(), UiValidatorsInjector.injectValidatedLatitude());
   }

   public static ProgMetaInputBlock injectProgMetaInputBLock() {
      return new ProgMetaInputBlock();
   }

}
