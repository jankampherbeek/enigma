/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.requests;

import com.radixpro.enigma.xchg.api.settings.ICalcSettings;
import com.radixpro.enigma.xchg.domain.FullDateTime;
import com.radixpro.enigma.xchg.domain.Location;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Request for secundary progressions.
 */
public class SecundaryCalcRequest implements IProgCalcRequest {

   private final FullDateTime dateTime;
   private final FullDateTime birthDateTime;
   private final Location location;
   private final ICalcSettings settings;

   /**
    * Constructor defines all properties.
    *
    * @param dateTime      date and time of the event. PRE: not null.
    * @param birthDateTime date and time of the birth. PRE: not null.
    * @param location      locationof birth. PRE: not null.
    * @param settings      Settings for the calcualtion. PRE: not null.
    */
   public SecundaryCalcRequest(final FullDateTime dateTime, final FullDateTime birthDateTime, final Location location, final ICalcSettings settings) {
      this.dateTime = checkNotNull(dateTime);
      this.location = checkNotNull(location);
      this.settings = checkNotNull(settings);
      this.birthDateTime = checkNotNull(birthDateTime);
   }

   @Override
   public FullDateTime getDateTime() {
      return dateTime;
   }

   public FullDateTime getBirthDateTime() {
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
