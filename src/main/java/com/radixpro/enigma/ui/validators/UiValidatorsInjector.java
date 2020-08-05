/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.validators;

import com.radixpro.enigma.AppScope;
import com.radixpro.enigma.xchg.api.XchgApiInjector;

public class UiValidatorsInjector {

   private UiValidatorsInjector() {
      // prevent instantiation
   }

   public static ValidatedChartName injectValidatedChartName(AppScope scope) {
      return new ValidatedChartName(XchgApiInjector.injectPersistedChartDataApi(scope));
   }

   public static ValidatedDate injectValidatedDate(AppScope scope) {
      return new ValidatedDate(XchgApiInjector.injectDateTimeApi(scope));
   }

   public static ValidatedLatitude injectValidatedLatitude(AppScope scope) {
      return new ValidatedLatitude();
   }

   public static ValidatedLongitude injectValidatedLongitude(AppScope scope) {
      return new ValidatedLongitude();
   }

   public static ValidatedTime injectValidatedTime(AppScope scope) {
      return new ValidatedTime();
   }

}
