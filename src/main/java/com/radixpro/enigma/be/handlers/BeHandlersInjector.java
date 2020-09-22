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

   public static AspectsHandler injectAspectsHandler() {
      return new AspectsHandler(BeAnalysisInjector.injectAspectsForRadix());
   }

   public static CalculatedChartHandler injectCalculatedChartHandler() {
      return new CalculatedChartHandler(injectFullPointPositionHandler(), injectMundanePositionsHandler());
   }

   public static DataFileHandler injectDataFileHandler(AppScope scope) {
      return new DataFileHandler(BePersistencyInjector.injectDataFileDao(scope), XchgApiInjector.injectPersistedPropertyApi());
   }

   // TODO retrieve SeFrontend from scope
   public static EphProgCalcHandler injectEphProgCalcHandler() {
      return new EphProgCalcHandler(SeFrontend.INSTANCE);
   }

   public static FullPointPositionHandler injectFullPointPositionHandler() {
      return new FullPointPositionHandler(BeCalcInjector.injectSeFrontend());
   }

   public static InputDataFileHandler injectInputDataFileHandler() {
      return new InputDataFileHandler(BePersistencyInjector.injectDataReaderCsv(), BePersistencyInjector.injectJsonWriter());
   }

   public static MidpointsHandler injectMidpointsHandler() {
      return new MidpointsHandler(BeAnalysisInjector.injectMidpointsForRadix());
   }

   public static MundanePositionsHandler injectMundanePositionsHandler() {
      return new MundanePositionsHandler(BeCalcInjector.injectSeFrontend(), injectObliquityHandler());
   }

   public static ObliquityHandler injectObliquityHandler() {
      return new ObliquityHandler(BeCalcInjector.injectSeFrontend());
   }

   public static PrimaryHandler injectPrimaryHandler(AppScope scope) {
      return new PrimaryHandler(injectPrimaryPositionsHandler(scope), injectTimeKeyHandler(scope), injectObliquityHandler(),
            BeCalcInjector.injectSpaeculumPropSaCalculator(scope));
   }

   public static PrimaryPositionsHandler injectPrimaryPositionsHandler(AppScope scope) {
      return new PrimaryPositionsHandler();
   }

   public static ProgAspectHandler injectProgAspectHandler(AppScope scope) {
      return new ProgAspectHandler(BeAnalysisInjector.injectProgRadixAspects());
   }

   public static SecundaryDateHandler injectSecundaryDateHandler(AppScope scope) {
      return new SecundaryDateHandler();
   }

   public static SolarReturnHandler injectSolarReturnHandler(AppScope scope) {
      return new SolarReturnHandler(BeCalcInjector.injectJdFromPosCalc(scope), XchgApiInjector.injectCalculatedChartApi());
   }

   public static TetenburgHandler injectTetenburgHandler() {
      return new TetenburgHandler(BeCalcInjector.injectSeFrontend(), injectObliquityHandler());
   }

   public static TimeKeyHandler injectTimeKeyHandler(AppScope scope) {
      return new TimeKeyHandler(injectSecundaryDateHandler(scope), injectFullPointPositionHandler());
   }

}
