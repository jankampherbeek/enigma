/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api;

import com.radixpro.enigma.be.handlers.CalculatedChartHandler;
import com.radixpro.enigma.domain.astronpos.CalculatedChart;
import com.radixpro.enigma.domain.reqresp.CalculatedChartRequest;
import com.radixpro.enigma.domain.reqresp.CalculatedChartResponse;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Api for the calculation of a chart.
 */
public class CalculatedChartApi {

   private final CalculatedChartHandler handler;


   public CalculatedChartApi(final CalculatedChartHandler handler) {
      this.handler = checkNotNull(handler);
   }

   /**
    * Service for the calculation of a chart.
    *
    * @param request Request for the calcualtion. PRE: not null.
    * @return response with the calculated chart.
    */
   public CalculatedChartResponse calcChart(final CalculatedChartRequest request) {
      checkNotNull(request);
      CalculatedChart calculatedChart = null;
      String resultMsg = "OK";
      try {
//         calculatedChart = handler.defineChart(request.getSettings(), request.getDateTime(), request.getLocation());
         calculatedChart = handler.defineChart(request.getSettings(), request.getDateTime(), request.getLocation());    // FIXME, use Datetime
      } catch (Exception e) {
         resultMsg = "Exception when calculating a chart : " + e.getMessage();
      }
      return new CalculatedChartResponse(calculatedChart, resultMsg);
   }

}
