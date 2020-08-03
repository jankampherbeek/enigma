/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc;

import com.radixpro.enigma.AppScope;

public class BeCalcInjector {

   public static SeFrontend injectSeFrontend(AppScope scope) {
      return SeFrontend.getFrontend();
   }    // TODO DI move to AppScope


}
