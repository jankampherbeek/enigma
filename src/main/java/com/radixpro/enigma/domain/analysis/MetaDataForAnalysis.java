/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.analysis;

import com.radixpro.enigma.xchg.domain.general.IMetaData;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class MetaDataForAnalysis implements IMetaData {

   private final String name;
   private final String configName;
   private final double baseOrb;

   /**
    * Constructor defines all properties.
    *
    * @param name       Name of chartowner, for event, etc. PRE: not null.
    * @param configName Name of configuration. PRE: not null.
    * @param baseOrb    Base orb that has been used. PRE: baseOrb > 0.
    */
   public MetaDataForAnalysis(final String name, final String configName, final double baseOrb) {
      checkArgument(0.0 < baseOrb);
      this.name = checkNotNull(name);
      this.configName = checkNotNull(configName);
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
