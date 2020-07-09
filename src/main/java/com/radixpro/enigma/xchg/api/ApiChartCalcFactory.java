/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.calc.handlers.astrondata.AstronDataHandlersFactory;

/**
 * Factory for API's that handle calcualtions for a chart.
 */
public class ApiChartCalcFactory {

   public CalculatedChartApi getCalculatedChartApi() {
      return new CalculatedChartApi(new AstronDataHandlersFactory().getCalculatedChartHandler());
   }

   ;

}
