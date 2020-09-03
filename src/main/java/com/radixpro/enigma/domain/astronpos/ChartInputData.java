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
 * Inputdata for a chart. Reflects the internal Json structure for inputdata for charts.
 */
public class ChartInputData {

   final int id;
   final String name;
   final DateTimeJulian dateTime;
   final Location location;

   public ChartInputData(final int id, @NotNull final String name, @NotNull final DateTimeJulian dateTime, @NotNull final Location location) {
      this.id = id;
      this.name = name;
      this.dateTime = dateTime;
      this.location = location;
   }

   public int getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public DateTimeJulian getDateTime() {
      return dateTime;
   }

   public Location getLocation() {
      return location;
   }
}
