/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.astronpos;

import com.radixpro.enigma.xchg.domain.IChartPoints;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Item for a single body/point in a spaeculum based on Placidian semi-arcs.
 */
public class SpaeculumPropSaItem {

   private final IChartPoints chartPoint;
   private final double lon;
   private final double ra;
   private final double decl;
   private final double sa;
   private final double propSa;
   private final int quadrant;

   /**
    * Constructor defines all elements.
    *
    * @param lon    longitude
    * @param ra     right ascension
    * @param decl   declination
    * @param propSa proportion of semi-arc.
    */
   public SpaeculumPropSaItem(final IChartPoints chartPoint, final double lon, final double ra, final double decl, final double sa, final double propSa,
                              final int quadrant) {
      this.chartPoint = checkNotNull(chartPoint);
      this.lon = lon;
      this.ra = ra;
      this.decl = decl;
      this.sa = sa;
      this.propSa = propSa;
      this.quadrant = quadrant;
   }

   public IChartPoints getChartPoint() {
      return chartPoint;
   }

   public double getLon() {
      return lon;
   }

   public double getRa() {
      return ra;
   }

   public double getDecl() {
      return decl;
   }

   public double getSa() {
      return sa;
   }

   public double getPropSa() {
      return propSa;
   }

   public int getQuadrant() {
      return quadrant;
   }
}
