/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc;

import com.radixpro.enigma.AppScope;
import com.radixpro.enigma.be.handlers.BeHandlersInjector;

public class BeCalcInjector {

   public static SeFrontend injectSeFrontend(AppScope scope) {
      return SeFrontend.getFrontend();
   }    // TODO DI move to AppScope

   public static CoordSetForDateTimeCalc injectCoordSetForDateTimeCalc(AppScope scope) {
      return new CoordSetForDateTimeCalc(injectSeFrontend(scope));
   }

   public static JdFromPosCalc injectJdFromPosCalc(AppScope scope) {
      return new JdFromPosCalc(injectCoordSetForDateTimeCalc(scope));
   }

   public static SpaeculumPropSaCalculator injectSpaeculumPropSaCalculator(AppScope scope) {
      return new SpaeculumPropSaCalculator(BeHandlersInjector.injectObliquityHandler(scope));
   }
}
