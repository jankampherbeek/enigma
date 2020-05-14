/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.validation;

import com.radixpro.enigma.xchg.api.PersistedChartDataApi;
import com.radixpro.enigma.xchg.domain.ChartData;

import java.util.List;

/**
 * Validation for the name given to a chart. A name is valid if it is not empty and does not yet exist in the database.
 */
public class ValidatedChartName extends ValidatedInput {

   private String nameText;

   /**
    * Constructor performs validation.
    *
    * @param input The name to validate.
    */
   public ValidatedChartName(final String input) {
      super(input);
      validate();
   }

   @Override
   protected void validate() {
      List<ChartData> existingChart;
      validated = true;
      nameText = input;
      if (nameText.length() < 1) validated = false;
      else {
         PersistedChartDataApi api = new PersistedChartDataApi();
         existingChart = api.search(nameText);
         validated = existingChart.isEmpty();
      }
   }

   public String getNameText() {
      return this.nameText;
   }
}
