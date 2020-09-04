/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.references;

import org.jetbrains.annotations.NotNull;

/**
 * Enum of categories for aspects.
 */
public enum AspectCategory {
   MAJOR(0, "aspectcat.major"),
   MINOR(1, "aspectcat.minor"),
   MICRO(2, "aspectcat.micro"),
   DECLINATION(3, "aspectcat.declination");

   private final int id;
   private final String rbName;

   /**
    * An aspectcategory consists of the followign items:
    *
    * @param id     unique id, only for persistency purposes.
    * @param rbName the lookup name for the resource bundle.
    */
   AspectCategory(final int id, @NotNull final String rbName) {
      this.id = id;
      this.rbName = rbName;
   }

   public int getId() {
      return id;
   }

   public String getRbName() {
      return rbName;
   }
}
