/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.config;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class DelinConfiguration implements Serializable {

   private final AspectConfiguration aspectConfiguration;

   public DelinConfiguration(@NotNull final AspectConfiguration aspectConfiguration) {
      this.aspectConfiguration = aspectConfiguration;
   }

   public AspectConfiguration getAspectConfiguration() {
      return aspectConfiguration;
   }


}
