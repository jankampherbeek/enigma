/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.reqresp;

import com.radixpro.enigma.domain.datetime.FullDateTime;
import com.radixpro.enigma.domain.input.Location;
import com.radixpro.enigma.xchg.api.settings.ICalcSettings;
import org.jetbrains.annotations.NotNull;

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
    * @param dateTime date and time.
    * @param location location (use depends on use of topocentric positions).
    * @param settings the settings to use.
    */
   public EphProgCalcRequest(@NotNull final FullDateTime dateTime, @NotNull final Location location, @NotNull final ICalcSettings settings) {
      this.dateTime = dateTime;
      this.location = location;
      this.settings = settings;
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
