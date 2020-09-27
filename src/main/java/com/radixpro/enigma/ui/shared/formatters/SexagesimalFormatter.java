/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.formatters;

import com.radixpro.enigma.be.util.Range;

import static com.radixpro.enigma.shared.common.EnigmaDictionary.*;

/**
 * Formats into sexagesimal results.
 */
public class SexagesimalFormatter {

   private final int lengthOfIntegerPart;

   /**
    * Constructor, defines the length of the hours/degrees in the formatted result.
    *
    * @param lengthOfIntegerPart Expects 2 or 3, any other value is handled as 3.
    */
   public SexagesimalFormatter(final int lengthOfIntegerPart) {
      this.lengthOfIntegerPart = lengthOfIntegerPart;
   }

   /**
    * Format a double into a string with degrees, minutes and seconds.
    *
    * @param value2Format the value to format.
    * @return the formatted string.
    */
   public String formatDms(final double value2Format) {
      return performFormatting(value2Format);
   }

   /**
    * Format a double into a string with degrees and minutes.
    *
    * @param value2Format the value to format.
    * @return the formatted string.
    */
   public String formatDm(final double value2Format) {
      return performFormatting(value2Format).substring(0, lengthOfIntegerPart + 4);
   }

   private String performFormatting(final double value2Format) {
      double tempValue = Range.INSTANCE.checkValue(value2Format, 0.0, 360.0);
      int degHour = (int) tempValue;
      double fraction = tempValue - degHour;
      double fractionalMinute = fraction * 60.0;
      int minute = (int) fractionalMinute;
      int second = (int) ((fractionalMinute - minute) * 60.0);
      String degHourFormat = lengthOfIntegerPart == 2 ? "%02d" : "%03d";
      String content = degHourFormat + DEGREESIGN + "%02d" + MINUTESIGN + "%02d" + SECONDSIGN;
      return String.format(content, degHour, minute, second);
   }

}
