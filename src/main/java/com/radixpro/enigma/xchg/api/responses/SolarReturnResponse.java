/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.responses;

import com.radixpro.enigma.xchg.domain.FullChartDepr;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Response after calcualting a Solar Return Chart.
 */
public class SolarReturnResponse {

   private final FullChartDepr solarReturnChart;
   private final String resultMsg;

   /**
    * Constructor defines all properties.
    *
    * @param solarReturnChart the calculated chart for the SOlar Return.
    * @param resultMsg        Result message: "OK" if no error occurred, otherwise with a descriptive text of the error.
    */
   public SolarReturnResponse(final FullChartDepr solarReturnChart, final String resultMsg) {
      this.solarReturnChart = solarReturnChart;
      this.resultMsg = checkNotNull(resultMsg);
   }

   public FullChartDepr getSolarReturnChart() {
      return solarReturnChart;
   }

   public String getResultMsg() {
      return resultMsg;
   }
}
