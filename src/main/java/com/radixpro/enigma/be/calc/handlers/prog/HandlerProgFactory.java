/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.handlers.prog;

import com.radixpro.enigma.be.calc.converters.CalcConvertersFactory;
import com.radixpro.enigma.be.calc.core.SeFrontend;
import com.radixpro.enigma.be.calc.factories.ProgCalcFactory;
import com.radixpro.enigma.be.calc.handlers.astrondata.AstronDataHandlersFactory;
import com.radixpro.enigma.xchg.api.ApiChartCalcFactory;

public class HandlerProgFactory {

   public EphProgCalcHandler getTransitsCalcHandler() {
      return new EphProgCalcHandler(SeFrontend.getFrontend(), new CalcConvertersFactory().getEclipticHorizontalConverter());
   }

   public SolarReturnHandler getSolarReturnHandler() {
      return new SolarReturnHandler(new ProgCalcFactory().getJdFromPosCalc(), new ApiChartCalcFactory().getCalculatedChartApi());
   }

   public TetenburgHandler getTetenburgHandler() {
      return new TetenburgHandler(SeFrontend.getFrontend(), new CalcConvertersFactory().getEclipticalEquatorialConversions());
   }

   public TimeKeyHandler getTimeKeyHandler() {
      return new TimeKeyHandler(new SecundaryDateHandler(), new AstronDataHandlersFactory().getFullPointPositionHandler());   // TODO use factory for SecundaryDateHandler
   }

   public PrimaryPositionsHandler getPrimaryPositionsHandler() {
      return new PrimaryPositionsHandler();
   }

   public PrimaryHandler getPrimaryHandler() {
      return new PrimaryHandler(getPrimaryPositionsHandler(), getTimeKeyHandler());
   }

}
