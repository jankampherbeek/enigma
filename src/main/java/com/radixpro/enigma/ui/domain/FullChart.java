/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.domain;

import com.radixpro.enigma.domain.astronpos.CalculatedChart;
import com.radixpro.enigma.xchg.domain.ChartData;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Calculatred chart and the date for this chart.
 */
public class FullChart {

   private final ChartData chartData;
   private final CalculatedChart calculatedChart;

   /**
    * Constructor defines all properties.
    *
    * @param chartData       Inputted data for the chart.
    * @param calculatedChart Calculted positions for the chart.
    */
   public FullChart(final ChartData chartData, final CalculatedChart calculatedChart) {
      this.chartData = checkNotNull(chartData);
      this.calculatedChart = checkNotNull(calculatedChart);
   }

   public ChartData getChartData() {
      return chartData;
   }

   public CalculatedChart getCalculatedChart() {
      return calculatedChart;
   }
}
