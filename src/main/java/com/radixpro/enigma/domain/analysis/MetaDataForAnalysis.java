/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.analysis;

import com.radixpro.enigma.xchg.domain.general.IMetaData;
import org.jetbrains.annotations.NotNull;

import static com.google.common.base.Preconditions.checkArgument;

public class MetaDataForAnalysis implements IMetaData {

   private final String name;
   private final String configName;
   private final double baseOrb;

   /**
    * Constructor defines all properties.
    *
    * @param name       Name of chartowner, for event, etc.
    * @param configName Name of configuration.
    * @param baseOrb    Base orb that has been used. PRE: baseOrb > 0.
    */
   public MetaDataForAnalysis(@NotNull final String name, final String configName, final double baseOrb) {
      checkArgument(0.0 < baseOrb);
      this.name = name;
      this.configName = configName;
      this.baseOrb = baseOrb;
   }

   @Override
   public String getName() {
      return name;
   }

   @Override
   public String getConfigName() {
      return configName;
   }

   public double getBaseOrb() {
      return baseOrb;
   }
}
