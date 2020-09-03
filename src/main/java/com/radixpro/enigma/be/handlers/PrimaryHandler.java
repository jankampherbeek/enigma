/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.handlers;

import com.radixpro.enigma.be.calc.CoordinateConversions;
import com.radixpro.enigma.be.calc.EnigmaAstronMath;
import com.radixpro.enigma.be.calc.EnigmaMath;
import com.radixpro.enigma.be.calc.SpaeculumPropSaCalculator;
import com.radixpro.enigma.domain.astronpos.*;
import com.radixpro.enigma.domain.reqresp.PrimaryCalcRequest;
import com.radixpro.enigma.domain.reqresp.SimpleProgResponse;
import com.radixpro.enigma.shared.Range;
import com.radixpro.enigma.shared.exceptions.UnknownTimeKeyException;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class PrimaryHandler {

   private final PrimaryPositionsHandler primaryPositionsHandler;
   private final TimeKeyHandler timeKeyHandler;
   private final ObliquityHandler obliquityHandler;
   private final SpaeculumPropSaCalculator spsCalculator;


   public PrimaryHandler(final PrimaryPositionsHandler primaryPositionsHandler, final TimeKeyHandler timeKeyHandler, final ObliquityHandler obliquityHandler,
                         final SpaeculumPropSaCalculator spsCalculator) {
      this.primaryPositionsHandler = checkNotNull(primaryPositionsHandler);
      this.timeKeyHandler = checkNotNull(timeKeyHandler);
      this.spsCalculator = spsCalculator;
      this.obliquityHandler = obliquityHandler;
   }


   public SimpleProgResponse performCalculations(final PrimaryCalcRequest request) {
      checkNotNull(request);
      List<IPosition> responsePositions = new ArrayList<>();
      final CalculatedChart calculatedChart = request.getCalculatedChart();
      final double geoLat = request.getLocation().getGeoLat();
      try {
         double solarArc = timeKeyHandler.retrieveTimeSpan(request.getDateTimeRadix(), request.getDateTime(), request.getTimeKey(), request.getLocation(),
               request.getSettings());
         double eps = obliquityHandler.calcTrueObliquity(request.getDateTimeRadix().getJd());
         double prMc = new Range(0, 360).checkValue(calculatedChart.getMundPoints().getMc().getLongitude() + solarArc);
         double prRaMc = CoordinateConversions.eclipticToEquatorial(new double[]{prMc, 0.0}, eps)[0];
         double prAsc = EnigmaAstronMath.ascFromRamc(prRaMc, geoLat, eps);
         SpaeculumPropSaData spsData = spsCalculator.performCalculation(calculatedChart, request.getDateTimeRadix().getJd(),
               request.getLocation().getGeoLat(), request.getSettings());
         for (SpaeculumPropSaItem item : spsData.getItems()) {
            double offset = item.getRa() - spsData.getRaMcRx();
            double raProg = placideanIterator(geoLat, eps, prRaMc, offset, item.getPropSa(), item.getQuadrant());
            double lonProg = CoordinateConversions.equatorialToEcliptic(raProg, eps);
            double declProg = CoordinateConversions.longitudeToDeclination(lonProg, eps);
            responsePositions.add(new LonDeclPosition(item.getChartPoint(), lonProg, declProg));
         }

      } catch (UnknownTimeKeyException utke) {
         // TODO LOG and create error msg
      }
      return new SimpleProgResponse(responsePositions, request);
   }


   private double placideanIterator(double geoLat, double eps, double rightAscMC, double offsetForPosition, double factor, int quadrant) {

      double workOffsetForPosition;
      double workFactor;
      workOffsetForPosition = offsetForPosition;
      workFactor = factor;
      final double margin = 0.00001;
      double diff = 1;
      double currentRightAscension = rightAscMC + offsetForPosition;
      double tempRightAscension;
      double tanEpsilon = EnigmaMath.tan(eps);
      double tanGeoLatitude = EnigmaMath.tan(geoLat);
      while (diff > margin) {
         if (workOffsetForPosition < 90) {
            tempRightAscension = rightAscMC + (EnigmaMath.acos(-EnigmaMath.sin(currentRightAscension) * tanEpsilon * tanGeoLatitude)) * workFactor;
         } else {
            tempRightAscension = 180 + rightAscMC - (EnigmaMath.acos(EnigmaMath.sin(currentRightAscension) * tanEpsilon * tanGeoLatitude)) * workFactor;
         }
         diff = Math.abs(tempRightAscension - currentRightAscension);
         currentRightAscension = tempRightAscension;
      }
      return new Range(0, 360).checkValue(currentRightAscension);
   }

}
