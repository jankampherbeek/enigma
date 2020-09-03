/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.input;

import org.jetbrains.annotations.NotNull;

/**
 * Value object for date and time.
 */
public class DateTimeJulian {

   private double jd;
   private String calendar;

   public DateTimeJulian(final double jd, @NotNull String calendar) {
      this.calendar = calendar;
      this.jd = jd;
   }

   public double getJd() {
      return jd;
   }

   public String getCalendar() {
      return calendar;
   }

}
