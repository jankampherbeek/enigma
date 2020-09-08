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
 * Types of distances to be used in statistical research.
 */
public enum DistanceTypes {

   RADIUS(1, "distancetype.radius.name", "distancetype.radius.descr"),
   LATITUDE(2, "distancetype.latitude.name", "distancetype.latitude.descr"),
   DECLINATION(3, "distancetype.declination.name", "distancetype.declination.descr"),
   ALTITUDE(4, "distancetype.altitude.name", "distancetype.altitude.descr");

   private final int id;
   private final String rbName;
   private final String rbDescr;

   DistanceTypes(final int id,
                 @NotNull final String rbName,
                 @NotNull final String rbDescr) {
      this.id = id;
      this.rbName = rbName;
      this.rbDescr = rbDescr;
   }

   public static DistanceTypes getDistanceTypeForId(final int id) throws UnknownIdException {
      for (DistanceTypes type : DistanceTypes.values()) {
         if (type.getId() == id) {
            return type;
         }
      }
      throw new UnknownIdException("Tried to read DistanceType with invalid id : " + id);
   }

   public int getId() {
      return id;
   }

   public String getRbName() {
      return rbName;
   }

   public String getRbDescr() {
      return rbDescr;
   }


}
