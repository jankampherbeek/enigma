/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.reqresp;

import com.radixpro.enigma.domain.datetime.FullDateTime;
import com.radixpro.enigma.domain.input.Location;
import com.radixpro.enigma.xchg.api.settings.ChartCalcSettings;
import org.jetbrains.annotations.NotNull;

/**
 * Request for the calcualtion of a chart
 */
public class CalculatedChartRequest {

   private final ChartCalcSettings settings;
   private final FullDateTime dateTime;
   private final Location location;

   /**
    * Constructor defines all properties.
    *
    * @param settings Settings for the calculation.
    * @param dateTime Date and time.
    * @param location Location.
    */
   public CalculatedChartRequest(@NotNull final ChartCalcSettings settings, @NotNull final FullDateTime dateTime, @NotNull final Location location) {
      this.settings = settings;
      this.dateTime = dateTime;
      this.location = location;
   }

   public ChartCalcSettings getSettings() {
      return settings;
   }

   public FullDateTime getDateTime() {
      return dateTime;
   }

   public Location getLocation() {
      return location;
   }
}
