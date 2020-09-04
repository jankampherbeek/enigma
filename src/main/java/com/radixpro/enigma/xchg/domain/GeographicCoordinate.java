/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * DTO for a specific Geographic coordinate.
 */
public class GeographicCoordinate implements Serializable {

   private final int degrees;
   private final int minutes;
   private final int seconds;
   private final String direction;
   private final double value;

   /**
    * Constructor defines all properties
    *
    * @param degrees   The degrees of the coordinate. Pre: degree >= 0.
    * @param minutes   The minutes of the coordinate. Pre: 0 <= minute < 60.
    * @param seconds   The seconds of the coordinate. Pre: 0 <= minute < 60.
    * @param direction The direction of the coordinate. Pre: length direction == 1
    * @param value     Teh calculated value of the coordinate.
    */
   public GeographicCoordinate(final int degrees, final int minutes, final int seconds, @NotNull final String direction,
                               final double value) {
      checkArgument(degrees >= 0);
      checkArgument(minutes >= 0 && minutes < 60);
      checkArgument(seconds >= 0 && seconds < 60);
      checkArgument(direction.length() == 1);
      this.degrees = degrees;
      this.minutes = minutes;
      this.seconds = seconds;
      this.direction = direction;
      this.value = value;
   }

   public int getDegrees() {
      return degrees;
   }

   public int getMinutes() {
      return minutes;
   }

   public int getSeconds() {
      return seconds;
   }

   public String getDirection() {
      return direction;
   }

   public double getValue() {
      return value;
   }
}
