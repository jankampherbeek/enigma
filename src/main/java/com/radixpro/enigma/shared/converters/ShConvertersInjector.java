/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.shared.converters;

public class ShConvertersInjector {

   private ShConvertersInjector() {
      // prevent instantiation
   }


   public static Csv2LocationConverter injectCsv2LocationConverter() {
      return new Csv2LocationConverter();
   }

}

