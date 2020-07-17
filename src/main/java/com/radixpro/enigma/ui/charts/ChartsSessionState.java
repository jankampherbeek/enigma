/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.charts;

import com.radixpro.enigma.ui.domain.FullChart;
import com.radixpro.enigma.xchg.domain.config.Configuration;
import org.apache.log4j.Logger;

/**
 * Remembers state of charts and configurations.
 * Implemented as singleton. Is mutable.
 */
public class ChartsSessionState {

   private static final Logger LOG = Logger.getLogger(ChartsSessionState.class);
   private static ChartsSessionState instance = null;
   private FullChart selectedChart;
   private Configuration selectedConfig;

   private ChartsSessionState() {
      // prevent instantiation
   }

   public static ChartsSessionState getInstance() {
      if (instance == null) {
         instance = new ChartsSessionState();
         LOG.info("Created singleton instance for ChartsSessionState.");
      }
      return instance;
   }

   public void deSelectChart() {
      selectedChart = null;
   }

   public FullChart getSelectedChart() {
      return selectedChart;
   }

   public void setSelectedChart(FullChart selectedChart) {
      this.selectedChart = selectedChart;
   }

   public boolean selectedChartIsSet() {
      return selectedChart != null;
   }

   public Configuration getSelectedConfig() {
      return selectedConfig;
   }

   public void setSelectedConfig(Configuration selectedConfig) {
      this.selectedConfig = selectedConfig;
   }

   public boolean selectedConfigIsSet() {
      return selectedConfig != null;
   }
}
