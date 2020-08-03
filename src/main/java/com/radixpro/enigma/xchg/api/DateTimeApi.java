/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.calc.SeFrontend;

public class DateTimeApi {

   private final SeFrontend seFrontend;

   public DateTimeApi() {
      seFrontend = SeFrontend.getFrontend();
   }

   public boolean checkDate(final int year, final int month, final int day, final boolean gregorian) {
      return seFrontend.isValidDate(year, month, day, gregorian);
   }

}
