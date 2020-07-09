/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.handlers.astrondata;

import com.radixpro.enigma.be.calc.converters.CalcConvertersFactory;
import com.radixpro.enigma.be.calc.core.SeFrontend;

/**
 * Factory for handlers that take care of astronomical data.
 */
public class AstronDataHandlersFactory {

   public FullPointPositionHandler getFullPointPositionHandler() {
      return new FullPointPositionHandler(SeFrontend.getFrontend());
   }

   public ObliquityHandler getObliquityHandler() {
      return new ObliquityHandler(SeFrontend.getFrontend());
   }

   public MundanePositionsHandler getMundanePositionsHandler() {
      return new MundanePositionsHandler(SeFrontend.getFrontend(), new CalcConvertersFactory().getEclipticalEquatorialConversions(), getObliquityHandler());
   }

   public CalculatedChartHandler getCalculatedChartHandler() {
      return new CalculatedChartHandler(getFullPointPositionHandler(), getMundanePositionsHandler());
   }

}
