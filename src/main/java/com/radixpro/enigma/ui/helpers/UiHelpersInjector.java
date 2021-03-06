/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.helpers;

public class UiHelpersInjector {

   private UiHelpersInjector() {
      // prevent instantiation
   }

   public static DateTimeJulianCreator injectDateTimeJulianCreator() {
      return new DateTimeJulianCreator();
   }

   public static LocationCreator injectLocationCreator() {
      return new LocationCreator();
   }

}
