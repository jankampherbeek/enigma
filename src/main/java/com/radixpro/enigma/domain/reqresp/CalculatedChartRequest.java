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

/**
 * Request for the calcualtion of a chart
 */
public class CalculatedChartRequest {

   private final ChartCalcSettings settings;
   private final DateTimeJulian dateTimeJulian;
   private final Location location;


   public CalculatedChartRequest(@NotNull final ChartCalcSettings settings, @NotNull final DateTimeJulian dateTimeJulian, @NotNull final Location location) {
      this.settings = settings;
      this.dateTimeJulian = dateTimeJulian;
      this.location = location;
   }

   public DateTimeJulian getDateTime() {   // TODO remove
      return dateTimeJulian;
   }

   public ChartCalcSettings getSettings() {
      return settings;
   }

   public DateTimeJulian getDateTimeJulian() {
      return dateTimeJulian;
   }

   public Location getLocation() {
      return location;
   }
}
