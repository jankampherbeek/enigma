/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.reqresp;

import com.radixpro.enigma.domain.datetime.FullDateTime;
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
   private final FullDateTime birthDateTime;
   private final FullDateTime progDateTime;

   /**
    * Constructor defines all arguments.
    *
    * @param longMcRadix   ecliptical longitude MC radix. PRE: 0.0 <= longMcRadix < 360.0
    * @param solarSpeed    daily speed of Sun in radix. PRE: 0.9 < solarSpeed < 1.1
    * @param location      location at birth (could be relocated but that does probably not make sense).
    * @param birthDateTime Date and time at birth.
    * @param progDateTime  Date and time for progression.
    */
   public TetenburgRequest(final double longMcRadix, final double solarSpeed, @NotNull final Location location, @NotNull final FullDateTime birthDateTime,
                           @NotNull final FullDateTime progDateTime) {
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

   public FullDateTime getBirthDateTime() {
      return birthDateTime;
   }

   public FullDateTime getProgDateTime() {
      return progDateTime;
   }
}
