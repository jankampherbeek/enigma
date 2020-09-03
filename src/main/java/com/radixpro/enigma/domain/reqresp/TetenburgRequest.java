/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.reqresp;

import com.radixpro.enigma.domain.input.DateTimeJulian;
import com.radixpro.enigma.domain.input.Location;
import org.jetbrains.annotations.NotNull;

import static com.google.common.base.Preconditions.checkArgument;


/**
 * Request for the calculation of a critical point according to the theory of Ton TEtenburg.
 */
public class TetenburgRequest {

   private final double longMcRadix;
   private final double solarSpeed;
   private final Location location;
   private final DateTimeJulian birthDateTime;
   private final DateTimeJulian progDateTime;


   public TetenburgRequest(final double longMcRadix, final double solarSpeed, @NotNull final Location location, @NotNull final DateTimeJulian birthDateTime,
                           @NotNull final DateTimeJulian progDateTime) {
      checkArgument(0.0 <= longMcRadix && longMcRadix < 360.0);
      checkArgument(0.9 < solarSpeed && solarSpeed < 1.1);
      this.longMcRadix = longMcRadix;
      this.solarSpeed = solarSpeed;
      this.location = location;
      this.birthDateTime = birthDateTime;
      this.progDateTime = progDateTime;
   }

   public double getLongMcRadix() {
      return longMcRadix;
   }

   public double getSolarSpeed() {
      return solarSpeed;
   }

   public Location getLocation() {
      return location;
   }

   public DateTimeJulian getBirthDateTime() {
      return birthDateTime;
   }

   public DateTimeJulian getProgDateTime() {
      return progDateTime;
   }
}
