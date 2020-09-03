/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.validators;

import static com.radixpro.enigma.ui.shared.UiDictionary.*;

/**
 * Validation for time. Time should be in the format h:m:s (hours 0..23, minutes 0..59, seconds 0..59).
 * Seconds are optional.
 */
public class ValidatedTime {

   private boolean validated;

   // TODO split into converter and validator


   public boolean validate(final String input) {
      int hour;
      int minute;
      int second;
      String[] values = input.split(SEXAG_SEPARATOR);
      if (values.length == 2 || values.length == 3) {
         try {
            hour = Integer.parseInt(values[0]);
            minute = Integer.parseInt(values[1]);
            second = values.length == 3 ? Integer.parseInt(values[2]) : 0;
            validated = (hour >= HOUR_MIN && hour <= HOUR_MAX &&
                  minute >= MINUTE_MIN && minute <= MINUTE_MAX &&
                  second >= SECOND_MIN && second <= SECOND_MAX);
         } catch (NumberFormatException nfe) {
            validated = false;
         }
      }
      return validated;
   }

}
