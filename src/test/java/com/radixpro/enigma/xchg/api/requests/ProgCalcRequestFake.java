/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.requests;

import com.radixpro.enigma.domain.datetime.FullDateTime;
import com.radixpro.enigma.domain.datetime.SimpleDate;
import com.radixpro.enigma.domain.datetime.SimpleDateTime;
import com.radixpro.enigma.domain.datetime.SimpleTime;
import com.radixpro.enigma.xchg.api.settings.CalcSettingsFake;
import com.radixpro.enigma.xchg.api.settings.ICalcSettings;
import com.radixpro.enigma.xchg.domain.GeographicCoordinate;
import com.radixpro.enigma.xchg.domain.Location;
import com.radixpro.enigma.xchg.domain.TimeZones;

public class ProgCalcRequestFake implements IProgCalcRequest {


   @Override
   public FullDateTime getDateTime() {
      return createDateTime();
   }

   @Override
   public ICalcSettings getSettings() {
      return new CalcSettingsFake();
   }

   @Override
   public Location getLocation() {
      return new Location(new GeographicCoordinate(1, 30, 0, "n", 1.5),
            new GeographicCoordinate(2, 20, 0, "w", -2.3333333), "Somewhere");
   }

   private FullDateTime createDateTime() {
      SimpleDate date = new SimpleDate(2020, 6, 5, true);
      SimpleTime time = new SimpleTime(21, 43, 00);
      SimpleDateTime dateTime = new SimpleDateTime(date, time);
      return new FullDateTime(dateTime, TimeZones.CET, true, 0.0);
   }

}
