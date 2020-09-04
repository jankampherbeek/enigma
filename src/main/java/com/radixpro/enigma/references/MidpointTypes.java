/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.references;

import org.jetbrains.annotations.NotNull;

public enum MidpointTypes {
   FULL(1, 360.0, "midpoints.full"),
   QUARTER(2, 90.0, "midpoints.quarter"),
   EIGHT(3, 45.0, "midpoints.eight"),
   SIXTEENTH(4, 22.5, "midpoints.sixteenth");

   private final int id;
   private final double angle;
   private final String fullRbId;

   MidpointTypes(final int id, final double angle, @NotNull final String fullRbId) {
      this.id = id;
      this.angle = angle;
      this.fullRbId = fullRbId;
   }

   public MidpointTypes getMidpointForId(int id) {
      for (MidpointTypes midpoint : MidpointTypes.values()) {
         if (midpoint.getId() == id) {
            return midpoint;
         }
      }
      return null;
      // TODO Release 2020.2: throw exception if aspect is not found
   }

   public int getId() {
      return id;
   }

   public double getAngle() {
      return angle;
   }

   public String getFullRbId() {
      return fullRbId;
   }
}
