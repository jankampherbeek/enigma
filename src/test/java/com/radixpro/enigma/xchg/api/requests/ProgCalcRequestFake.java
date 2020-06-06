/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.requests;

import com.radixpro.enigma.xchg.api.settings.CalcSettingsFake;
import com.radixpro.enigma.xchg.api.settings.ICalcSettings;
import com.radixpro.enigma.xchg.domain.*;

public class ProgCalcRequestFake implements IProgCalcRequest {


   @Override
   public FullDateTime getDateTime() {
      return createDateTime();
   }

   @Override
   public ICalcSettings getSettings() {
      return new CalcSettingsFake();
   }

   private FullDateTime createDateTime() {
      SimpleDate date = new SimpleDate(2020, 6, 5, true);
      SimpleTime time = new SimpleTime(21, 43, 00);
      SimpleDateTime dateTime = new SimpleDateTime(date, time);
      return new FullDateTime(dateTime, TimeZones.CET, true, 0.0);
   }

}
