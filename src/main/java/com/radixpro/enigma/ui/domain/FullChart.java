/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.domain;

import com.radixpro.enigma.domain.astronpos.CalculatedChart;
import com.radixpro.enigma.xchg.domain.FullChartInputData;
import org.jetbrains.annotations.NotNull;

/**
 * Calculatred chart and the date for this chart.
 */
public class FullChart {

   private final FullChartInputData fullChartInputData;
   private final CalculatedChart calculatedChart;

   /**
    * Constructor defines all properties.
    *
    * @param fullChartInputData Inputted data for the chart.
    * @param calculatedChart    Calculted positions for the chart.
    */
   public FullChart(@NotNull final FullChartInputData fullChartInputData, @NotNull final CalculatedChart calculatedChart) {
      this.fullChartInputData = fullChartInputData;
      this.calculatedChart = calculatedChart;
   }

   public FullChartInputData getChartData() {
      return fullChartInputData;
   }

   public CalculatedChart getCalculatedChart() {
      return calculatedChart;
   }
}
