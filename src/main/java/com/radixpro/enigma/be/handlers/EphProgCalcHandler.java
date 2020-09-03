/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.handlers;

import com.radixpro.enigma.be.calc.CoordinateConversions;
import com.radixpro.enigma.be.calc.SeFrontend;
import com.radixpro.enigma.be.calc.assist.CombinedFlags;
import com.radixpro.enigma.be.calc.assist.SePositionResultCelObjects;
import com.radixpro.enigma.domain.astronpos.*;
import com.radixpro.enigma.domain.input.Location;
import com.radixpro.enigma.domain.reqresp.IProgCalcRequest;
import com.radixpro.enigma.domain.reqresp.SimpleProgResponse;
import com.radixpro.enigma.references.Ayanamshas;
import com.radixpro.enigma.references.CelestialObjects;
import com.radixpro.enigma.references.SeFlags;
import com.radixpro.enigma.xchg.api.settings.ICalcSettings;
import com.radixpro.enigma.xchg.domain.IChartPoints;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Handler for the calculation of progressive positions based on ephemeris calculations.
 */
public class EphProgCalcHandler {

   public final SeFrontend seFrontend;

   public EphProgCalcHandler(final SeFrontend seFrontend) {
      this.seFrontend = seFrontend;
   }

   public SimpleProgResponse retrievePositions(final IProgCalcRequest request) {
      checkNotNull(request);
      final List<IPosition> posVos = calculatePositions(request.getDateTime().getJd(), request.getLocation(), request.getSettings());
      return new SimpleProgResponse(posVos, request);
   }

   private List<IPosition> calculatePositions(final double jdUt, final Location location, final ICalcSettings settings) {
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

      List<IPosition> posResults = new ArrayList<>();
      List<IChartPoints> points = settings.getPoints();
      for (IChartPoints point : points) {
         int seId = (int) ((CelestialObjects) point).getSeId();
         final SePositionResultCelObjects posEcl = seFrontend.getPositionsForCelBody(jdUt, seId, eclFlags, location);
         final SePositionResultCelObjects posEq = seFrontend.getPositionsForCelBody(jdUt, seId, eqFlags, location);
         double[] coordSet = {posEcl.getAllPositions()[0], posEcl.getAllPositions()[1], posEcl.getAllPositions()[2]};
         final double[] horCoordinates = CoordinateConversions.eclipticToHorizontal(jdUt, coordSet, location);
         final CoordinateSet fullHorCoordinates = new CoordinateSet(horCoordinates[0], horCoordinates[1]);
         final FullPointCoordinate fullEclCoordinates = createFullPointCoordinate(posEcl);
         final FullPointCoordinate fullEqCoordinates = createFullPointCoordinate(posEq);
         posResults.add(new FullPointPosition((CelestialObjects) point, fullEclCoordinates, fullEqCoordinates, fullHorCoordinates));
      }
      return posResults;
   }

   private FullPointCoordinate createFullPointCoordinate(final SePositionResultCelObjects sePos) {
      CoordinateSet3D posCoordinates = new CoordinateSet3D(sePos.getAllPositions()[0], sePos.getAllPositions()[1], sePos.getAllPositions()[2]);
      CoordinateSet3D speedCoordinates = new CoordinateSet3D(sePos.getAllPositions()[3], sePos.getAllPositions()[4], sePos.getAllPositions()[5]);
      return new FullPointCoordinate(posCoordinates, speedCoordinates);
   }


}
