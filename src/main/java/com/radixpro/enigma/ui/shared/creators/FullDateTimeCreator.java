/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.creators;

import com.radixpro.enigma.domain.datetime.FullDateTime;
import com.radixpro.enigma.domain.datetime.SimpleDateTime;
import com.radixpro.enigma.ui.shared.validation.ValidatedDate;
import com.radixpro.enigma.ui.shared.validation.ValidatedLongitude;
import com.radixpro.enigma.ui.shared.validation.ValidatedTime;
import com.radixpro.enigma.xchg.domain.TimeZones;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Creator for FullDateTime, based on data from input screen.
 */
public class FullDateTimeCreator {

   /**
    * Create instance of FullDateTime.
    *
    * @param valDate          validated version of the date. PRE: not null, isValidated.
    * @param valTime          validated version of the time. PRE: not null, isValidated.
    * @param valLongLocalTime validated version of localTimeLongitude. PRE: (not null, isValidated) OR timeZoneName does not indicate LMT.
    * @param localEastWest    indication east/west. PRE: not null, value: e, E, o, O, w, W.
    * @param timeZoneName     resource bundle name for time zone. PRE: not null.
    * @param dst              true if daylight saving time is used.
    * @return
    */
   public FullDateTime constructFullDateTime(final ValidatedDate valDate, final ValidatedTime valTime, final ValidatedLongitude valLongLocalTime,
                                             final String localEastWest, final String timeZoneName, final boolean dst) {
      checkArgument(null != valDate && valDate.isValidated());
      checkArgument(null != valTime && valTime.isValidated());
      checkArgument(null != timeZoneName && (!timeZoneName.equalsIgnoreCase("timezone.lmt") ||
            (null != valLongLocalTime && valLongLocalTime.isValidated())));
      checkArgument(null != localEastWest && (localEastWest.equalsIgnoreCase("E") || localEastWest.equalsIgnoreCase("O") ||
            localEastWest.equalsIgnoreCase("W")));
      String localEastWestTxt = checkNotNull(localEastWest);
      SimpleDateTime dateTime = new SimpleDateTime(valDate.getSimpleDate(), valTime.getSimpleTime());
      TimeZones selectedTimeZone = TimeZones.UT.timeZoneForName(timeZoneName);
      boolean selectedDst = dst;
      double offSetForLmt = 0.0;
      if (selectedTimeZone == TimeZones.LMT) {
         offSetForLmt = valLongLocalTime.getValue() / 15.0;
         if (localEastWestTxt.equalsIgnoreCase("W")) offSetForLmt = -offSetForLmt;
      }
      return new FullDateTime(dateTime, selectedTimeZone, selectedDst, offSetForLmt);
   }
}
