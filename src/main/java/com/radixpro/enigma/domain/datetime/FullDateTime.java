/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.datetime;

import com.radixpro.enigma.be.calc.handlers.CaHandlersFactory;
import com.radixpro.enigma.be.calc.handlers.JulianDayHandler;
import com.radixpro.enigma.shared.common.Rosetta;
import com.radixpro.enigma.xchg.domain.TimeZones;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * All data for date and time, both for calculation and presentation.
 */
public class FullDateTime {

   private final SimpleDateTime simpleDateTime;
   private final TimeZones timeZone;
   private final boolean dst;
   private final double offsetForLmt;
   private final double jdUt;
   private final String formattedDateTime;
   private final Rosetta rosetta;

   /**
    * Constructor defines all members.
    *
    * @param simpleDateTime Populated instance of SimpleDateTime.
    * @param timeZone       Instance from enum TimeZones which includes offset for UT in decimal hours.
    * @param dst            True if dst applies, otherwise false, assumed is dst is always one hour.
    * @param offsetForLmt   If timezone is LMT, this field should present the offset to UT in decimal hours.
    */
   public FullDateTime(final SimpleDateTime simpleDateTime, final TimeZones timeZone, final boolean dst,
                       final double offsetForLmt) {
      rosetta = Rosetta.getRosetta();
      this.simpleDateTime = checkNotNull(simpleDateTime);
      this.timeZone = checkNotNull(timeZone);
      this.dst = dst;
      this.offsetForLmt = offsetForLmt;
      double utDelta = calculateUtDelta();
      JulianDayHandler julianDayHandler = CaHandlersFactory.getJulianDayHandler();
      jdUt = julianDayHandler.calculateJdNr(simpleDateTime, "ut") - utDelta / 24.0;
      formattedDateTime = formatDateTime();
   }

   private double calculateUtDelta() {
      double utDelta = (TimeZones.LMT == timeZone) ? offsetForLmt : timeZone.getOffset();
      if (dst) utDelta++;
      return utDelta;
   }

   private String formatDateTime() {
      StringBuilder dateTime = new StringBuilder();
      SimpleDate date = simpleDateTime.getDate();
      String cal = date.isGregorian() ? "G" : "J";
      dateTime.append(String.format("%d-%d-%d %s", date.getYear(), date.getMonth(), date.getDay(), cal));
      dateTime.append(" / ");
      SimpleTime time = simpleDateTime.getTime();
      dateTime.append(String.format("%d:%d:%d", time.getHour(), time.getMinute(), time.getSecond()));
      if (dst) {
         dateTime.append(" ");
         dateTime.append(rosetta.getText("ui.shared.dst"));
      }
      dateTime.append(" / ");
      dateTime.append(rosetta.getText(timeZone.getNameForRB()));
      return dateTime.toString();
   }

   public SimpleDateTime getSimpleDateTime() {
      return simpleDateTime;
   }

   public TimeZones getTimeZone() {
      return timeZone;
   }

   public boolean isDst() {
      return dst;
   }

   public double getOffsetForLmt() {
      return offsetForLmt;
   }

   public double getJdUt() {
      return jdUt;
   }

   public String getFormattedDateTime() {
      return formattedDateTime;
   }

}
