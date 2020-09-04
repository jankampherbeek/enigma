/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.astronpos;

import com.radixpro.enigma.references.MundanePoints;
import org.jetbrains.annotations.NotNull;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Position for a point in the mundane frame (houses etc.).
 */
public class MundanePosition implements IPosition {

   private final MundanePoints mundanePoint;
   private final double longitude;
   private final CoordinateSet eqPos;
   private final CoordinateSet horPos;

   /**
    * Constructor defines all properties.
    *
    * @param mundanePoint Type of mundane point (MC, Vertex,cusp etc.).
    * @param longitude    Ecliptical longitude (latitude does not apply for mundane points). PRE: 0.0 <= longitude < 360.0
    * @param eqPos        Equatorial coordinates.
    * @param horPos       Horizontal coordinates.
    */
   public MundanePosition(@NotNull final MundanePoints mundanePoint, final double longitude, @NotNull final CoordinateSet eqPos,
                          @NotNull final CoordinateSet horPos) {
      this.mundanePoint = mundanePoint;
      checkArgument(0.0 <= longitude && longitude < 360.0);
      this.longitude = longitude;
      this.eqPos = eqPos;
      this.horPos = horPos;
   }

   @Override
   public MundanePoints getChartPoint() {
      return mundanePoint;
   }

   @Override
   public double getLongitude() {
      return longitude;
   }

   @Override
   public double getDeclination() {
      return eqPos.getDeviation();
   }

   public CoordinateSet getEqPos() {
      return eqPos;
   }

   public CoordinateSet getHorPos() {
      return horPos;
   }
}
