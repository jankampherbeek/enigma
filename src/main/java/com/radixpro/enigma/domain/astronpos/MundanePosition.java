/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.astronpos;

import com.radixpro.enigma.xchg.domain.MundanePoints;
import com.radixpro.enigma.xchg.domain.astrondata.CoordinateSet;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

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
    * @param mundanePoint Type of mundane point (MC, Vertex,cusp etc.). PRE: not null.
    * @param longitude    Ecliptical longitude (latitude does not apply for mundane points). PRE: 0.0 <= longitude < 360.0
    * @param eqPos        Equatorial coordinates. PRE: not null.
    * @param horPos       Horizontal coordinates. PRE: not null.
    */
   public MundanePosition(final MundanePoints mundanePoint, final double longitude, final CoordinateSet eqPos, final CoordinateSet horPos) {
      this.mundanePoint = checkNotNull(mundanePoint);
      checkArgument(0.0 <= longitude && longitude < 360.0);
      this.longitude = longitude;
      this.eqPos = checkNotNull(eqPos);
      this.horPos = checkNotNull(horPos);
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
