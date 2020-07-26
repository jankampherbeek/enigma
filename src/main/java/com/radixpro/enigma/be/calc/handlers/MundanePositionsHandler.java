/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.calc.handlers;

import com.radixpro.enigma.be.calc.assist.SePositionResultHouses;
import com.radixpro.enigma.be.calc.core.SeFrontend;
import com.radixpro.enigma.be.util.CoordinateConversions;
import com.radixpro.enigma.xchg.domain.*;
import com.radixpro.enigma.xchg.domain.astrondata.AllMundanePositions;
import com.radixpro.enigma.xchg.domain.astrondata.CoordinateSet;
import com.radixpro.enigma.xchg.domain.astrondata.IPosition;
import com.radixpro.enigma.xchg.domain.astrondata.MundanePosition;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static swisseph.SweConst.*;

/**
 * Takes care of calculating mundane positions.
 */
public class MundanePositionsHandler {

   private final SeFrontend seFrontend;
   private final ObliquityHandler obliquityHandler;

   /**
    * Initialization via Factory.
    *
    * @param seFrontend       instance of SeFrontend. PRE: not null.
    * @param obliquityHandler handler for the calculation of epsilon(obliquity). PRE: not null.
    * @see CaHandlersFactory
    */
   public MundanePositionsHandler(final SeFrontend seFrontend, final ObliquityHandler obliquityHandler) {
      this.seFrontend = checkNotNull(seFrontend);
      this.obliquityHandler = checkNotNull(obliquityHandler);
   }

   /**
    * Define mundane positions.
    *
    * @param jdUt        Julian day for UT.
    * @param eclProj     Type of projection to the ecliptic. PRE: not null.
    * @param ayanamsha   The ayanamsha to be used (could be 'NONE'). PRE: not null.
    * @param houseSystem The housystem to be used. PRE: not null.
    * @param location    Location. PRE: not null.
    * @return
    */
   public AllMundanePositions definePositions(final double jdUt, final EclipticProjections eclProj, final Ayanamshas ayanamsha, final HouseSystems houseSystem,
                                              final Location location) {
      checkNotNull(eclProj);
      checkNotNull(ayanamsha);
      checkNotNull(houseSystem);
      checkNotNull(location);
      int seFlags = SEFLG_SWIEPH;
      if (eclProj == EclipticProjections.SIDEREAL) seFlags = seFlags | SEFLG_SIDEREAL;
      final int seId = houseSystem.getSeId().charAt(0);
      final int nrOfCusps = houseSystem.getNrOfCusps();
      final SePositionResultHouses positionsForHouses = seFrontend.getPositionsForHouses(jdUt, seFlags, location, seId, nrOfCusps);
      return convert(positionsForHouses, jdUt, seId, nrOfCusps, seFlags, location, houseSystem);
   }


   private AllMundanePositions convert(final SePositionResultHouses positions, final double jdUt, final int seId, final int nrOfCusps, final int seFlags,
                                       final Location location, final HouseSystems houseSystem) {
      double[] cusps = positions.getCusps();
      double[] ascMc = positions.getAscMc();
      double obliquity = obliquityHandler.calcTrueObliquity(jdUt);
      List<IPosition> allCusps = new ArrayList<>();
      for (double cusp : cusps) {
         allCusps.add(createMundanePosition(cusp, obliquity, jdUt, location, MundanePoints.CUSP));
      }
      List<IPosition> specPoints = new ArrayList<>();
      specPoints.add(createMundanePosition(ascMc[0], obliquity, jdUt, location, MundanePoints.ASC));
      specPoints.add(createMundanePosition(ascMc[1], obliquity, jdUt, location, MundanePoints.MC));
      // skip 3: ARMC
      specPoints.add(createMundanePosition(ascMc[3], obliquity, jdUt, location, MundanePoints.VERTEX));
      specPoints.add(createMundanePosition(ascMc[4], obliquity, jdUt, location, MundanePoints.EAST_POINT));
      return new AllMundanePositions(allCusps, specPoints);
   }

   private MundanePosition createMundanePosition(final double longitude, final double obliquity, final double jdUt, final Location location,
                                                 final MundanePoints mundanePoint) {
      double[] eclValues = {longitude, 0.0, 1.0};    // longitude, latitude, distance
      double[] equaPositions = CoordinateConversions.eclipticToEquatorial(eclValues, obliquity);  // RA, decl
      CoordinateSet eqPos = new CoordinateSet(equaPositions[0], equaPositions[1]);
      double[] horizontalPosition = seFrontend.getHorizontalPosition(jdUt, eclValues, location, SE_ECL2HOR);
      CoordinateSet horPos = new CoordinateSet(horizontalPosition[0], horizontalPosition[1]);  // true altitude, index 2 = apparent altitude
      return new MundanePosition(mundanePoint, longitude, eqPos, horPos);
   }

}
