/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import com.radixpro.enigma.domain.input.ChartMetaData;
import com.radixpro.enigma.domain.input.DateTimeJulian;
import com.radixpro.enigma.domain.input.Location;
import org.jetbrains.annotations.NotNull;

public class FullChartInputData {

   private final int id;
   private final DateTimeJulian dateTimeJulian;
   private final Location location;
   private final ChartMetaData chartMetaData;

   public FullChartInputData(final int id, @NotNull final DateTimeJulian dateTimeJulian, @NotNull final Location location,
                             @NotNull final ChartMetaData chartMetaData) {
      this.id = id;
      this.dateTimeJulian = dateTimeJulian;
      this.location = location;
      this.chartMetaData = chartMetaData;
   }

   public int getId() {
      return id;
   }

   public DateTimeJulian getDateTimeJulian() {
      return dateTimeJulian;
   }

   public Location getLocation() {
      return location;
   }

   public ChartMetaData getChartMetaData() {
      return chartMetaData;
   }

   @Override
   public String toString() {
      return String.format("ChartData(id=%d, fullDateTime=%s, location=%s, chartMetaData=%s)", id, dateTimeJulian, location, chartMetaData);
   }

}
