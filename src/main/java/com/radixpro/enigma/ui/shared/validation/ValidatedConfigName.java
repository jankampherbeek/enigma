/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.validation;

import com.radixpro.enigma.xchg.api.PersistedConfigurationApi;
import com.radixpro.enigma.xchg.domain.config.Configuration;

import java.util.List;

public class ValidatedConfigName extends ValidatedInput {

   private String nameText;

   /**
    * Constructor performs validation.
    *
    * @param input The name to validate.
    */
   public ValidatedConfigName(final String input) {
      super(input);
      validate();
   }

   @Override
   protected void validate() {
      List<Configuration> existingConfig;
      validated = true;
      nameText = input;
      if (nameText.length() < 1) validated = false;
      else {
         existingConfig = new PersistedConfigurationApi().search(nameText);
         validated = existingConfig.isEmpty();
      }
   }

   public String getNameText() {
      return nameText;
   }
}
