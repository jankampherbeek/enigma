/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Enum with categories for celestial objects.
 */
public enum CelObjectCategory {
   UNKNOWN(0, "celobjectcat.unknown"),
   CLASSICS(1, "celobjectcat.classic"),
   MODERN(2, "celobjectcat.modern"),
   EXTRA_PLUT(3, "celobjectcat.extraplut"),
   ASTEROIDS(4, "celobjectcat.asteroids"),
   CENTAURS(5, "celobjectcat.centaurs"),
   INTERSECTIONS(6, "celobjectcat.intersections"),
   HYPOTHETS(7, "celobjectcat.hypothets");

   private final int id;
   private final String nameForRB;

   /**
    * Constructor.
    *
    * @param id        The id of the category.
    * @param nameForRB The name to access the resource bundle for a translated text.
    */
   CelObjectCategory(final int id, final String nameForRB) {
      this.id = id;
      this.nameForRB = checkNotNull(nameForRB);
   }

   public int getId() {
      return id;
   }

   public String getNameForRB() {
      return nameForRB;
   }
}
