/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.helpers;

import com.radixpro.enigma.AppScope;

public class UiHelpersInjector {

   private UiHelpersInjector() {
      // prevent instantiation
   }

   public static DateTimeJulianCreator injectDateTimeJulianCreator(AppScope scope) {
      return new DateTimeJulianCreator();
   }

   public static LocationCreator injectLocationCreator(AppScope scope) {
      return new LocationCreator();
   }

}
