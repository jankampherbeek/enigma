/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.calculatedobjects;

import com.radixpro.enigma.xchg.domain.IChartPoints;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Simple position with only ecliptical and equatorial coordinates and no speed.
 */
public class SimplePosVo {

   private final IChartPoints point;
   private final double longitude;
   private final double latitude;
   private final double rightAscension;
   private final double declination;

   /**
    * Constructor defines all properties.
    *
    * @param point          planet, point, cusp etc. PRE: not null
    * @param longitude      longitude in degrees. PRE: 0.0 <= longitude < 360.0
    * @param latitude       latitude in degrees. PRE: -90.0 <= latitude <= 90.0
    * @param rightAscension right ascension in degrees. PRE:  0.0 <= right ascension < 360.0
    * @param declination    declination in degrees. PRE: -90.0 <= declination <= 90.0
    */
   public SimplePosVo(final IChartPoints point, final double longitude, final double latitude, final double rightAscension, final double declination) {
      checkArgument(longitude >= 0.0 && longitude < 360.0);
      checkArgument(latitude >= -90.0 && latitude <= 90.0);
      checkArgument(rightAscension >= 0.0 && longitude < 360.0);
      checkArgument(declination >= -90.0 && latitude <= 90.0);
      this.point = checkNotNull(point);
      this.longitude = longitude;
      this.latitude = latitude;
      this.rightAscension = rightAscension;
      this.declination = declination;
   }

   public IChartPoints getPoint() {
      return point;
   }

   public double getLongitude() {
      return longitude;
   }

   public double getLatitude() {
      return latitude;
   }

   public double getRightAscension() {
      return rightAscension;
   }

   public double getDeclination() {
      return declination;
   }
}
