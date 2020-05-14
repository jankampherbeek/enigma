/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.configs.factories;

import com.radixpro.enigma.shared.Rosetta;
import com.radixpro.enigma.ui.configs.screens.ConfigEdit;
import com.radixpro.enigma.xchg.domain.config.Configuration;
import javafx.stage.Stage;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Factory for ConfigEdit.
 */
public class ConfigEditFactory {

   /**
    * Build instance of ConfigEdit.
    *
    * @param config The actual configuration.
    * @return instance of ConfigEdit.
    */
   public ConfigEdit createConfigEdit(final Configuration config) {
      checkNotNull(config);
      return new ConfigEdit(config, new Stage(), Rosetta.getRosetta());
   }

}
