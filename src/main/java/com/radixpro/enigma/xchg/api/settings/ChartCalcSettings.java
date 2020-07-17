/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.xchg.api.settings;

import com.radixpro.enigma.xchg.domain.*;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Settigns for the calculation of a chart.
 */
public class ChartCalcSettings {

   private final List<IChartPoints> points;
   private final ObserverPositions obsPos;
   private final EclipticProjections eclProj;
   private final Ayanamshas ayanamsha;
   private final HouseSystems houseSystem;

   /**
    * Constructor defines all properties.
    *
    * @param points      Celestial points to calculate. PRE: not null, not empty.
    * @param obsPos      Observerpositon. PRE: not null.
    * @param eclProj     Ecliptical projection. PRE: not null.
    * @param ayanamsha   Ayanamsha. Only used if Ecliptical projection is sidereal. PRE: not null.
    * @param houseSystem Housesystem. PRE: not null.
    */
   public ChartCalcSettings(final List<IChartPoints> points, final ObserverPositions obsPos, final EclipticProjections eclProj, final Ayanamshas ayanamsha,
                            final HouseSystems houseSystem) {
      checkArgument(null != points && !points.isEmpty());
      this.points = points;
      this.obsPos = obsPos;
      this.eclProj = eclProj;
      this.ayanamsha = ayanamsha;
      this.houseSystem = houseSystem;
   }

   public List<IChartPoints> getPoints() {
      return points;
   }

   public ObserverPositions getObsPos() {
      return obsPos;
   }

   public EclipticProjections getEclProj() {
      return eclProj;
   }

   public Ayanamshas getAyanamsha() {
      return ayanamsha;
   }

   public HouseSystems getHouseSystem() {
      return houseSystem;
   }
}
