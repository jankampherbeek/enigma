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
import com.radixpro.enigma.be.calc.SeFrontend;
import com.radixpro.enigma.be.persistency.BePersistencyInjector;
import com.radixpro.enigma.xchg.api.XchgApiInjector;

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

   public static DataFileHandler injectDataFileHandler(AppScope scope) {
      return new DataFileHandler(BePersistencyInjector.injectDataFileDao(scope), XchgApiInjector.injectPersistedPropertyApi(scope));
   }

   // TODO retrieve SeFrontend from scope
   public static EphProgCalcHandler injectEphProgCalcHandler(AppScope scope) {
      return new EphProgCalcHandler(SeFrontend.INSTANCE);
   }

   public static FullPointPositionHandler injectFullPointPositionHandler(AppScope scope) {
      return new FullPointPositionHandler(BeCalcInjector.injectSeFrontend(scope));
   }

   public static InputDataFileHandler injectInputDataFileHandler(AppScope scope) {
      return new InputDataFileHandler(BePersistencyInjector.injectDataReaderCsv(scope), BePersistencyInjector.injectJsonWriter(scope), scope.getRosetta());
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

   public static PrimaryHandler injectPrimaryHandler(AppScope scope) {
      return new PrimaryHandler(injectPrimaryPositionsHandler(scope), injectTimeKeyHandler(scope), injectObliquityHandler(scope),
            BeCalcInjector.injectSpaeculumPropSaCalculator(scope));
   }

   public static PrimaryPositionsHandler injectPrimaryPositionsHandler(AppScope scope) {
      return new PrimaryPositionsHandler();
   }

   public static ProgAspectHandler injectProgAspectHandler(AppScope scope) {
      return new ProgAspectHandler(BeAnalysisInjector.injectProgRadixAspects(scope));
   }

   public static SecundaryDateHandler injectSecundaryDateHandler(AppScope scope) {
      return new SecundaryDateHandler();
   }

   public static SolarReturnHandler injectSolarReturnHandler(AppScope scope) {
      return new SolarReturnHandler(BeCalcInjector.injectJdFromPosCalc(scope), XchgApiInjector.injectCalculatedChartApi(scope));
   }

   public static TetenburgHandler injectTetenburgHandler(AppScope scope) {
      return new TetenburgHandler(BeCalcInjector.injectSeFrontend(scope), injectObliquityHandler(scope));
   }

   public static TimeKeyHandler injectTimeKeyHandler(AppScope scope) {
      return new TimeKeyHandler(injectSecundaryDateHandler(scope), injectFullPointPositionHandler(scope));
   }

}
