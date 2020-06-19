/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.shared;

import static com.google.common.base.Preconditions.checkNotNull;

public class Property {

   private final String key;
   private String value;

   public Property(final String key, final String value) {
      this.key = checkNotNull(key);
      this.value = checkNotNull(value);
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
