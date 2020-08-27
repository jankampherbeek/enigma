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
 * Inputdata for a chart. Reflects the internal Json structure for inputdata for charts.
 */
public class ChartInputData {

   final int id;
   final String name;
   final FullDateTime dateTime;
   final LocationOld locationOld;

   public ChartInputData(final int id, final String name, final FullDateTime dateTime, final LocationOld locationOld) {
      this.id = id;
      this.name = checkNotNull(name);
      this.dateTime = checkNotNull(dateTime);
      this.locationOld = checkNotNull(locationOld);
   }

   public int getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public FullDateTime getDateTime() {
      return dateTime;
   }

   public LocationOld getLocation() {
      return locationOld;
   }
}
