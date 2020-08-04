/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.requests;

import com.radixpro.enigma.domain.datetime.FullDateTime;
import com.radixpro.enigma.xchg.api.settings.ChartCalcSettings;
import com.radixpro.enigma.xchg.domain.Location;

import static com.google.common.base.Preconditions.checkNotNull;

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
    * @param settings Settings for the calculation. PRE: not null.
    * @param dateTime Date and time. PRE: not null.
    * @param location Location. PRE: not null.
    */
   public CalculatedChartRequest(final ChartCalcSettings settings, final FullDateTime dateTime, final Location location) {
      this.settings = checkNotNull(settings);
      this.dateTime = checkNotNull(dateTime);
      this.location = checkNotNull(location);
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
