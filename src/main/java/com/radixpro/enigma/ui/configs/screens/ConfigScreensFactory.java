/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.configs.screens;

import com.radixpro.enigma.shared.common.Rosetta;
import com.radixpro.enigma.ui.charts.ChartsSessionState;
import com.radixpro.enigma.ui.configs.screens.helpers.AspectsInConfig;
import com.radixpro.enigma.ui.configs.screens.helpers.CelObjectsInConfig;
import com.radixpro.enigma.ui.configs.screens.helpers.PropertiesForConfig;
import com.radixpro.enigma.xchg.api.ApiFactory;
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
      return new ConfigDetails(prop4Config, rosetta, ChartsSessionState.getInstance());
   }

   /**
    * Build instance of ConfigEdit.
    *
    * @param config The actual configuration.
    * @return instance of ConfigEdit.
    */
   public ConfigEdit createConfigEdit(final Configuration config) {
      checkNotNull(config);
      return new ConfigEdit(Rosetta.getRosetta(), ChartsSessionState.getInstance());
   }

   public ConfigNew createConfigNew() {
      return new ConfigNew(Rosetta.getRosetta(), ApiFactory.getPersistedConfigurationApi(), ChartsSessionState.getInstance());
   }

   public ConfigOverview createConfigOverview() {
      return new ConfigOverview(ApiFactory.getPersistedConfigurationApi(), new PersistedPropertyApi(), Rosetta.getRosetta(),
            ChartsSessionState.getInstance());
   }
}
