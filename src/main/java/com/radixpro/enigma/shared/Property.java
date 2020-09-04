/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.shared;

import org.jetbrains.annotations.NotNull;

public class Property {

   private final String key;
   private String value;

   public Property(@NotNull final String key, @NotNull final String value) {
      this.key = key;
      this.value = value;
   }

   public String getKey() {
      return key;
   }

   public String getValue() {
      return value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   @Override
   public String toString() {
      return String.format("Property(key=%s, value=%s)", key, value);
   }
}
