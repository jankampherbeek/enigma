/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.handlers;

import com.radixpro.enigma.xchg.domain.*;
import swisseph.SweDate;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.radixpro.enigma.shared.EnigmaDictionary.TROPICAL_YEAR;

/**
 * Calculates the secundary date.
 */
public class SecundaryDateHandler {


   public FullDateTime calcSecundaryDate(final FullDateTime birthDateTime, final FullDateTime eventDateTime) {
      checkNotNull(birthDateTime);
      checkNotNull(eventDateTime);
      final double differenceInRealDays = getDifferenceInRealDays(birthDateTime, eventDateTime);
      final double differenceInSecDays = getDifferenceInSecDays(differenceInRealDays);
      return getSecundaryDate(birthDateTime, differenceInSecDays);
   }

   private double getDifferenceInRealDays(final FullDateTime birthDateTime, final FullDateTime eventDateTime) {
      return eventDateTime.getJdUt() - birthDateTime.getJdUt();
   }

   private double getDifferenceInSecDays(final double differenceInRealDays) {
      return differenceInRealDays / TROPICAL_YEAR;
   }

   private FullDateTime getSecundaryDate(FullDateTime birthDateTime, double differenceInSecDays) {
      boolean gregorian = birthDateTime.getSimpleDateTime().getDate().isGregorian();
      final double secundaryJdUt = birthDateTime.getJdUt() + differenceInSecDays;
      final SweDate sweDate = new SweDate(secundaryJdUt, gregorian);
      final SimpleDate simpleDate = new SimpleDate(sweDate.getYear(), sweDate.getMonth(), sweDate.getDay(), gregorian);
      double ut = sweDate.getHour();
      int hour = (int) ut;
      double remaining = ut - hour;
      int minute = (int) (remaining * 60.0);
      remaining = (remaining * 60.0) - minute;
      int second = (int) (remaining * 60.0);
      SimpleTime simpleTime = new SimpleTime(hour, minute, second);
      SimpleDateTime simpleDateTime = new SimpleDateTime(simpleDate, simpleTime);
      return new FullDateTime(simpleDateTime, TimeZones.UT, false, 0.0);
   }

}
