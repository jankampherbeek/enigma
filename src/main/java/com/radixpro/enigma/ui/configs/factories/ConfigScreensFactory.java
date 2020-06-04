/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.configs.factories;

import com.radixpro.enigma.shared.Rosetta;
import com.radixpro.enigma.ui.configs.screens.ConfigDetails;
import com.radixpro.enigma.ui.configs.screens.ConfigEdit;
import com.radixpro.enigma.ui.configs.screens.ConfigNew;
import com.radixpro.enigma.ui.configs.screens.ConfigOverview;
import com.radixpro.enigma.ui.configs.screens.helpers.AspectsInConfig;
import com.radixpro.enigma.ui.configs.screens.helpers.CelObjectsInConfig;
import com.radixpro.enigma.ui.configs.screens.helpers.PropertiesForConfig;
import com.radixpro.enigma.xchg.api.PersistedConfigurationApi;
import com.radixpro.enigma.xchg.api.PersistedPropertyApi;
import com.radixpro.enigma.xchg.domain.config.Configuration;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Factory for Config screens.
 */
public class ConfigScreensFactory {

   /**
    * Build instance of ConfigDetails.
    *
    * @param config the configuration.
    * @return the instantiated ConfigDetails.
    */
   public ConfigDetails createConfigDetails(final Configuration config) {
      checkNotNull(config);
      Rosetta rosetta = Rosetta.getRosetta();
      PropertiesForConfig prop4Config = new PropertiesForConfig(config,
            new CelObjectsInConfig(rosetta),
            new AspectsInConfig(rosetta), rosetta);
      return new ConfigDetails(config.getName(), prop4Config, rosetta);

   }

   /**
    * Build instance of ConfigEdit.
    *
    * @param config The actual configuration.
    * @return instance of ConfigEdit.
    */
   public ConfigEdit createConfigEdit(final Configuration config) {
      checkNotNull(config);
      return new ConfigEdit(config, Rosetta.getRosetta());
   }

   /**
    * Build instance of ConfigNew.
    *
    * @param config The actual configuration.
    * @return instance of ConfigNew.
    */
   public ConfigNew createConfigNew(final Configuration config) {
      checkNotNull(config);
      return new ConfigNew(config, Rosetta.getRosetta(), new PersistedConfigurationApi());
   }

   public ConfigOverview createConfigOverview() {
      return new ConfigOverview(new PersistedConfigurationApi(), new PersistedPropertyApi(), Rosetta.getRosetta());
   }
}
