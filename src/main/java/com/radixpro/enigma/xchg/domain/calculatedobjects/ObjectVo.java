/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.domain.calculatedobjects;

import com.radixpro.enigma.xchg.domain.IChartPoints;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Value object for a specific celestial object.
 * TODO remove obsolete class.
 */
public class ObjectVo implements IObjectVo {

   private final ICoordinateVo eclipticalCoords;
   private final ICoordinateVo equatorialCoords;
   private final ICoordinateVo horizontalCoords;
   private final IChartPoints celestialObject;

   /**
    * Constructor defines all properties.
    *
    * @param eclipticalCoords Positions using ecliptic coordinates. PRE: not null.
    * @param equatorialCoords Positions using equatorial coordinates. PRE: not null.
    * @param horizontalCoords Positions using horizontal coordinates. PRE: not null.
    * @param celestialObject  Identification of the celestial object. PRE: not null.
    */
   public ObjectVo(final ICoordinateVo eclipticalCoords, final ICoordinateVo equatorialCoords,
                   final ICoordinateVo horizontalCoords, final IChartPoints celestialObject) {
      this.eclipticalCoords = checkNotNull(eclipticalCoords);
      this.equatorialCoords = checkNotNull(equatorialCoords);
      this.horizontalCoords = checkNotNull(horizontalCoords);
      this.celestialObject = checkNotNull(celestialObject);
   }

   @Override
   public ICoordinateVo getEclipticCoords() {
      return eclipticalCoords;
   }

   @Override
   public ICoordinateVo getEquatorialCoords() {
      return equatorialCoords;
   }

   public ICoordinateVo getHorizontalCoords() {
      return horizontalCoords;
   }

   @Override
   public IChartPoints getChartPoint() {
      return celestialObject;
   }

}
