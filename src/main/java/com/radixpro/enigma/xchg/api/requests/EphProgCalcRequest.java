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
 * Request for the calculation of progressive positions based on ephejmeris calculations.
 */
public class EphProgCalcRequest implements IProgCalcRequest {

   private final FullDateTime dateTime;
   private final Location location;
   private final ICalcSettings settings;

   /**
    * Constructor defines all properties.
    *
    * @param dateTime date and time. PRE: not null.
    * @param location location (use depends on use of topocentric positions). PRE: not null.
    * @param settings the settings to use. PRE: not null.
    */
   public EphProgCalcRequest(final FullDateTime dateTime, final Location location, final ICalcSettings settings) {
      this.dateTime = checkNotNull(dateTime);
      this.location = checkNotNull(location);
      this.settings = checkNotNull(settings);
   }


   @Override
   public FullDateTime getDateTime() {
      return dateTime;
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
