/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChartData {          // TODO rename to prevent confusion about ChartsData

   private final long id;
   private final FullDateTime fullDateTime;
   private final Location location;
   private final ChartMetaData chartMetaData;

   public ChartData(final long id, final FullDateTime fullDateTime, final Location location,
                    final ChartMetaData chartMetaData) {
      this.id = id;
      this.fullDateTime = checkNotNull(fullDateTime);
      this.location = checkNotNull(location);
      this.chartMetaData = checkNotNull(chartMetaData);
   }

   public long getId() {
      return id;
   }

   public FullDateTime getFullDateTime() {
      return fullDateTime;
   }

   public Location getLocation() {
      return location;
   }

   public ChartMetaData getChartMetaData() {
      return chartMetaData;
   }

   @Override
   public String toString() {
      return String.format("ChartData(id=%d, fullDateTime=%s, location=%s, chartMetaData=%s)", id, fullDateTime, location, chartMetaData);
   }

}
