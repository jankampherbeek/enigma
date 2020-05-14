/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain;

import java.io.Serializable;

/**
 * DTO for a date and the calendar.
 */
public class SimpleDate implements Serializable {
   private final int year;
   private final int month;
   private final int day;
   private final boolean gregorian;

   /**
    * The constructor defines all members.
    *
    * @param year      the astronomical year
    * @param month     month, 1..12
    * @param day       day of month
    * @param gregorian true if gregorian calendar, false if julian calendar
    */
   public SimpleDate(final int year, final int month, final int day, final boolean gregorian) {
      this.year = year;
      this.month = month;
      this.day = day;
      this.gregorian = gregorian;
   }

   public int getYear() {
      return year;
   }

   public int getMonth() {
      return month;
   }

   public int getDay() {
      return day;
   }

   public boolean isGregorian() {
      return gregorian;
   }
}
