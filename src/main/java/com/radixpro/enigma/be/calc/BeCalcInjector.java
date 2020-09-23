/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc;

import com.radixpro.enigma.be.handlers.BeHandlersInjector;

public class BeCalcInjector {

   public static CoordSetForDateTimeCalc injectCoordSetForDateTimeCalc() {
      return new CoordSetForDateTimeCalc();
   }

   public static JdFromPosCalc injectJdFromPosCalc() {
      return new JdFromPosCalc(injectCoordSetForDateTimeCalc());
   }

   public static SpaeculumPropSaCalculator injectSpaeculumPropSaCalculator() {
      return new SpaeculumPropSaCalculator(BeHandlersInjector.injectObliquityHandler());
   }
}
