/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.handlers;

import com.radixpro.enigma.domain.input.DateTimeJulian;
import org.jetbrains.annotations.NotNull;

import static com.radixpro.enigma.shared.common.EnigmaDictionary.TROPICAL_YEAR;

/**
 * Calculates the secundary date.
 */
public class SecundaryDateHandler {

   public DateTimeJulian calcSecundaryDate(@NotNull final DateTimeJulian birthDateTime, @NotNull final DateTimeJulian eventDateTime) {
      final double differenceInRealDays = getDifferenceInRealDays(birthDateTime, eventDateTime);
      final double differenceInSecDays = getDifferenceInSecDays(differenceInRealDays);
      return getSecundaryDate(birthDateTime, differenceInSecDays);
   }

   private double getDifferenceInRealDays(final DateTimeJulian birthDateTime, final DateTimeJulian eventDateTime) {
      return eventDateTime.getJd() - birthDateTime.getJd();
   }

   private double getDifferenceInSecDays(final double differenceInRealDays) {
      return differenceInRealDays / TROPICAL_YEAR;
   }

   private DateTimeJulian getSecundaryDate(final DateTimeJulian birthDateTime, double differenceInSecDays) {
      final String gregorian = birthDateTime.getCalendar();
      final double secundaryJdUt = birthDateTime.getJd() + differenceInSecDays;
      return new DateTimeJulian(secundaryJdUt, gregorian);
   }

}
