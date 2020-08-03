/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.AppScope;
import com.radixpro.enigma.be.handlers.BeHandlersInjector;

public class XchgApiInjector {

   public static CalculatedChartApi injectCalculatedChartApi(AppScope scope) {
      return new CalculatedChartApi(BeHandlersInjector.injectCalculatedChartHandler(scope));
   }

   public static TetenburgApi injectTetenburgApi(AppScope scope) {
      return new TetenburgApi(BeHandlersInjector.injectTetenburgHandler(scope));
   }

}
