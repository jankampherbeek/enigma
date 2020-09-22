/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.validators;

import com.radixpro.enigma.xchg.api.XchgApiInjector;

public class UiValidatorsInjector {

   private UiValidatorsInjector() {
      // prevent instantiation
   }

   public static ConfigNameValidator injectConfigNameValidator() {
      return new ConfigNameValidator(XchgApiInjector.injectPersistedConfigurationApi());
   }

   public static ValidatedChartName injectValidatedChartName() {
      return new ValidatedChartName(XchgApiInjector.injectPersistedChartDataApi());
   }

   public static ValidatedDate injectValidatedDate() {
      return new ValidatedDate(XchgApiInjector.injectDateTimeApi());
   }

   public static ValidatedLatitude injectValidatedLatitude() {
      return new ValidatedLatitude();
   }

   public static ValidatedLongitude injectValidatedLongitude() {
      return new ValidatedLongitude();
   }

   public static ValidatedTime injectValidatedTime() {
      return new ValidatedTime();
   }

}
