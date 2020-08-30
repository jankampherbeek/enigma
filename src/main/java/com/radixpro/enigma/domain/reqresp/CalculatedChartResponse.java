/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.reqresp;

import com.radixpro.enigma.domain.astronpos.CalculatedChart;
import org.jetbrains.annotations.NotNull;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Response from service for calculating a chart.
 */
public class CalculatedChartResponse {

   private final CalculatedChart calculatedChart;
   private final String resultMsg;

   /**
    * Constructor defines all properties.
    *
    * @param calculatedChart The calcualted chart. PRE: if ResultMsg is "OK" then not null.
    * @param resultMsg       Result message. In case of error a descrioption, otherwise "OK". PRE: not null, not empty.
    */
   public CalculatedChartResponse(final CalculatedChart calculatedChart, @NotNull final String resultMsg) {
      checkArgument(!resultMsg.isBlank());
      checkArgument((resultMsg.equals("OK") && calculatedChart != null) || !resultMsg.equals("OK"));
      this.calculatedChart = calculatedChart;
      this.resultMsg = resultMsg;
   }

   public CalculatedChart getCalculatedChart() {
      return calculatedChart;
   }

   public String getResultMsg() {
      return resultMsg;
   }
}
