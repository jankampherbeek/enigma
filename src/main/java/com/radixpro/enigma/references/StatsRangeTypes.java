/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.references;

import com.radixpro.enigma.shared.exceptions.UnknownIdException;
import org.jetbrains.annotations.NotNull;

/**
 * Range types (areas) to be used in statistical research.
 */
public enum StatsRangeTypes {

   SIGNS(1, "statsrangetype.signs"),
   HOUSES(2, "statsrangetype.houses"),
   DECANS(3, "statsrangetype.decans");

   private final int id;
   private final String rbName;

   StatsRangeTypes(final int id,
                   @NotNull final String rbName) {
      this.id = id;
      this.rbName = rbName;
   }

   public static StatsRangeTypes getRangeTypeForId(final int id) throws UnknownIdException {
      for (StatsRangeTypes type : StatsRangeTypes.values()) {
         if (type.getId() == id) {
            return type;
         }
      }
      throw new UnknownIdException("Tried to read StatsRangeType with invalid id : " + id);
   }

   public int getId() {
      return id;
   }

   public String getRbName() {
      return rbName;
   }

}
