/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.shared.common;

import com.radixpro.enigma.ui.domain.FullChart;
import com.radixpro.enigma.xchg.domain.config.Configuration;
import org.apache.log4j.Logger;

/**
 * Remembers state of several components.
 * Implemented as singleton. Is mutable.
 */
public class SessionState {

   private static final Logger LOG = Logger.getLogger(SessionState.class);
   private static SessionState instance = null;
   private FullChart selectedChart;
   private Configuration selectedConfig;

   private SessionState() {
      // prevent instantiation
   }

   public static SessionState getInstance() {
      if (instance == null) {
         instance = new SessionState();
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
