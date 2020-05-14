/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.validation;

/**
 * Validation for longitude. Longitude should be in the format h:m:s (degrees -180..+180, minutes 0..59, seconds 0..59).
 * Seconds are optional. Negative degrees indicate west longitude, positive degrees indicate east longitude.
 */
public class ValidatedLongitude extends ValidatedInput {

   private double value;
   private int degrees;
   private int minutes;
   private int seconds;

   /**
    * The constructor performs the validation.
    *
    * @param input The longitude to validate.
    */
   public ValidatedLongitude(final String input) {
      super(input);
      validate();
   }

   @Override
   protected void validate() {
      String[] values = input.split(SEXAG_SEPARATOR);
      if (values.length == 2 || values.length == 3) {
         try {
            degrees = Integer.parseInt(values[0]);
            minutes = Integer.parseInt(values[1]);
            seconds = values.length == 3 ? Integer.parseInt(values[2]) : 0;
            validated = (degrees >= LONG_DEGREE_MIN && degrees <= LONG_DEGREE_MAX &&
                  minutes >= MINUTE_MIN && minutes <= MINUTE_MAX &&
                  seconds >= SECOND_MIN && seconds <= SECOND_MAX);
            if (validated && ((Math.abs(degrees)) == LONG_DEGREE_MAX)) validated = (minutes == 0 && seconds == 0);
            if (validated) value = degrees + (double) minutes / MINUTES_PER_HOUR + (double) seconds / SECONDS_PER_HOUR;
         } catch (NumberFormatException nfe) {
            validated = false;
         }
      }
      if (!validated) value = 0.0;
   }

   public double getValue() {
      return this.value;
   }

   public int getDegrees() {
      return this.degrees;
   }

   public int getMinutes() {
      return this.minutes;
   }

   public int getSeconds() {
      return this.seconds;
   }

   public void applyWesternLongitude() {
      if (value > 0.0) value = -value;
   }
}
