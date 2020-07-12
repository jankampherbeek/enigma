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
import com.radixpro.enigma.xchg.api.responses.SimpleProgResponse;
import com.radixpro.enigma.xchg.api.settings.ICalcSettings;
import com.radixpro.enigma.xchg.domain.*;
import com.radixpro.enigma.xchg.domain.calculatedobjects.SimplePosVo;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Handler for the calculation of progressive positions based on ephemeris calculations.
 * TODO replace SimplePosVo with iPosition
 */
public class EphProgCalcHandler {

   public final SeFrontend seFrontend;


   public EphProgCalcHandler(final SeFrontend seFrontend) {
      this.seFrontend = seFrontend;
   }

   public SimpleProgResponse retrievePositions(final IProgCalcRequest request) {
      checkNotNull(request);
      final List<SimplePosVo> posVos = calculatePositions(request.getDateTime().getJdUt(), request.getLocation(), request.getSettings());
      return new SimpleProgResponse(posVos, request);
   }

   private List<SimplePosVo> calculatePositions(final double jdUt, final Location location, final ICalcSettings settings) {
      Ayanamshas ayanamsha = settings.getAyamsha();
      boolean sidereal = settings.isSidereal();
      List<SeFlags> flagListEcl = new ArrayList<>();
      List<SeFlags> flagListEq = new ArrayList<>();
      flagListEcl.add(SeFlags.SWISSEPH);
      flagListEq.add(SeFlags.SWISSEPH);
      flagListEq.add(SeFlags.EQUATORIAL);
      if (sidereal) {
         flagListEcl.add(SeFlags.SIDEREAL);
         flagListEq.add(SeFlags.SIDEREAL);
      }
      // TODO handle topocentric and sidereal
      int eclFlags = (int) new CombinedFlags().getCombinedValue(flagListEcl);
      int eqFlags = (int) new CombinedFlags().getCombinedValue(flagListEq);

      List<SimplePosVo> posResults = new ArrayList<>();
      List<IChartPoints> points = settings.getPoints();
      for (IChartPoints point : points) {
         int seId = (int) ((CelestialObjects) point).getSeId();
         SePositionResultCelObjects posEcl = seFrontend.getPositionsForCelBody(jdUt, seId, eclFlags, location);
         SePositionResultCelObjects posEq = seFrontend.getPositionsForCelBody(jdUt, seId, eqFlags, location);
         posResults.add(new SimplePosVo(point, posEcl.getAllPositions()[0], posEcl.getAllPositions()[1], posEq.getAllPositions()[0],
               posEq.getAllPositions()[1]));
      }
      return posResults;
   }


}
