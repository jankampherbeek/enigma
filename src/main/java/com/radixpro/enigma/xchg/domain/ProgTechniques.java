/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain;

/**
 * Spported techniques for progressive atrology.
 */
public enum ProgTechniques {
   TRANSIT("progtechnique.transit"),
   SECUNDARY("progtechnique.secundary"),
   PRIMARY("progtechnique.primary"),
   SOLAR("progtechnique.solar");

   private final String rbValue;

   ProgTechniques(final String rbValue) {
      this.rbValue = rbValue;
   }

   public String getRbValue() {
      return rbValue;
   }
}
