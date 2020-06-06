/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Value object for an element of a situation in a specific coordinate system, typically position or speed.
 */
public class CelCoordinateElementVo {

   private final double base;
   private final double deviation;
   private final double distance;

   /**
    * Constructor defines all properties.
    *
    * @param base      The main factor (typically longitude, right ascension or azimuth). PRE: -360 <= base < 360.0
    * @param deviation Deviation from base (typically latitude, declination or altitude). PRE: -90 <= deviation <= 90.0
    * @param distance  Distance. (Typically radius vector in AU).
    */
   public CelCoordinateElementVo(final double base, final double deviation, final double distance) {
      checkArgument(-360.0 <= base && base <= 360.0);      // TODO limit range to < 360.0 (spec. for azimuth)
      checkArgument(90.0 >= deviation && -90.0 <= deviation);
      this.base = base;
      this.deviation = deviation;
      this.distance = distance;
   }

   public double getBase() {
      return base;
   }

   public double getDeviation() {
      return deviation;
   }

   public double getDistance() {
      return distance;
   }
}
