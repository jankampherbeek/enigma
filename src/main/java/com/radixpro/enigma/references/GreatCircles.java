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
 * Great circles used for mesurement of celestial positions.
 */
public enum GreatCircles {
   ECLIPTIC(1, "greatcircles.ecliptic"),
   EQUATOR(2, "greatcircles.equator"),
   HORIZON(3, "greatcircles.horizon"),
   ALTITUDE(4, "greatcircles.altitude");

   final int id;
   final String rbName;

   GreatCircles(final int id,
                @NotNull final String rbName) {
      this.id = id;
      this.rbName = rbName;
   }

   public static GreatCircles getGreatCircleForId(final int id) throws UnknownIdException {
      for (GreatCircles circle : GreatCircles.values()) {
         if (circle.getId() == id) {
            return circle;
         }
      }
      throw new UnknownIdException("Tried to read GreatCircles with invalid id : " + id);
   }

   public int getId() {
      return id;
   }

   public String getRbName() {
      return rbName;
   }

}
