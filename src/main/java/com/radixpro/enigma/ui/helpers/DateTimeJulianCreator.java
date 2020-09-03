/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.helpers;

import com.radixpro.enigma.domain.input.DateTimeJulian;
import com.radixpro.enigma.references.TimeZones;
import org.jetbrains.annotations.NotNull;
import swisseph.SweDate;

public class DateTimeJulianCreator {

   public DateTimeJulian createDateTime(@NotNull final String dateText, @NotNull final String calendar, @NotNull final String timeText,
                                        @NotNull final TimeZones zone, final boolean dst, final double offsetLmt) {
      int[] dateParts = createDate(dateText);
      double jdFor0h = createJdFor0h(dateParts, calendar);
      double time = createTime(timeText, zone, dst, offsetLmt);
      double jdEt = jdFor0h + time / 24.0;
      return new DateTimeJulian(jdEt, calendar);
   }

   private int[] createDate(final String dateText) {
      String[] parts = dateText.split("/");
      int year = Integer.parseInt(parts[0]);
      int month = Integer.parseInt(parts[1]);
      int day = Integer.parseInt(parts[2]);
      return new int[]{year, month, day};
   }

   private double createJdFor0h(final int[] dateParts, final String calendar) {
      SweDate sweDate = new SweDate(dateParts[0], dateParts[1], dateParts[2], 0.0, calendar.equalsIgnoreCase("G"));
      return sweDate.getJulDay();
   }

   private double createTime(final String timeText, final TimeZones zone, final boolean dst, final double offsetLmt) {
      String[] parts = timeText.split(":");
      int hour = Integer.parseInt(parts[0]);
      int minute = Integer.parseInt(parts[1]);
      int second = Integer.parseInt(parts[2]);
      double time = hour + minute / 60.0 + second / 3600.0;
      if (dst) time--;
      if (zone.name().equals("LMT")) time -= offsetLmt;
      else time -= zone.getOffset();
      return time;
   }

}
