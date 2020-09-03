/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.reqresp;

import com.radixpro.enigma.domain.input.DateTimeJulian;
import com.radixpro.enigma.domain.input.Location;
import com.radixpro.enigma.xchg.api.settings.ChartCalcSettings;
import org.jetbrains.annotations.NotNull;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Request for the calculation of a Solar Return chart.
 */
public class SolarReturnRequest {

   private final DateTimeJulian birthDateTime;
   private final ChartCalcSettings settings;
   private final Location location;
   private final double longSun;
   private final int ageForReturn;


   public SolarReturnRequest(@NotNull final DateTimeJulian birthDateTime, @NotNull final ChartCalcSettings settings, @NotNull final Location location,
                             final double longSun, final int ageForReturn) {
      checkArgument(longSun >= 0.0 && longSun < 360.0);
      this.birthDateTime = birthDateTime;
      this.settings = settings;
      this.location = location;
      this.longSun = longSun;
      this.ageForReturn = ageForReturn;
   }


   public DateTimeJulian getBirthDateTime() {
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

   public int getAgeForReturn() {
      return ageForReturn;
   }
}
