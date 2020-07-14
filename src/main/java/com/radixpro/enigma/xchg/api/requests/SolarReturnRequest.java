/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.requests;

import com.radixpro.enigma.xchg.api.settings.ChartCalcSettings;
import com.radixpro.enigma.xchg.domain.FullDateTime;
import com.radixpro.enigma.xchg.domain.Location;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Request for the calculation of a Solar Return chart.
 */
public class SolarReturnRequest {

   private final FullDateTime birthDateTime;
   private final ChartCalcSettings settings;
   private final Location location;
   private final double longSun;
   private final int yearForReturn;

   /**
    * Constructor defines all properties.
    *
    * @param birthDateTime birthdate and birthtime. PRE: not null.
    * @param settings      settings for the calcualtion. PRE: not null.
    * @param location      location. If relocation is desired use current location. PRE: not null.
    * @param longSun       ecliptical longitude of the Sun in the radix. PRE: 0.0 <= longSun < 360.0
    * @param yearForReturn The year for which the solar chart needs to be calculated.
    */
   public SolarReturnRequest(final FullDateTime birthDateTime, final ChartCalcSettings settings, final Location location, final double longSun,
                             final int yearForReturn) {
      checkArgument(longSun >= 0.0 && longSun < 360.0);
      this.birthDateTime = checkNotNull(birthDateTime);
      this.settings = checkNotNull(settings);
      this.location = checkNotNull(location);
      this.longSun = longSun;
      this.yearForReturn = yearForReturn;
   }


   public FullDateTime getBirthDateTime() {
      return birthDateTime;
   }

   public ChartCalcSettings getSettings() {
      return settings;
   }

   public Location getLocation() {
      return location;
   }

   public double getLongSun() {
      return longSun;
   }

   public int getYearForReturn() {
      return yearForReturn;
   }
}
