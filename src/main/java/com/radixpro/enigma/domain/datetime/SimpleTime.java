/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.datetime;

import java.io.Serializable;

/**
 * DTO for the time.
 */
public class SimpleTime implements Serializable {
   private final int hour;
   private final int minute;
   private final int second;

   /**
    * Constructor defines all members.
    *
    * @param hour   Hours of the time.
    * @param minute Minutes of the time.
    * @param second Seconds of the time.
    */
   public SimpleTime(final int hour, final int minute, final int second) {
      this.hour = hour;
      this.minute = minute;
      this.second = second;
   }

   public int getHour() {
      return hour;
   }

   public int getMinute() {
      return minute;
   }

   public int getSecond() {
      return second;
   }
}
