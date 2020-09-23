/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.shared;

/**
 * Makes sure a number is in a specific range.
 */
public class Range {

   private final double min;
   private final double max;
   private final double actualRange;

   /**
    * Constructor defines min and max values. The input parameters should be in the correct sequence,
    * otherwise a Runtime Exception will be thrown.
    *
    * @param minValue Minimal value, inclusive.
    * @param maxValue Maximum value, exclusive.
    */
   public Range(final double minValue, final double maxValue) {
      if (minValue >= maxValue) throw new IllegalArgumentException("Input for Range has wrong sequence");
      this.min = minValue;
      this.max = maxValue;
      actualRange = max - min;
   }

   /**
    * Checks if a value in the defined range. If not, the value will be adapted so that it does fit.
    *
    * @param inputValue The value to check.
    * @return The checked, and possibly adapted, value.
    */
   public double checkValue(final double inputValue) {
      double workValue = inputValue;
      while (workValue >= max) workValue = workValue - actualRange;
      while (workValue < min) workValue = workValue + actualRange;
      return workValue;
   }

}
