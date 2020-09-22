/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens.helpers;

import com.radixpro.enigma.SessionState;
import com.radixpro.enigma.domain.astronpos.AllMundanePositions;
import com.radixpro.enigma.domain.astronpos.IPosition;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates info from chart: chart name, list for celestial objects and list with MC + Asc
 */
public class ChartDataHelper {

   private final SessionState state;

   public ChartDataHelper() {
      this.state = SessionState.INSTANCE;
   }

   public List<IPosition> getCelObjectList() {
      return state.getSelectedChart().getCalculatedChart().getCelPoints();
   }

   public String getChartName() {
      return state.getSelectedChart().getChartData().getChartMetaData().getName();
   }

   /**
    * Create list with house positions
    *
    * @return positions for MC and Asc (in that sequence).
    */
   public List<IPosition> getHousesList() {
      AllMundanePositions allMundanePositions = state.getSelectedChart().getCalculatedChart().getMundPoints();
      List<IPosition> housesList = new ArrayList<>();
      housesList.add(allMundanePositions.getMc());
      housesList.add(allMundanePositions.getAsc());
      return housesList;
   }
}
