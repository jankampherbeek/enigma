/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.be.handlers;

import com.radixpro.enigma.be.calc.CoordinateConversions;
import com.radixpro.enigma.be.calc.SeFrontend;
import com.radixpro.enigma.be.calc.assist.SePositionResultHouses;
import com.radixpro.enigma.domain.astronpos.AllMundanePositions;
import com.radixpro.enigma.domain.astronpos.CoordinateSet;
import com.radixpro.enigma.domain.astronpos.IPosition;
import com.radixpro.enigma.domain.astronpos.MundanePosition;
import com.radixpro.enigma.domain.input.Location;
import com.radixpro.enigma.references.Ayanamshas;
import com.radixpro.enigma.references.EclipticProjections;
import com.radixpro.enigma.references.HouseSystems;
import com.radixpro.enigma.references.MundanePoints;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static swisseph.SweConst.*;

/**
 * Takes care of calculating mundane positions.
 */
public class MundanePositionsHandler {

   private final SeFrontend seFrontend;
   private final ObliquityHandler obliquityHandler;

   public MundanePositionsHandler(@NotNull final SeFrontend seFrontend,
                                  @NotNull final ObliquityHandler obliquityHandler) {
      this.seFrontend = seFrontend;
      this.obliquityHandler = obliquityHandler;
   }

   /**
    * Define mundane positions.
    *
    * @param jdUt        Julian day for UT.
    * @param eclProj     Type of projection to the ecliptic.
    * @param ayanamsha   The ayanamsha to be used (could be 'NONE').
    * @param houseSystem The housystem to be used.
    * @param location    Location.
    * @return
    */
   public AllMundanePositions definePositions(final double jdUt, @NotNull final EclipticProjections eclProj, @NotNull final Ayanamshas ayanamsha,
                                              @NotNull final HouseSystems houseSystem, @NotNull final Location location) {
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
