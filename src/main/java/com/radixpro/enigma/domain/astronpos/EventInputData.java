/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.astronpos;

import com.radixpro.enigma.domain.datetime.FullDateTime;
import com.radixpro.enigma.xchg.domain.LocationOld;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Inputdata for an event. Reflects the internal Json structure for inputdata for events.
 */
public class EventInputData {

   final int id;
   final int chartId;
   final String description;
   final FullDateTime dateTime;
   final LocationOld locationOld;

   public EventInputData(final int id, final int chartId, final String description, final FullDateTime dateTime, final LocationOld locationOld) {
      this.id = id;
      this.chartId = chartId;
      this.description = checkNotNull(description);
      this.dateTime = checkNotNull(dateTime);
      this.locationOld = checkNotNull(locationOld);
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

   public FullDateTime getDateTime() {
      return dateTime;
   }

   public LocationOld getLocation() {
      return locationOld;
   }
}
