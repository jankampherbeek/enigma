/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.assist;

import com.radixpro.enigma.be.calc.CoordinateConversions;
import com.radixpro.enigma.be.calc.EnigmaAstronMath;
import com.radixpro.enigma.be.calc.handlers.CaHandlersFactory;
import com.radixpro.enigma.domain.astronpos.IPosition;
import com.radixpro.enigma.shared.Range;
import com.radixpro.enigma.shared.exceptions.EnigmaMathException;
import com.radixpro.enigma.xchg.api.settings.ICalcSettings;
import com.radixpro.enigma.xchg.domain.CelestialObjects;
import com.radixpro.enigma.xchg.domain.IChartPoints;
import com.radixpro.enigma.xchg.domain.analysis.ChartPointTypes;
import com.radixpro.enigma.xchg.domain.astrondata.CalculatedChart;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Spaeculum forn primary directions that use proprtions of the Placidian semi arcs.
 */
public class SpaeculumPropSa {

   private final CalculatedChart calculatedChart;
   private final ICalcSettings settings;
   private final double jdUt;
   private final double geoLat;
   private List<SpaeculumPropSaItem> spaeculum;
   private double raMcRx;


   public SpaeculumPropSa(final CalculatedChart calculatedChart, final double jdUt, final double geoLat, final ICalcSettings settings) {
      this.calculatedChart = checkNotNull(calculatedChart);
      this.settings = checkNotNull(settings);
      this.jdUt = jdUt;
      this.geoLat = geoLat;
      performCalculation();
   }

   private void performCalculation() {
      spaeculum = new ArrayList<>();
      for (IChartPoints point : settings.getPoints()) {
         if (ChartPointTypes.CEL_BODIES == point.getPointType()) spaeculum.add(itemForCelestialBody(point.getId(), jdUt));
      }
   }

   private SpaeculumPropSaItem itemForCelestialBody(final int id, final double jdUt) {
      SpaeculumPropSaItem item;
      double lon = 0.0;
      double mc = calculatedChart.getMundPoints().getMc().getLongitude();
      double asc = calculatedChart.getMundPoints().getAsc().getLongitude();
      double decl = 0.0;
      double ra = 0.0;
      double sa = 0.0;
      double propSa = 0.0;
      int quadrant = 0;
      double eps = CaHandlersFactory.getObliquityHandler().calcTrueObliquity(jdUt);
      raMcRx = CoordinateConversions.eclipticToEquatorial(new double[]{mc, 0.0}, eps)[0];
      IChartPoints chartPoint = CelestialObjects.EMPTY;
      for (IPosition point : calculatedChart.getCelPoints()) {
         if (id == point.getChartPoint().getId()) {
            chartPoint = point.getChartPoint();
            lon = point.getLongitude();
            decl = point.getDeclination();
            ra = CoordinateConversions.eclipticToEquatorial(new double[]{lon, 0.0}, eps)[0];
            try {
               sa = EnigmaAstronMath.semiArc(ra, geoLat, eps);
            } catch (EnigmaMathException eme) {
               // TODO log error, make no item for spaeculum
            }
            quadrant = defineQuadrant(lon, mc, asc);
            propSa = getPlacideanPropOfSa(geoLat, eps, raMcRx, ra, sa, quadrant);

         }
      }
      return new SpaeculumPropSaItem(chartPoint, lon, ra, decl, sa, propSa, quadrant);
   }

   /**
    * Proportion of semi-arc based on the Placidean method.
    * The arc is calculated as part from a quadrant.
    * Cusp XI is 1/3, cusp XII is 2/3, ascendant is 0.
    *
    * @param geoLat Geographic latitude.
    * @param eps    Angle between ecliptic and equator.
    * @param raMc   Right ascension of MC.
    * @param raPos  Right ascension of position where the proportion is calculated for.
    * @return The proportion of the semi-arc.
    */
   private double getPlacideanPropOfSa(double geoLat, double eps, double raMc, double raPos, double sa, int quadrant) {
      double meridianDistance = raPos - raMc;
      if (quadrant == 4 || quadrant == 2) {
         meridianDistance = new Range(0, 180).checkValue(meridianDistance);
      } else if (quadrant == 1 || quadrant == 3) {
         meridianDistance = new Range(-180.0, 0).checkValue(meridianDistance);
      }
      double proportion = meridianDistance / sa;
      if (quadrant == 1 || quadrant == 3) proportion = -proportion;
      return proportion;
   }

   private int defineQuadrant(double lon, double mc, double asc) {
      double distanceFromMC = new Range(0, 360).checkValue(lon - mc);
      double distanceFromAsc = new Range(0, 360).checkValue(lon - asc);
      boolean eastHemisphere = false;
      boolean southHemisphere = false;
      if (distanceFromMC < 180) {
         eastHemisphere = true;
      }
      if (distanceFromAsc < 180) {
         southHemisphere = true;
      }
      int quadrant = 0;
      if (eastHemisphere && southHemisphere) {
         quadrant = 1;
      } else if (eastHemisphere && !southHemisphere) {
         quadrant = 4;
      } else if (!eastHemisphere && southHemisphere) {
         quadrant = 2;
      } else {
         quadrant = 3;
      }
      return quadrant;
   }

   public double getRaMcRx() {
      return raMcRx;
   }

   public List<SpaeculumPropSaItem> getSpaeculum() {
      return spaeculum;
   }
}
