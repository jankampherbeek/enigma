/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.handlers;

import com.radixpro.enigma.be.calc.CoordinateConversions;
import com.radixpro.enigma.be.calc.EnigmaAstronMath;
import com.radixpro.enigma.be.calc.EnigmaMath;
import com.radixpro.enigma.be.calc.assist.SpaeculumPropSa;
import com.radixpro.enigma.be.calc.assist.SpaeculumPropSaItem;
import com.radixpro.enigma.be.handlers.TimeKeyHandler;
import com.radixpro.enigma.domain.astronpos.IPosition;
import com.radixpro.enigma.domain.astronpos.LonDeclPosition;
import com.radixpro.enigma.shared.Range;
import com.radixpro.enigma.shared.exceptions.UnknownTimeKeyException;
import com.radixpro.enigma.xchg.api.requests.PrimaryCalcRequest;
import com.radixpro.enigma.xchg.api.responses.SimpleProgResponse;
import com.radixpro.enigma.xchg.domain.astrondata.CalculatedChart;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class PrimaryHandler {

   private final PrimaryPositionsHandler primaryPositionsHandler;
   private final TimeKeyHandler timeKeyHandler;

   /**
    * Instantiate via factory.
    *
    * @param primaryPositionsHandler Handler for the astronomical calculations. PRE: not null.
    * @param timeKeyHandler          Handler for time key related calculations. PRE: nut null.
    * @see CaHandlersFactory
    */
   public PrimaryHandler(final PrimaryPositionsHandler primaryPositionsHandler, final TimeKeyHandler timeKeyHandler) {
      this.primaryPositionsHandler = checkNotNull(primaryPositionsHandler);
      this.timeKeyHandler = checkNotNull(timeKeyHandler);
   }


   public SimpleProgResponse performCalculations(final PrimaryCalcRequest request) {
      checkNotNull(request);
      List<IPosition> responsePositions = new ArrayList<>();
      final CalculatedChart calculatedChart = request.getCalculatedChart();
      final double geoLat = request.getLocation().getGeoLat();
      SpaeculumPropSa spaeculumPropSa = new SpaeculumPropSa(calculatedChart, request.getDateTimeRadix().getJdUt(), request.getLocation().getGeoLat(),
            request.getSettings());

      try {
         double solarArc = timeKeyHandler.retrieveTimeSpan(request.getDateTimeRadix(), request.getDateTime(), request.getTimeKey(), request.getLocation(),
               request.getSettings());
         double eps = CaHandlersFactory.getObliquityHandler().calcTrueObliquity(request.getDateTimeRadix().getJdUt());
         double prMc = new Range(0, 360).checkValue(calculatedChart.getMundPoints().getMc().getLongitude() + solarArc);
         double prRaMc = CoordinateConversions.eclipticToEquatorial(new double[]{prMc, 0.0}, eps)[0];
         double prAsc = EnigmaAstronMath.ascFromRamc(prRaMc, geoLat, eps);

         for (SpaeculumPropSaItem item : spaeculumPropSa.getSpaeculum()) {
            double offset = item.getRa() - spaeculumPropSa.getRaMcRx();
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
