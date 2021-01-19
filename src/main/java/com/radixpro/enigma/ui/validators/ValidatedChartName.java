/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.validators;

import com.radixpro.enigma.xchg.api.PersistedChartDataApi;
import com.radixpro.enigma.xchg.domain.FullChartInputData;

import java.util.List;

/**
 * Validation for the name given to a chart. A name is valid if it is not empty and does not yet exist in the database.
 */
public class ValidatedChartName {

   private String nameText;
   private final PersistedChartDataApi api;
   private boolean validated;

   public ValidatedChartName(final PersistedChartDataApi api) {
      this.api = api;
   }

   public boolean validate(final String input) {
      List<FullChartInputData> existingChart;
      validated = true;
      nameText = input;
      if (nameText.length() < 1) validated = false;
      else {
         existingChart = api.searchExact(nameText);
         validated = existingChart.isEmpty();
      }
      return validated;
   }

}
