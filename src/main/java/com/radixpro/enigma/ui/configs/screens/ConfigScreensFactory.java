/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.configs.screens;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.SessionState;
import com.radixpro.enigma.domain.config.Configuration;
import com.radixpro.enigma.ui.configs.screens.helpers.AspectsInConfig;
import com.radixpro.enigma.ui.configs.screens.helpers.CelObjectsInConfig;
import com.radixpro.enigma.ui.configs.screens.helpers.PropertiesForConfig;

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
      return new ConfigDetails(prop4Config, rosetta, SessionState.getInstance());
   }
//
//   /**
//    * Build instance of ConfigEdit.
//    *
//    * @param config The actual configuration.
//    * @return instance of ConfigEdit.
//    */
//   public ConfigEdit createConfigEdit(final Configuration config) {
//      checkNotNull(config);
//      return new ConfigEdit(Rosetta.getRosetta(), SessionState.getInstance());
//   }

//   public ConfigNew createConfigNew() {
//      return new ConfigNew(Rosetta.getRosetta(), ApiFactory.getPersistedConfigurationApi(), SessionState.getInstance());
//   }


}
