/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.handlers;

import com.radixpro.enigma.be.calc.assist.CombinedFlags;
import com.radixpro.enigma.be.calc.assist.SePositionResultCelObjects;
import com.radixpro.enigma.be.calc.core.SeFrontend;
import com.radixpro.enigma.xchg.api.requests.IProgCalcRequest;
import com.radixpro.enigma.xchg.api.requests.TransitCalcRequest;
import com.radixpro.enigma.xchg.api.responses.SimpleProgResponse;
import com.radixpro.enigma.xchg.domain.Ayanamshas;
import com.radixpro.enigma.xchg.domain.CelestialObjects;
import com.radixpro.enigma.xchg.domain.Location;
import com.radixpro.enigma.xchg.domain.SeFlags;
import com.radixpro.enigma.xchg.domain.analysis.IChartPoints;
import com.radixpro.enigma.xchg.domain.calculatedobjects.SimplePosVo;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Handler for the calculation of transit positions.
 */
public class TransitsCalcHandler {

   public final SeFrontend seFrontend;


   public TransitsCalcHandler(final SeFrontend seFrontend) {
      this.seFrontend = seFrontend;
   }

   public SimpleProgResponse retrieveTransitPositions(final IProgCalcRequest request) {
      checkNotNull(request);
      return new SimpleProgResponse(calculatePositions((TransitCalcRequest) request), request);
   }

   private List<SimplePosVo> calculatePositions(TransitCalcRequest request) {
      double jdUt = request.getDateTime().getJdUt();
      Location location = request.getLocation();
      Ayanamshas ayanamsha = request.getSettings().getAyamsha();
      boolean sidereal = request.getSettings().isSidereal();
      List<SeFlags> flagListEcl = new ArrayList<>();
      List<SeFlags> flagListEq = new ArrayList<>();
      flagListEcl.add(SeFlags.SWISSEPH);
      flagListEq.add(SeFlags.SWISSEPH);
      flagListEq.add(SeFlags.EQUATORIAL);
      if (sidereal) {
         flagListEcl.add(SeFlags.SIDEREAL);
         flagListEq.add(SeFlags.SIDEREAL);
      }
      // TODO handle topocentric
      int eclFlags = (int) new CombinedFlags(flagListEcl).getCombinedValue();
      int eqFlags = (int) new CombinedFlags(flagListEq).getCombinedValue();

      List<SimplePosVo> posResults = new ArrayList<>();
      List<IChartPoints> points = request.getSettings().getPoints();
      for (IChartPoints point : points) {
         int seId = (int) ((CelestialObjects) point).getSeId();
         SePositionResultCelObjects posEcl = seFrontend.getPositionsForCelBody(jdUt, seId, eclFlags, location);
         SePositionResultCelObjects posEq = seFrontend.getPositionsForCelBody(jdUt, seId, eqFlags, location);
         posResults.add(new SimplePosVo(point, posEcl.getAllPositions()[0], posEcl.getAllPositions()[1],
               posEq.getAllPositions()[0], posEq.getAllPositions()[1]));
      }
      return posResults;
   }


}
