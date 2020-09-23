/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.handlers;

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

   public static DataFileHandler injectDataFileHandler() {
      return new DataFileHandler(BePersistencyInjector.injectDataFileDao(), XchgApiInjector.injectPersistedPropertyApi());
   }

   // TODO retrieve SeFrontend from scope
   public static EphProgCalcHandler injectEphProgCalcHandler() {
      return new EphProgCalcHandler(SeFrontend.INSTANCE);
   }

   public static FullPointPositionHandler injectFullPointPositionHandler() {
      return new FullPointPositionHandler();
   }

   public static InputDataFileHandler injectInputDataFileHandler() {
      return new InputDataFileHandler(BePersistencyInjector.injectDataReaderCsv(), BePersistencyInjector.injectJsonWriter());
   }

   public static MidpointsHandler injectMidpointsHandler() {
      return new MidpointsHandler(BeAnalysisInjector.injectMidpointsForRadix());
   }

   public static MundanePositionsHandler injectMundanePositionsHandler() {
      return new MundanePositionsHandler(injectObliquityHandler());
   }

   public static ObliquityHandler injectObliquityHandler() {
      return new ObliquityHandler();
   }

   public static PrimaryHandler injectPrimaryHandler() {
      return new PrimaryHandler(injectPrimaryPositionsHandler(), injectTimeKeyHandler(), injectObliquityHandler(),
            BeCalcInjector.injectSpaeculumPropSaCalculator());
   }

   public static PrimaryPositionsHandler injectPrimaryPositionsHandler() {
      return new PrimaryPositionsHandler();
   }

   public static ProgAspectHandler injectProgAspectHandler() {
      return new ProgAspectHandler(BeAnalysisInjector.injectProgRadixAspects());
   }

   public static SecundaryDateHandler injectSecundaryDateHandler() {
      return new SecundaryDateHandler();
   }

   public static SolarReturnHandler injectSolarReturnHandler() {
      return new SolarReturnHandler(BeCalcInjector.injectJdFromPosCalc(), XchgApiInjector.injectCalculatedChartApi());
   }

   public static TetenburgHandler injectTetenburgHandler() {
      return new TetenburgHandler(injectObliquityHandler());
   }

   public static TimeKeyHandler injectTimeKeyHandler() {
      return new TimeKeyHandler(injectSecundaryDateHandler(), injectFullPointPositionHandler());
   }

}
