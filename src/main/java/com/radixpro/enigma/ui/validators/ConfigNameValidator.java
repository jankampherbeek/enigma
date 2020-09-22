
/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.validators;

import com.radixpro.enigma.domain.config.Configuration;
import com.radixpro.enigma.xchg.api.PersistedConfigurationApi;

import java.util.List;

public class ConfigNameValidator {

   final PersistedConfigurationApi api;

   public ConfigNameValidator(final PersistedConfigurationApi api) {
      this.api = api;
   }

   public boolean validate(final String input) {
      List<Configuration> existingConfig;
      boolean validated = true;
      if (input.length() < 1) validated = false;
      else {
         existingConfig = api.search(input);
         validated = existingConfig.isEmpty();
      }
      return validated;
   }

}
