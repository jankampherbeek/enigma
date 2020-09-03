/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.astronpos;

import com.radixpro.enigma.domain.input.DateTimeJulian;
import com.radixpro.enigma.domain.input.Location;
import org.jetbrains.annotations.NotNull;


/**
 * Inputdata for an event. Reflects the internal Json structure for inputdata for events.
 */
public class EventInputData {

   final int id;
   final int chartId;
   final String description;
   final DateTimeJulian dateTime;
   final Location location;

   public EventInputData(final int id, final int chartId, @NotNull final String description, @NotNull final DateTimeJulian dateTime,
                         @NotNull final Location location) {
      this.id = id;
      this.chartId = chartId;
      this.description = description;
      this.dateTime = dateTime;
      this.location = location;
   }

   public int getId() {
      return id;
   }

   public int getChartId() {
      return chartId;
   }

   public String getDescription() {
      return description;
   }

   public DateTimeJulian getDateTime() {
      return dateTime;
   }

   public Location getLocation() {
      return location;
   }

}
