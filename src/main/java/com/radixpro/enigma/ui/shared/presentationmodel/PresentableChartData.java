/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel;

import com.radixpro.enigma.xchg.domain.FullChartInputData;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Wrapper around ChartData; enables the use in a tableview.
 */
public class PresentableChartData {

   private final long chartId;
   private final String chartName;
   private final String chartDescr;
   private final String chartDataDescr;
   private final FullChartInputData originalData;

   public PresentableChartData(final FullChartInputData fullChartInputData) {
      checkNotNull(fullChartInputData);
      chartId = fullChartInputData.getId();
      chartName = fullChartInputData.getChartMetaData().getName();
      chartDescr = fullChartInputData.getChartMetaData().getDescription();
      chartDataDescr = fullChartInputData.getChartMetaData().getDataInput();
      originalData = fullChartInputData;
   }

   public long getChartId() {
      return chartId;
   }

   public String getChartName() {
      return chartName;
   }

   public String getChartDescr() {
      return chartDescr;
   }

   public String getChartDataDescr() {
      return chartDataDescr;
   }

   public FullChartInputData getOriginalData() {
      return originalData;
   }
}
