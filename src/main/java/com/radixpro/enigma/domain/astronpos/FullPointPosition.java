/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.astronpos;

import com.radixpro.enigma.xchg.domain.IChartPoints;
import org.jetbrains.annotations.NotNull;

/**
 * Full set of positions with all coordinates for a specific celestial object.
 */
public class FullPointPosition implements IPosition {

   private final IChartPoints celObject;
   private final FullPointCoordinate eclPos;
   private final FullPointCoordinate eqPos;
   private final CoordinateSet horPos;

   /**
    * Constructor defines all properties.
    *
    * @param celObject definition of celestial object.
    * @param eclPos    ecliptical position.
    * @param eqPos     equatorial positon.
    * @param horPos    horizontal position.
    */
   public FullPointPosition(@NotNull final IChartPoints celObject, @NotNull final FullPointCoordinate eclPos, @NotNull final FullPointCoordinate eqPos,
                            @NotNull final CoordinateSet horPos) {
      this.celObject = celObject;
      this.eclPos = eclPos;
      this.eqPos = eqPos;
      this.horPos = horPos;
   }

   @Override
   public IChartPoints getChartPoint() {
      return celObject;
   }

   public FullPointCoordinate getEclPos() {
      return eclPos;
   }

   public FullPointCoordinate getEqPos() {
      return eqPos;
   }

   public CoordinateSet getHorPos() {
      return horPos;
   }

   @Override
   public double getLongitude() {
      return eclPos.getPosition().getMainCoord();
   }

   @Override
   public double getDeclination() {
      return eqPos.getPosition().getDeviation();
   }

}
