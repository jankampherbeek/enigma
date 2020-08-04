/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc;

/**
 * Utility class for goniometric calculations.
 * All calculations are performed in degrees, no conversion from and to radians is required.
 * All methods are static.
 */
public class EnigmaMath {

   /**
    * Calculates sine.
    *
    * @param value Value for which the sine will be calculated.
    * @return Sine of value in degrees.
    */
   public static final double sin(double value) {
      return Math.sin(Math.toRadians(value));
   }

   /**
    * Calculates cosine.
    *
    * @param value Value for which the cosine will be calculated.
    * @return Cosine of value in degrees.
    */
   public static final double cos(double value) {
      return Math.cos(Math.toRadians(value));
   }

   /**
    * Calculates tangent.
    *
    * @param value Value for which the tangent will be calculated.
    * @return Tangent of value in degrees.
    */
   public static final double tan(double value) {
      return Math.tan(Math.toRadians(value));
   }

   /**
    * Calculates arc sine.
    *
    * @param value Value for which the arc sine will be calculated.
    * @return Arc sine of value in degrees.
    */
   public static final double asin(double value) {
      return Math.toDegrees(Math.asin(value));
   }

   /**
    * Calculates arc cosine.
    *
    * @param value Value for which the arc cosine will be calculated.
    * @return Arc cosine of value in degrees.
    */
   public static final double acos(double value) {
      return Math.toDegrees(Math.acos(value));
   }

   /**
    * Calculates arc tangent.
    *
    * @param value Value for which the arc tangent will be calculated.
    * @return Arc tangent of value in degrees.
    */
   public static final double atan(double value) {
      return Math.toDegrees(Math.atan(value));
   }

   /**
    * Calculates arc tangent of first argument divided by second argument.
    * Result is always in correct quadrant.
    *
    * @param value1 First argument.
    * @param value2 Second argument.
    * @return Arc tangent of first argument divided by second argument
    */
   public static final double atan2(double value1, double value2) {
      double result = Math.toDegrees(Math.atan2(value1, value2));
      return result;
   }

}
