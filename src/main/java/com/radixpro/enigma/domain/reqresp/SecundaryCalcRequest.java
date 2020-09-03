/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.reqresp;

import com.radixpro.enigma.domain.input.DateTimeJulian;
import com.radixpro.enigma.domain.input.Location;
import com.radixpro.enigma.xchg.api.settings.ICalcSettings;
import org.jetbrains.annotations.NotNull;

/**
 * Request for secundary progressions.
 */
public class SecundaryCalcRequest implements IProgCalcRequest {

   private final DateTimeJulian dateTime;
   private final DateTimeJulian birthDateTime;
   private final Location location;
   private final ICalcSettings settings;

   public SecundaryCalcRequest(@NotNull final DateTimeJulian eventDateTime, @NotNull final DateTimeJulian birthDateTime, @NotNull final Location location,
                               @NotNull final ICalcSettings settings) {
      this.dateTime = eventDateTime;
      this.location = location;
      this.settings = settings;
      this.birthDateTime = birthDateTime;
   }

   @Override
   public DateTimeJulian getDateTime() {
      return dateTime;
   }

   public DateTimeJulian getBirthDateTime() {
      return birthDateTime;
   }

   @Override
   public ICalcSettings getSettings() {
      return settings;
   }

   @Override
   public Location getLocation() {
      return location;
   }
}
