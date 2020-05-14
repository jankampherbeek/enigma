/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain.config;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

public class DelinConfiguration implements Serializable {

   private final AspectConfiguration aspectConfiguration;

   public DelinConfiguration(final AspectConfiguration aspectConfiguration) {
      this.aspectConfiguration = checkNotNull(aspectConfiguration);
   }

   public AspectConfiguration getAspectConfiguration() {
      return aspectConfiguration;
   }


}
