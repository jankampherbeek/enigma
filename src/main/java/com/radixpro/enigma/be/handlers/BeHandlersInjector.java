/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.handlers;

import com.radixpro.enigma.AppScope;
import com.radixpro.enigma.be.analysis.BeAnalysisInjector;
import com.radixpro.enigma.be.calc.BeCalcInjector;

public class BeHandlersInjector {

   private BeHandlersInjector() {
      // prevent instantiation
   }

   public static AspectsHandler injectAspectsHandler(AppScope scope) {
      return new AspectsHandler(BeAnalysisInjector.injectAspectsForRadix(scope));
   }

   public static CalculatedChartHandler injectCalculatedChartHandler(AppScope scope) {
      return new CalculatedChartHandler(injectFullPointPositionHandler(scope), injectMundanePositionsHandler(scope));
   }

   public static FullPointPositionHandler injectFullPointPositionHandler(AppScope scope) {
      return new FullPointPositionHandler(BeCalcInjector.injectSeFrontend(scope));
   }

   public static MidpointsHandler injectMidpointsHandler(AppScope scope) {
      return new MidpointsHandler(BeAnalysisInjector.injectMidpointsForRadix(scope));
   }

   public static MundanePositionsHandler injectMundanePositionsHandler(AppScope scope) {
      return new MundanePositionsHandler(BeCalcInjector.injectSeFrontend(scope), injectObliquityHandler(scope));
   }

   public static ObliquityHandler injectObliquityHandler(AppScope scope) {
      return new ObliquityHandler(BeCalcInjector.injectSeFrontend(scope));
   }

   public static ProgAspectHandler injectProgAspectHandler(AppScope scope) {
      return new ProgAspectHandler(BeAnalysisInjector.injectProgRadixAspects(scope));
   }

   public static SecundaryDateHandler injectSecundaryDateHandler() {
      return new SecundaryDateHandler();
   }

   public static TetenburgHandler injectTetenburgHandler(AppScope scope) {
      return new TetenburgHandler(BeCalcInjector.injectSeFrontend(scope), injectObliquityHandler(scope));
   }

   public static TimeKeyHandler injectTimeKeyHandler(AppScope scope) {
      return new TimeKeyHandler(injectSecundaryDateHandler(), injectFullPointPositionHandler(scope));
   }

}
