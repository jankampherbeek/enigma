/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.shared.converters;

import com.radixpro.enigma.domain.datetime.FullDateTime;
import com.radixpro.enigma.domain.datetime.SimpleDate;
import com.radixpro.enigma.domain.datetime.SimpleDateTime;
import com.radixpro.enigma.domain.datetime.SimpleTime;
import com.radixpro.enigma.references.TimeZones;
import com.radixpro.enigma.shared.exceptions.InputDataException;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Convertor for Csv data to FullDateTime.
 */
public class Csv2FullDateTimeConverter {

   /**
    * Creates a FullDateTime from csv text. Does not support LMT!
    *
    * @param dateTxt  Text for data input: year, month and day, separated with a slash. E. g. 1953/1/29. PRE: not null.
    * @param timeTxt  Text for time input: hours, minutes and optionally seconds, separated with a colon. E. g. 8:37:30 or 11:12. PRE: not null.
    * @param calendar 'G' for Gregorian calendar, 'J' for Julian calendar. PRE: not null and contains 'J' or 'G';
    * @param zone     The timezone to use. Do not enter LMT as there is no option to enter the offset. If LMT is required, first calculate UT and use that.
    *                 PRE: not null.
    * @param dst      'Y' if dst is used, otherwise 'N'. PRE: not null and contains 'Y' or 'N'.
    * @return The calculated FullDateTime.
    * @throws InputDataException if any parsing error occurs.
    */
   public FullDateTime convert(final String dateTxt, final String timeTxt, final String calendar, final String zone, final String dst)
         throws InputDataException {
      checkNotNull(dateTxt);
      checkNotNull(timeTxt);
      checkNotNull(zone);
      checkArgument(null != calendar && (calendar.equalsIgnoreCase("G") || calendar.equalsIgnoreCase("J")));
      checkArgument(null != dst && (dst.equalsIgnoreCase("Y") || dst.equalsIgnoreCase("N")));
      return createFullDateTime(dateTxt, timeTxt, calendar, zone, dst);
   }

   private FullDateTime createFullDateTime(final String dateTxt, final String timeTxt, final String calendar, final String zone, final String dst)
         throws InputDataException {
      SimpleDateTime dateTime;
      TimeZones timeZone;
      String[] dateParts = dateTxt.split("/");
      if (3 != dateParts.length) throw new InputDataException("Error when parsing dateTxt : " + dateTxt);
      String[] timeParts = timeTxt.split(":");
      if (timeParts.length < 2 || timeParts.length > 3) throw new InputDataException("Error when parsing timeText : " + timeTxt);
      try {
         int year = Integer.parseInt(dateParts[0]);
         int month = Integer.parseInt(dateParts[1]);
         int day = Integer.parseInt(dateParts[2]);
         int hour = Integer.parseInt(timeParts[0]);
         int minute = Integer.parseInt(timeParts[1]);
         int second = (timeParts.length == 3) ? Integer.parseInt(timeParts[2]) : 0;
         SimpleDate date = new SimpleDate(year, month, day, calendar.equalsIgnoreCase("G"));
         SimpleTime time = new SimpleTime(hour, minute, second);
         dateTime = new SimpleDateTime(date, time);
         timeZone = TimeZones.timeZoneForId(Integer.parseInt(zone));
         if (timeZone == TimeZones.UT && (timeZone.getId() != Integer.parseInt(zone)))
            throw new InputDataException("Encountered unknown index for timezone when parsing FullDateTime : " + zone);
      } catch (NumberFormatException nfe) {
         throw new InputDataException("NumberFormatException when parsing FullDateTime : " + dateTxt + " " + timeTxt);
      }
      return new FullDateTime(dateTime, timeZone, dst.equalsIgnoreCase("Y"), 0.0); // offsetForLmt is set to zero
   }

}
