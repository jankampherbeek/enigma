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
      chartDataDescr = createDataDescription(fullChartInputData);
      originalData = fullChartInputData;
   }

   private String createDataDescription(final FullChartInputData fullChartInputData) {
      StringBuilder descrSb = new StringBuilder();
      descrSb.append("Temporary dummy text");   // FIXME replace this text
//      PresentableDateTime dateTime4Text = new PresentableDateTime(fullChartInputData.getDateTimeJulian());   // FIXME, read this info from ChartMetaData
//      descrSb.append(dateTime4Text.getDate());
      descrSb.append(" ");
//      descrSb.append(dateTime4Text.getTime());
      return descrSb.toString();
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
