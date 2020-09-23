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
import org.jetbrains.annotations.NotNull;

public class CalculatedChartApi {

   private final CalculatedChartHandler handler;


   public CalculatedChartApi(@NotNull final CalculatedChartHandler handler) {
      this.handler = handler;
   }

   public CalculatedChartResponse calcChart(@NotNull final CalculatedChartRequest request) {
      CalculatedChart calculatedChart = null;                   // FIXME assignment to null
      String resultMsg = "OK";
      try {
         calculatedChart = handler.defineChart(request.getSettings(), request.getDateTimeJulian(), request.getLocation());
      } catch (Exception e) {
         resultMsg = "Exception when calculating a chart : " + e.getMessage();
      }
      return new CalculatedChartResponse(calculatedChart, resultMsg);
   }

}
