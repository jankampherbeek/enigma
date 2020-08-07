/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.domain.config.Configuration;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Wrapper for Configuration; enables the use in a tableview.
 */
public class PresentableConfiguration {

   private final int configId;
   private final String configName;
   private final String configDescription;
   private final String standardIndication;
   private final Configuration originalConfig;

   /**
    * All members are populated using the Configuration.
    *
    * @param configuration The configuration to present.
    */
   public PresentableConfiguration(final Configuration configuration) {
      checkNotNull(configuration);
      configId = configuration.getId();
      configName = configuration.getName();
      configDescription = configuration.getDescription();
      standardIndication = configuration.getParentId() == 0L ?
            Rosetta.getRosetta().getText("ui.shared.yes") : Rosetta.getRosetta().getText("ui.shared.no");
      originalConfig = configuration;
   }

   public int getConfigId() {
      return configId;
   }

   public String getConfigName() {
      return configName;
   }

   public String getConfigDescription() {
      return configDescription;
   }

   public String getStandardIndication() {
      return standardIndication;
   }

   public Configuration getOriginalConfig() {
      return originalConfig;
   }
}
