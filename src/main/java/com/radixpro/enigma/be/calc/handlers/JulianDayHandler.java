/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.handlers;

import com.radixpro.enigma.domain.datetime.SimpleDate;
import com.radixpro.enigma.domain.datetime.SimpleDateTime;
import com.radixpro.enigma.domain.datetime.SimpleTime;
import swisseph.SDate;
import swisseph.SweDate;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Julian Day numbers. Handles JD nrs for Ephemeris Time (jdnrEt) and Universal Time (jdnrUt).
 */
public class JulianDayHandler {

   private final SweDate sweDate;


   public JulianDayHandler(final SweDate sweDate) {
      this.sweDate = checkNotNull(sweDate);
   }

   /**
    * Calcualte julian day number from date and time.
    *
    * @param dateTime Date and time. PRE: not null.
    * @param jdType   "ut" or "UT" means Universal Time, "et" or "ET" results in Ephemeris time. PRE: not null, value in ["ut","UT","et","ET"]
    * @return Calculated Julian day number.
    */
   public double calculateJdNr(final SimpleDateTime dateTime, final String jdType) {
      checkNotNull(dateTime);
      checkNotNull(jdType);
      checkArgument(jdType.equals("et") || jdType.equals("ET") || jdType.equals("ut") || jdType.equals("UT"));
      SimpleDate date = dateTime.getDate();
      SimpleTime time = dateTime.getTime();
      double[] jdNrs = sweDate.getJDfromUTC(date.getYear(), date.getMonth(), date.getDay(), time.getHour(), time.getMinute(), time.getSecond(),
            date.isGregorian(), false);
      return jdType.equalsIgnoreCase("ut") ? jdNrs[1] : jdNrs[0];
   }

   /**
    * Calculate date and time frm julian day number.
    *
    * @param jd     The julian day number.
    * @param greg   true for Gregorian calendar, false for Julian calendar.
    * @param jdType "ut" or "UT" means Universal Time, "et" or "ET" results in Ephemeris time. PRE: not null, value in ["ut","UT","et","ET"]
    * @return calculated date and time.
    */
   public SimpleDateTime dateTimeFromJd(final double jd, final boolean greg, final String jdType) {
      checkNotNull(jdType);
      SDate sDate = jdType.equalsIgnoreCase("ut") ? sweDate.getUTCfromJDUT1(jd, greg) : sweDate.getUTCfromJDET(jd, greg);
      SimpleDate simpleDate = new SimpleDate(sDate.year, sDate.month, sDate.day, greg);
      SimpleTime simpleTime = new SimpleTime(sDate.hour, sDate.minute, (int) sDate.second);
      return new SimpleDateTime(simpleDate, simpleTime);
   }

}
